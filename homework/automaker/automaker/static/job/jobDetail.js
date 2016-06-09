/**
 * Created by emengjzs on 2016/4/5.
 */
angular.module('components')
    .component('jobDetail', {
        templateUrl: tplurl('job/jobDetail.html'),
        controller: JobDetailController,
        bindings: {
            $router: '<',
            jobId: '@'
        },
        $routeConfig: [
            {path: '/', name: 'JobHistoryList', component: 'jobHistoryList', useAsDefault: true},
            {path: '/config', name: 'JobConfig', component: 'jobConfig'},
            {path: '/log', name: 'JobLog', component: 'jobLog'},
            {path: '/history/:hid', name: 'JobHistoryDetail', component: 'jobHistoryDetail'},
            {path: '/history/:hid/...', name: 'JobHistoryDetail', component: 'jobHistoryDetail'},
            {path: '/analyse/...', name: 'Plan', component: 'plan'},
            

        ]
    });

function JobDetailController($http, URLSet, $interval, $scope, $location) {
    var ctrl = this;
    this.runButtonStatus = null;
    this.runButtonText = null;
 
    // this.jobId = null;
    this.runProgress = 0;
    this.jobRunFeedBack = null;
    this.startTime = null;
    this.log = ["LOG"];



    this.jobStatusMap = {
        NOT_RUN: {
            runButtonStatus: 'primary',
            text: '启动功能测试'
        },
        RUNNING: {runButtonStatus: 'info disabled', text: '正在运行'},
        SUCCESS: {runButtonStatus: 'primary', text: '启动功能测试'},
        WARN: {runButtonStatus: 'primary', text: '启动功能测试'},
        FAIL: {runButtonStatus: 'primary', text: '启动功能测试'},
        DEPRECATED: {runButtonStatus: 'primary disabled', text: '已禁用'}
    };


    this.initJobStatus = function (status) {
        var jobStatus = ctrl.jobStatusMap[status];
        ctrl.runButtonStatus = jobStatus.runButtonStatus;
        ctrl.runButtonText = jobStatus.text;
        if (status == 'RUNNING') {

        }

    };

    this.updateJobData = function () {
        $http.post(URLSet.JOB_DETAIL, {"jobId": ctrl.jobId}).success(
            function (data) {
                ctrl.job = data.data;
                ctrl.initJobStatus(ctrl.job.status);
                ctrl.labelStatus = function (status) {
                    return ctrl.labelMap[status];
                };
            });
    };

    this.$routerOnActivate = function (next) {
        console.log("jobDetail init!");
        ctrl.progressActive ='';
        ctrl.jobId = next.params.id;
        ctrl.updateJobData();
        $scope.log = ctrl.log;
    };

    this.runJob = function () {
        ctrl.initJobStatus('RUNNING');
        $http.post(URLSet.JOB_RUN, {"jobId": ctrl.jobId}).success(ctrl.progressStart)
    };

    this.progressStart = function (data) {
        ctrl.jobRunFeedBack = data.data;
        ctrl.startTime = new Date();
        ctrl.log.length = 0;

        ctrl.progressController.start(2000,
            function () {

                var current = new Date();
                ctrl.runProgress = Math.min(99, (current - ctrl.startTime) / ctrl.jobRunFeedBack.predictRunTime * 100);
                ctrl.checkJobStatus();

            }
        );
        $location.path('/job/' + ctrl.jobId + "/log")

    };

    this.checkJobStatus = function () {
        $http.post(URLSet.JOB_RUN_INFO, {"runHistoryId": ctrl.jobRunFeedBack.runHistoryId})
            .success(function (data) {
                data = data.data;
                if (data.log !== null) {

                    for (var i =0, length = data.log.length; i < length; i ++ ) {
                        ctrl.log.push(data.log[i]);
                    }
                }

                if (data.status == 'SUCCESS') {
                    ctrl.progressController.finish();

                }
                ctrl.initJobStatus(data.status);
            })
    };

    this.labelMap = {
        'SUCCESS': "success",
        'RUNNING': 'info',
        'FAIL': 'danger',
        'WARN': 'warn',
        'NOT_RUN': 'primary',
        'DEPRECATED': 'warning'
    };



    this.progressController = function () {
        var progressTimer = null;
        var impl = {
            setProgress: function (percentage) {
                ctrl.runProgress = percentage;
            },
            start: function (timegap, updateProgress) {
                ctrl.runProgress = 0;
                ctrl.progressActive = "active";
                progressTimer = $interval(updateProgress, timegap)
            },

            finish: function () {
                ctrl.runProgress = 100;
                ctrl.progressActive = "";
                $interval.cancel(progressTimer);

            }
        };
        return impl;
    }();

    this.disableJob = function () {
        var url = ctrl.job.status == 'DEPRECATED' ? URLSet.JOB_ENABLE: URLSet.JOB_DISABLE;
        $http.post(url, {"jobId": ctrl.jobId})
            .success(function (data) {
                ctrl.updateJobData();
        });
    }
}