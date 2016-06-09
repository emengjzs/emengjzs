/**
 * Created by emengjzs on 2016/4/3.
 */
angular
    .module('components', ['ngResource'])
    ;

angular
    .module('app', [
            'ngComponentRouter',
            'components'
        ],

        // 修改 post 行为
        function ($httpProvider) {
            // Use x-www-form-urlencoded Content-Type
            $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8';
            /**
             * The workhorse; converts an object to x-www-form-urlencoded serialization.
             * @param {Object} obj
             * @return {String}
             */
            var param = function (obj) {
                var query = '', name, value, fullSubName, subName, subValue, innerObj, i;
                for (name in obj) {
                    value = obj[name];
                    if (value instanceof Array) {
                        for (i = 0; i < value.length; ++i) {
                            subValue = value[i];
                            fullSubName = name + '[' + i + ']';
                            innerObj = {};
                            innerObj[fullSubName] = subValue;
                            query += param(innerObj) + '&';
                        }
                    }
                    else if (value instanceof Object) {
                        for (subName in value) {
                            subValue = value[subName];
                            fullSubName = name + '[' + subName + ']';
                            innerObj = {};
                            innerObj[fullSubName] = subValue;
                            query += param(innerObj) + '&';
                        }
                    }
                    else if (value !== undefined && value !== null)
                        query += encodeURIComponent(name) + '=' + encodeURIComponent(value) + '&';
                }

                return query.length ? query.substr(0, query.length - 1) : query;
            };

            // Override $http service's default transformRequest
            $httpProvider.defaults.transformRequest = [function (data) {
                return angular.isObject(data) && String(data) !== '[object File]' ? param(data) : data;
            }]
        }

    ).value('$routerRootComponent', 'app')

    // 设置后台url常量
    .constant('URLSet', {
        'JOB_CREATE': "/job",
        'JOB_LIST': "/job",
        'JOB_DETAIL': "/jobDetail",
        'JOB_RUN': "/job/run",
        'JOB_RUN_INFO': "/job/runcontext/",
        'JOB_RUN_LIST': "/job/runlist",
        'JOB_LOG_LATEST': "/job/log/latest",
        'JOB_LOG': '/job/log',
        'JOB_DISABLE': '/job/disable',
        'JOB_ENABLE': '/job/enable',
        'JOB_RUN_DETAIL': '/job/run/history',
        'JOB_TEST_REPORT': '/job/testreport',
        'ANALYSE_PLAN_CREATE': '/analysePlan/create',
        'ANALYSE_PLAN_LIST': '/analysePlan',
        'ANALYSE_PLAN_RUN': '/analysePlan/run',
        'ANALYSE_RECORD_LIST': '/analysePlan/records',
        'ANALYSE_RECORD': '/analysePlan/record',
        'ANALYSE_PLAN': '/plan'
    })
    .filter('time', function () {
        return function (input, startTime) {
            input = input === null ? ((new Date()).getTime() - startTime) / 1000 : input / 1000;
        
            var h = parseInt(input / 3600);
            var m = parseInt(input / 60 % 60);
            var s = parseInt(input % 60);
            return h + "时 " + m + "分 " + s + "秒";
        };
    })

    .filter('nounderline', function () {
        return function (input) {
            return input === null ? input : input.replace("_", " ");
        };
    })
    .filter('jp', function() {
        return function (input, o) {
            if (typeof(input) == 'undefined') {
                return '';
            }
            return JSONPath(input["$ref"], o);
        };
    })
    .filter('exclude', function() {
        return function (input, ex) {
            return input == ex ? "" : input;
        };
    })
    .factory('SyncHttp', ['$http', '$q', function ($http, $q) {
        return {
            post : function(url, data) {
                var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
                $http.post(url, data).
                success(function(data, status, headers, config) {
                    deferred.resolve(data);  // 声明执行成功，即http请求数据成功，可以返回数据了
                }).
                error(function(data, status, headers, config) {
                    deferred.reject(data);   // 声明执行失败，即服务器返回错误
                });
                return deferred.promise;   // 返回承诺，这里并不是最终数据，而是访问最终数据的API
            } ,// end query,
            get : function(url, data) {
                var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
                $http.get(url, data).
                success(function(data, status, headers, config) {
                    deferred.resolve(data);  // 声明执行成功，即http请求数据成功，可以返回数据了
                }).
                error(function(data, status, headers, config) {
                    deferred.reject(data);   // 声明执行失败，即服务器返回错误
                });
                return deferred.promise;   // 返回承诺，这里并不是最终数据，而是访问最终数据的API
            } // end query
        };}])

    .component('bread', {
        templateUrl: 'bread.html',

    })
    .component('app', {
        templateUrl: 'home.html',
        $routeConfig: [

            // 设置前段路由
            {path: '/home', name: 'JobList', component: 'jobList', useAsDefault: true},
            {path: '/job/config', name: 'JobConfig', component: 'jobConfig'},
            {path: '/job/:id/...', name: 'JobDetail', component: 'jobDetail'},
            
            {path: '/', name: 'JobList', component: 'jobList'}
        ],
        controller: FeatureListController
    })
    .component('myMsg', {
        template: "<div></div>",
        controller: function ($rootScope, $document, $element) {
            var warn = function (msg) {
                return {
                    text: msg,
                    sticky: false,
                    position: 'middle-center',
                    type: 'warning'
                }
            };
            var error = function (msg) {
                return {
                    text: msg,
                    sticky: true,
                    position: 'middle-center',
                    type: 'error'
                }
            };
            var success = function (msg) {
                return {
                    text: msg,
                    sticky: false,
                    position: 'top-right',
                    type: 'success'
                }
            };
            this.show = function (myMsg) {
                console.log("watch!!");
                if (!myMsg) return;
                if (myMsg.success) {
                    $($element).toastmessage('showToast', success(myMsg.message));
                }
                else {
                    $($element).toastmessage('showToast', warn(myMsg.message));
                }
            };
            $rootScope.showMsg = this.show;

        },
    })



;


function FeatureListController() {
    this.features = [
        {
            'name': "任务列表",
            'icon': "th-list",
            'url' : "/home",
            'sublist': []
        },
        {
            'name': '新建自动化测试',
            'icon': 'plus',
            'url': '/job/config',
            'sublist': []
        },
        {
            'name': '系统设置',
            'icon': 'cog',
            'url': '/job/config',
            'sublist': []
        }
    ];
    this.index = 0;
}

function tplurl(templateUrl) {
    return templateUrl + "?" + new Date();
}