const {namedLambda} = require("./Lang.js");

// router.path('/teacher')
//       .controller(studentController)
//       .map(ctr -> ({
//            get: ctr.getStudentList,
//            post: ctr.getTeacherList,         
//        }));
//       .get()
//       .post();
//      ;
class Router {

    constructor() {
        this.routerMap = [{}, {}, {}, {}];
        this.routerIndex = 
        {
            get : 0,
            post : 1,
            put : 2,
            delete : 3,
        }
        this.wildCardStr = ":[0-9A-z]+";
    }

    path(url) {
        let app = this;
        const controller = (ctrl, ctrMapper) => {
            const ctrMapConfig = ctrMapper(ctrl);
            for (let method of ['get', 'post', 'put', 'delete']) {
                if (ctrMapConfig[method] != null) {
                    this._registerRoute(method, url, ctrl, ctrMapConfig);
                }                
            }   
            return app;        
        };
        return {controller: ctrl => ({map: ctrMapper => controller(ctrl, ctrMapper)})};
    }

    route(method, userUrl) {
        var routers = this.routerMap[this.routerIndex[method.toLowerCase()]];
        var resolvedAction = routers[userUrl];
       
        if (resolvedAction == null) {
            // find dynamic url
            console.log("user url " + userUrl);
            for(let url of Object.keys(routers)) {
                  var actionInfo = routers[url];
                  console.log("match ? " + url);
                  if (actionInfo.isWildcard && userUrl.match(actionInfo.reg)) {
                       resolvedAction = actionInfo;
                       var urlFragment = userUrl.split("/");
                       var pathVars = {};
                       for (let param of actionInfo.params) {
                           pathVars[param.paramName] = urlFragment[param.index];
                       }
                       console.log("dynamic url " + method + " " + userUrl + " => " + url);
                       console.log("param => " + JSON.stringify(pathVars));
                       break;
                  }
              }
        }
        else {
             console.log("static url " + method + " " + userUrl + " => " + resolvedAction.action)
        }
        return {
            action :  resolvedAction == null ? null : resolvedAction.action,
            pathVars: pathVars,
        };
    }


    _registerRoute(method, url, ctrl, ctrlMethod) {
        var app = this;
        
        var routeMetaData = {
            "isWildcard": null,
            "params": null,
            "action": null,
            "reg": null,
        };
        // does not contain wildcard
        if (url.indexOf(':') == -1) {
            routeMetaData.isWildcard = false;    
        }
        else {
            routeMetaData.isWildcard = true;
            var wildcardReg = new RegExp(this.wildCardStr);
            var pathsplits = url.split('/');
            routeMetaData.params = [];
            pathsplits.forEach((pathsplit, i) => {
                if (pathsplit.match(wildcardReg)) {
                    url = url.replace(pathsplit, this.wildCardStr.substr(1));
                    routeMetaData.params.push(
                    {
                        index: i,
                        paramName: pathsplit.substr(1)
                    });
                }
            });
            routeMetaData.reg = new RegExp(url);      
        }
        console.log("Register [" + method + "] " + url + " -> "  + "." + ctrlMethod[method].name);
        routeMetaData.action = (req, res, pathVars) => ctrlMethod[method].bind(ctrl)(req, res, pathVars);
        app.routerMap[app.routerIndex[method]][url] = routeMetaData;
    }
}

exports.router = new Router();