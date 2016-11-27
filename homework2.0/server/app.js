const http = require('http');
const {studentController} = require('./controller/StudentController.js');
const {router} = require('./lib/Router.js');
const {currrifyCallback} = require('./lib/Lang.js');

http.ServerResponse.prototype.endJSON = function(data) {
    this.end(JSON.stringify(data));
}

// router.path('/teacher')
//       .controller(studentController)
//       .map(ctr -> ({
//            get: ctr.getStudentList,
//            post: ctr.getTeacherList,         
//        }));
//       .get()
//       .post();
//      ;

// route config 
router
    .path("/student").controller(studentController)
    .map(ctr => ({
        get: ctr.getStudentList,
        delete: ctr.deleteStudent,
    }))

    .path("/student/:id").controller(studentController)
    .map(ctrl => ({
        get: ctrl.getStudentById,
    }))

    .path("/teacher").controller(studentController)
    .map(ctr => ({
        get: ctr.getTeacherList,
    }));

const route = function (req, res) {
    return {
        default(nofoundHandler) {
            let reqUrl = req.url;
            let dest = router.route(req.method.toUpperCase(), reqUrl);
            if (dest.action != undefined) {
                res.statusCode = 200;
                res.setHeader('Content-Type', 'text/plain');
                dest.action(req, res, dest.pathVars);
            }
            else {
                nofoundHandler(req, res);
            }
        }
    };
};


var readFileFake = function (row, col, callback) {
    console.log(`Read ${row} ${col}`);
    callback(row, col);
}

currrifyCallback(readFileFake)(1, 2)((row, col) => console.log(`Read ${row} ${col}`));

const server = http.createServer((req, res) => {

    route(req, res).default((req, res) => {
        res.statusCode = 200;
        res.setHeader('Content-Type', 'text/plain');
        res.end('Service Not Found!\n');
    });
});


const hostname = '127.0.0.1';
const port = 3000;

server.listen(port, hostname, () => {
    console.log(`Server running at http://${hostname}:${port}/`);
});