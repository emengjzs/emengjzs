/**
 * Created by emengjzs on 2016/4/14.
 */
angular.module('components')
    .component('jobHistoryTestReport', {
        templateUrl: 'test/test.html',
        controller: JobHistoryTestReportController,
        bindings: {$router: '<', jobId: '<'},

    })
    .factory('Report', ['$resource',
        function ($resource) {
            return $resource('js/a.json', {}, {
                query: {method: 'GET', params: {}, isArray: false}
            });
        }]);


function JobHistoryTestReportController($http, URLSet, SyncHttp) {
    var ctrl = this;

    this.$routerOnActivate = function (next) {
        var q = SyncHttp.post(URLSet.JOB_TEST_REPORT, {'runHistoryId': 286});
        q.then(
            function (data) {
                ctrl.t =  angular.fromJson(data.data);
                ctrl.getPassedTests = function () {
                    return ctrl.t.passedTests.map(function (item) {
                        return item;
                    });
                }
            })
    };


}