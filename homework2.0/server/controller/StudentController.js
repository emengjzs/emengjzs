const {Student}   = require('./../dao/Student.js');
var co = require("co");

class StudentController {

    constructor() {
        
    }


    getStudentList(req, res) {
        co(function* () {
           var {rows} = yield Student.all();
           res.endJSON(rows);
        }).catch(err => {
            console.log(err);
        });
    }

    getStudentById(req, res, pathVars) {
        co(function*() {
            var {rows} = yield Student.getById(pathVars.id);
            res.endJSON(rows);
        }).catch(err => {
            console.log(err);
        });
    }

    getTeacherList(req, res) {
        res.end("teacher!");
    }

    deleteStudent(req, res) {
        res.end("ddd");
    }
}

exports.studentController = new StudentController();