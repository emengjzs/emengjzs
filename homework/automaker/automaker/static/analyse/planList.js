/**
 * Created by emengjzs on 2016/5/1.
 */
angular.module('components')
    .component('plan', {
        template: "<ng-outlet></ng-outlet>",
        bindings: {$router: '<', jobId: '<'},
        require: {
            pCtrl: '^jobDetail'
        },
        $routeConfig: [
            {path: '/list', name: 'PlanList', component: 'planList', useAsDefault: true},
            {path: '/:id/...', name: 'PlanRecordList', component: 'planRecordList'},
            {path: '/config', name: 'PlanConfig', component: 'planConfig'}
        ]

    })
    .component('planList', {
        templateUrl: "analyse/planList.html",
        bindings: {$router: '<', jobId: '<'},
        require: {
            pCtrl: '^jobDetail'
        },
        controller: planListController
    });



    function planListController($http, URLSet, $interval, $scope) {
        var ctrl = this;

        this.$routerOnActivate = function (next) {
            console.log("dfff");
            $http.post(URLSet.ANALYSE_PLAN_LIST, {"jobId": ctrl.pCtrl.jobId}).success(
                function (data) {
                    ctrl.planList = data.data;
                    ctrl.job = ctrl.pCtrl.job;
                });

        };

        this.labelMap = {
            'SUCCESS': "success",
            'RUNNING': 'info',
            'FAIL': 'danger',
            'WARN': 'warn',
            'NOT_READY': 'primary'
        };

        this.labelStatus = function (status) {
            return this.labelMap[status];
        };


    }
