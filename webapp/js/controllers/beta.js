app.controller('beta', ['$scope', '$user', '$http', function ($scope, $user, $http) {

    $scope.mod = false;
    $scope.toggle = function () {
        if ($scope.mod) {
            $scope.mod = false;
            return;
        }
        $scope.mod = true;
    }

    $scope.update = function (obj) {
        if (obj.head == "" || obj.head == undefined || obj.body == "" || obj.body == undefined) {
            return;
        }
        obj.email = $user.email;
        obj.date = new Date();
        if (typeof obj.id == 'string')
            obj.id = undefined;
        $http(req("POST", "/api/feedback", {letter: JSON.stringify(obj)})).success(function (response) {
            error("소중한 피드백 감사드립니다.");
            obj.head = "";
            obj.body = "";
            $scope.mod = false;
        });
    };

}]);