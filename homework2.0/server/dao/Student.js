var jdbcTpl = require('./../lib/JDBCTemplate.js');

exports.Student = {

    // 
    all() {
        return jdbcTpl.query("select * from student");
    },


    getById(id) {
        return jdbcTpl.query("select * from student where sid = ?", id);
    }
}



