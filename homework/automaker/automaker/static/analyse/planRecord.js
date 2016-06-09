/**
 * Created by emengjzs on 2016/5/1.
 */
angular.module('components')
    .component('planRecord', {
        templateUrl: 'analyse/planRecord.html',
        controller: planRecordController,
        bindings: {$router: '<', jobId: '<'},
        require: {
            pCtrl: '^jobDetail'
        }
    });

function planRecordController($http, URLSet, $interval, $scope) {
    var ctrl = this;

    this.$routerOnActivate = function (next) {
  
        ctrl.planRecordId = next.params.id;
        $http.post(URLSet.ANALYSE_RECORD, {"id": ctrl.planRecordId}).success(
            function (data) {
                ctrl.record = data.data;
                ctrl.report =  angular.fromJson(ctrl.record.report);
                console.log(ctrl.report );
            });
    };



}
