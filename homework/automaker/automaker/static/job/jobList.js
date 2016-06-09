/**
 * Created by emengjzs on 2016/4/3.
 */

angular.module('components')
    .component('jobList', {
        templateUrl: tplurl('job/jobList.html'),
        controller: JobListController,
        
    });

function JobListController($rootScope, URLSet, $http) {

    var ctrl = this;

    this.labelMap = {
        'SUCCESS': "success",
        'RUNNING': 'info',
        'FAIL': 'danger',
        'WARN': 'warning',
        'NOT_RUN': 'primary',
        'DEPRECATED': 'warning'
    };

    this.labelStatus = function (status) {
        return this.labelMap[status];
    };

    $http.get(URLSet.JOB_LIST).success(function (data) {
        ctrl.jobList = data.data;
        console.log(ctrl.jobList);
    });
    this.$routerOnActivate = function (next) {
        
    };

}