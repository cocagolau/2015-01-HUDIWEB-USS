/**
 * Created by park on 15. 4. 26..
 */

var app = angular.module('register', []);


app.findScope = function (selector) {
    return angular.element(document.querySelector("[ng-controller='" + selector + "']")).scope();
};

app.factory('$req', function ($http) {
    var req = function (url, data, method) {
        if (method == undefined)
            method = "GET";
        if (data != undefined) {
            url += "?";
            url += parse(data);
        }
        function parse(obj) {
            var str = [];
            for (var p in obj)
                str.push(encodeURIComponent(p) + "="
                + encodeURIComponent(obj[p]));
            return str.join("&");
        }

        var http = $http({
            method: method,
            url: url,
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        });


        var success = http.success;
        http.onResponse = function (call) {
            success(function (response) {
                if (response.error) {
                    alert(response.errorMessage);
                    return;
                }
                call(response.response);
            });
        };

        return http;
    };
    return req;
});

app.controller('userController', function ($scope, $req) {
    $scope.user = {};

    $scope.submit = function () {
        $req('/api/user', {user: JSON.stringify($scope.user)}, "POST").success(
            function (response) {
                alert("가입되었습니다.");
            }
        )
    };


});
