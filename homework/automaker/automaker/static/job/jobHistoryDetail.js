/**
 * Created by emengjzs on 2016/4/13.
 */
angular.module('components')
    .component('jobHistoryDetail', {
        templateUrl: 'job/jobHistoryDetail.html',
        controller: JobHistoryDetailController,
        bindings: {$router: '<', jobId: '<'},
        require: {
            pCtrl: '^jobDetail'
        },
        $routeConfig: [
            {path: '/log', name: 'JobHistoryLog', component: 'jobHistoryLog'},
            {path: '/report', name: 'JobHistoryTestReport', component: 'jobHistoryTestReport', useAsDefault: true}
        ]
    });

function JobHistoryDetailController($http, URLSet, SyncHttp) {
    var ctrl = this;
    this.$routerOnActivate = function (next) {
        console.log("JobHistoryDetailController init!");
        ctrl.jobHistoryId = next.params.hid;

        ctrl.getJobHistoryDetail();
        console.log("JobHistoryDetailController done!");

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

    this.getJobHistoryDetail = function () {
        var promise = SyncHttp.post(URLSet.JOB_RUN_DETAIL,
            {"runHistoryId": ctrl.jobHistoryId});
        promise.then(
            function (data) {
                ctrl.jobHistory = data.data;
                console.log(ctrl.jobHistory);
                console.log("JobHistoryDetailController sss");
            }
        );
    }
}
        
        
 