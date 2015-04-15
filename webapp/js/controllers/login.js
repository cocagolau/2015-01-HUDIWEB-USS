/**
 * Created by park on 15. 4. 1..
 */

app.controller('controllers.login', ['$scope', '$http', '$user', '$timeout', function ($scope, $http, $user, $timeout) {
    $scope.user = $user;

    $scope.$watch(function () {
        return $scope.user.email;
    }, function () {
        if (!$scope.check.email())
            return;
        $scope.registeredEmail = false;
        $scope.checking = true;
        clearTimeout(this.ajax);
        this.ajax = setTimeout(function () {
            $http(req("POST", "/api/user/registeredEmail", {email: $scope.user.email})).success(function (response) {
                $scope.checking = false;
                if ($scope.user.email == response)
                    $scope.registeredEmail = true;
            });
        }, 500);
    });

    $scope.check = {
        email: function () {
            var regex = /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/i;
            return regex.test($scope.user.email);
        },
        password: function () {
            return /^[\w\W]{4,}$/i.test($scope.user.password);
        },
        all: function () {
            if (!$scope.check.password())
                return false;
            if (!$scope.check.email())
                return false;
            return true;
        }
    }

    $scope.send = false;

    $scope.register = function () {
        if (!$scope.check.all()) {
            error("입력값을 확인해주세요!");
            return;
        }
        if ($scope.send) {
            error("처리중입니다. 삐리삐리.");
            return;
        }
        $scope.send = true;

        $http(req("POST", "/api/user", {user: JSON.stringify($scope.user)})).success(function (response) {
            $scope.send = false;
            if (response.error) {
                error(response.errorMessage);
                $scope.user.logged = false;
                return;
            }
            angular.copy(response.obj, $scope.user);
            $scope.user.logged = true;
        });
    };

    $scope.facebookLogin = function () {
        FB.login(function (response) {
            if (response.authResponse) {
                console.log('Welcome!  Fetching your information.... ');
                FB.api('/me', function (response) {
                    console.log('Good to see you, ' + response.name + '.');

                    var user = {};
                    user.email = response.id + "@facebook.com";
                    user.password = response.id;
                    user.authEmail = true;
                    if (response.gender == 'male')
                        user.gender = 1;
                    if (response.gender == 'female')
                        user.gender = 2;
                    user.nickName = response.first_name;
                    user.profileUrl = "http://graph.facebook.com/" + response.id + "/picture";
                    app.findScope('login').user = user;
                    app.findScope('login').fbregister();
                });
            } else {
                console.log('User cancelled login or did not fully authorize.');
            }
        });
    };

    $scope.fbregister = function () {
        if ($scope.send) {
            error("처리중입니다. 삐리삐리.");
            return;
        }
        $scope.send = true;
        $http(req("POST", "/api/user/fblogin", {user: JSON.stringify($scope.user)})).success(function (response) {
            $scope.send = false;
            if (response.error) {
                error(response.errorMessage);
                $scope.user.logged = false;
                return;
            }
            angular.copy(response.obj, $scope.user);
            $scope.user.logged = true;
            app.findScope('user').refresh();
        });
    };


    $scope.login = function () {
        if (!$scope.check.all()) {
            error("입력값을 확인해주세요!");
            return;
        }

        if ($scope.send) {
            error("처리중입니다. 삐리삐리.");
            return;
        }
        $scope.send = true;

        $http(req("POST", "/api/user/login", {user: JSON.stringify($scope.user)})).success(function (response) {
            $scope.send = false;
            if (response.error) {
                error(response.errorMessage);
                $scope.user.logged = false;
                return;
            }
            angular.copy(response.obj, $scope.user);
            $timeout(function () {
                app.findScope('user').refresh();
            }, 300);
            $scope.user.logged = true;
        });
    };

    $scope.passwordRedefine = function () {
        if ($scope.send) {
            error("처리중입니다. 삐리삐리.");
            return;
        }
        $scope.send = true;
        $http(req("GET", "/api/passwordRedefine?email=" + $scope.user.email)).success(function (response) {
            $scope.send = false
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            error("이메일을 확인해주세요.");
        });
    };


    $scope.testLogin = function () {
        var tests =
            ["taehee@begin.again", "sumin@begin.again", "songe@begin.again", "leehyuk@begin.again", "jobin@begin.again", "dongmin@begin.again"];
        $scope.user.email = tests[parseInt(Math.random() * 5.99)];
        $scope.user.password = "begin";

        $scope.login();
    };

}]);

app.directive("login", function () {
    return {
        restrict: 'E',
        templateUrl: "directive/login.div",
        controller: "controllers.login",
        scope: true
    }
});