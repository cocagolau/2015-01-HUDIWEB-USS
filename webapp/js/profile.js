var app = angular.module("uss", ['ngRoute']);


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


app.controller('userController', function ($scope, $req, $routeParams, $timeout) {
    $scope.user = {};
    $req('/api/user', {stringId: location.pathname.substring(1)}).success(
        function (response) {
            $scope.user = response;
        }
    ).error(function () {
            alert("없는아이디에요.");
        });
});
