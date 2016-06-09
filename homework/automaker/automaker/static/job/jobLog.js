/**
 * Created by emengjzs on 2016/4/9.
 */
angular.module('components')
    .component('jobLog', {
        templateUrl: 'job/jobLog.html',
        controller: JobLogController,
        bindings: {$router: '<'},
        require: {
           pCtrl: '^jobDetail'
        }
    });

function JobLogController($http, $scope, URLSet) {
    var ctrl = this;
    ctrl.log = null;
    ctrl.staticLog = ["static log"];
    ctrl.isRunning  = null;
    this.$routerOnActivate = function (next) {
        ctrl.isRunning = ctrl.pCtrl.job.status;
        ctrl.log = ctrl.pCtrl.log;
        console.log(ctrl.pCtrl);
        
        
        
    };

    
}
