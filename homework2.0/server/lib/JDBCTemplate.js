const {dbConfig}         = require('./../config/Config.js');
var mysql                = require('mysql');

exports.query = function(sql, params = []) {
    return new Promise((res, rej) => {
        var connection = mysql.createConnection(dbConfig);
        connection.connect();
        connection.query(sql, params, function(err, rows, fields) {
            if (err) {
                console.log(err);
                rej(err);
            }
            res({err, rows, fields});
        });
        connection.end();
    });
}

