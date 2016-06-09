/**
 * Created by emengjzs on 2016/4/7.
 */
angular.module('components')
    .component('jobHistoryList', {
        templateUrl: 'job/jobHistoryList.html',
        controller: JobHistoryListController,
        bindings: {$router: '<', jobId: '<'},
        require: {
            pCtrl: '^jobDetail'
        }
    });

function JobHistoryListController($http, URLSet) {

    var ctrl = this;
    this.$routerOnActivate = function (next) {

        $http.post(URLSet.JOB_RUN_LIST, {"jobId": ctrl.pCtrl.jobId}).success(
            function (data) {
                ctrl.jobHistoryList = data.data;
                ctrl.job = ctrl.pCtrl.job;
            });

    };

    this.labelMap = {
        'SUCCESS': "success",
        'RUNNING': 'info',
        'FAIL': 'danger',
        'WARN': 'warn'
    };

    this.labelStatus = function (status) {
        return this.labelMap[status];
    };

    this.pageNo = 1;

    this.getJobHistoryList = function (i) {
        this.pageNo = i;
        $http.post(URLSet.JOB_RUN_LIST, {"jobId": ctrl.pCtrl.jobId, "pageNo": i}).success(
            function (data) {
                ctrl.jobHistoryList = data.data;
                ctrl.job = ctrl.pCtrl.job;
            }
        );
    }

}
