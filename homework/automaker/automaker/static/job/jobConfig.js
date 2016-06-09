/**
 * Created by emengjzs on 2016/4/4.
 */
angular.module('components')
    .component('jobConfig', {
        templateUrl: 'job/jobConfig.html',
        controller: JobConfigController,

    });

function JobConfigController($rootScope, $http, URLSet, $location) {
    var ctrl = this;
    this.svnTypes = ['file:///', 'svn://', 'http://'];

    this.job = {
        name: '',
        description: '描述',
        svnAdr: '',
        svnType: this.svnTypes[0],
        automation: true,
        needSchedule: false,
        cronExp: ""
    };
    this.$routerOnActivate = function (next) {
        
    };

    this.submit = function () {
        console.log(this.job);

        $http.post(URLSet.JOB_CREATE, {
            name: this.job.name,
            description: this.job.description,
            svnAdr: this.job.svnType + this.job.svnAdr,
            svnAutoAdr: this.job.svnType + this.job.svnAdr + "-automation",
            needAuto: this.job.automation,
            needSchedule: this.job.needSchedule,
            cronExp: this.job.cronExp
        }).success(
            function (data) {
                $rootScope.showMsg(data);
                if(data.success) {
                    $location.path('/home');
                }
            }
        );

    }
}