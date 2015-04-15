/**
 * Created by park on 15. 4. 4..
 */
app.controller('wrapController', ['$scope', '$user', '$toggle', '$http', '$anchorScroll', '$location', '$timeout', function ($scope, $user, $toggle, $http, $anchorScroll, $location, $timeout) {
    $scope.user = $user;
    $scope.toggle = $toggle;

    $toggle.test = true;
    $toggle.profile = true;
    $toggle.letter = true;
    $toggle.hideDone = true;


    $scope.move = function (id) {
        app.scroll(id);
    };

    $scope.logout = function () {
        error("Begin Again!");
        var user = {email: "", password: "", logged: false};
        $scope.user.email = "";
        angular.copy(user, $scope.user);
        $http(req("GET", "/api/user/logout")).success(function (response) {
            FB.logout(function (response) {
            });
            $timeout(function () {
                location.reload();
            }, 1000);
        });
    };


    $scope.mailTo = function () {
        location.href = "mailto:parksungho86@gmail.com=문의";
    };

    $scope.nextError = function () {
        app.findScope('alert').nextError();
    };


    app.scroll = function (x) {
        $timeout(function () {
            var newHash = x;
            if ($location.hash() !== newHash) {
                $location.hash(x);
                $anchorScroll();
            } else {
                $anchorScroll();
            }
        }, 300);
    };


}]);