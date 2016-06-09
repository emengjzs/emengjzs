/**
 * Created by emengjzs on 2016/5/1.
 */
/**
 * Created by emengjzs on 2016/5/1.
 */
angular.module('components')
    .component('planRecordList', {
        template: "<ng-outlet></ng-outlet>",
        bindings: {$router: '<', jobId: '<'},
        require: {
            pCtrl: '^jobDetail'
        },
        $routeConfig: [
            {path: '/records', name: 'PlanRecordListDefault', component: 'planRecordListDefault', useAsDefault: true},
            {path: '/:id', name: 'PlanRecord', component: 'planRecord'}
        ],
        controller: function () {
            var ctrl = this;
            this.$routerOnActivate = function (next) {
                ctrl.planId = next.params.id;
            }
        }
    })
    .component('planRecordListDefault', {
        templateUrl: 'analyse/planRecordList.html',
        controller: planRecordListController,
        bindings: {$router: '<'},
        require: {
            pCtrl: '^planRecordList'
        }
    });

function planRecordListController($http, URLSet, $interval, $scope) {
    var ctrl = this;

    this.$routerOnActivate = function (next) {
        ctrl.planId = ctrl.pCtrl.planId;
        console.log(ctrl.planId);
        $http.post(URLSet.ANALYSE_RECORD_LIST, {"planId": ctrl.pCtrl.planId}).success(
            function (data) {
                ctrl.recordList = data.data;
            });
    };


    this.run = function () {
        // console.log(ctrl.pCtrl.pCtrl);
        console.log(ctrl.planId);
        $http.post(URLSet.ANALYSE_PLAN_RUN, {"planId": ctrl.planId})
            .success(function (data) {
                console.log(data);
            });
        var per = 0;
        ctrl.pCtrl.pCtrl.progressController.start(2000,
            function () {
                console.log("start" + ctrl.planId);
                per = Math.min(99, per + 10);
                ctrl.pCtrl.pCtrl.progressController.setProgress(per);
                console.log("start" + ctrl.planId);
                $http.post(URLSet.ANALYSE_PLAN, {"planId": ctrl.planId})
                    .success(function (data) {
                        if (data.data.runStatus != 'RUNNING') {
                            ctrl.pCtrl.pCtrl.progressController.finish();
                            ctrl.pCtrl.pCtrl.initJobStatus('SUCCESS');
                            $http.post(URLSet.ANALYSE_RECORD_LIST, {"planId": ctrl.pCtrl.planId}).success(
                                function (data) {
                                    ctrl.recordList = data.data;
                                });
                        }
                    });
            }
        );

        ctrl.pCtrl.pCtrl.initJobStatus('RUNNING');
    };
}
