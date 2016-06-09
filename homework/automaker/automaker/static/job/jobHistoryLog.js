/**
 * Created by emengjzs on 2016/4/13.
 */
angular.module('components')
    .component('jobHistoryLog', {
        templateUrl: 'job/jobHistoryLog.html',
        controller: jobHistoryLogController,
        require: {
            pCtrl: '^jobHistoryDetail'
        },
        $routeConfig: [


        ],
        bindings: {$router: '<'}
    });

function jobHistoryLogController($http, URLSet) {
    var ctrl = this;
    this.$routerOnActivate = function (next) {
        console.log("jobHistoryLogController");
        $http.post(URLSet.JOB_LOG, {"runHistoryId": ctrl.pCtrl.jobHistoryId}).success(
            function (data) {
                ctrl.log = data.data;
            });
    };
}