/**
 * Created by emengjzs on 2016/5/1.
 */
angular.module('components')
    .component('planConfig', {
        templateUrl: 'analyse/planConfig.html',
        controller: PlanConfigController,
        bindings: {$router: '<', jobId: '<'},
        require: {
            pCtrl: '^jobDetail'
        }
    });

function PlanConfigController($rootScope, $http, URLSet, $location) {
    var ctrl = this;
    this.HTTPTypes = ['GET', 'POST'];



    this.$routerOnActivate = function (next) {
        var job = ctrl.pCtrl.job;
        // ctrl.plan.jobId = job.id;
        ctrl.plan = {
            name: '',
            url: '/',
            httpMethod: this.HTTPTypes[0],
            param: "{}",
            modules: "",
            base: '',
            jobId: job.id,
        };
    };

    this.submit = function () {
        console.log(ctrl.plan);

        $http.post(URLSet.ANALYSE_PLAN_CREATE, ctrl.plan).success(
            function (data) {
                $rootScope.showMsg(data);
                if(data.success) {
                    $location.path('/job/' + ctrl.plan.jobId + '/analyse/list');
                }
            }
        );
    }
}
