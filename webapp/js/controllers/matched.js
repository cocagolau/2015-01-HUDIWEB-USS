/**
 * Created by park on 15. 4. 3..
 */
app.controller('controllers.matched', ['$scope', '$http', '$user', function ($scope, $http, $user, $timeout) {
    $scope.user = $user;

    $scope.refresh = function () {
        if (!$scope.user.logged)
            return;
        $http(req("GET", "/api/matched")).success(function (response) {
            if (response.error) {
                return;
            }
            $scope.matchedUsers = response.obj;
            if ($scope.matchedUsers == undefined)
                return;
            computePoint();
        });
    };


    $scope.setResult = function (type, result, user) {
        app.findScope('result').setResult(type, result, user);
    };

    var computePoint = function () {
        $scope.matchedUsers.forEach(function (matchedUser) {
            var age;
            if ($scope.user.age == undefined || matchedUser.age == undefined)
                age = 15;
            else
                age = Math.abs($scope.user.age - matchedUser.age);
            var total = 0;
            var totalLength = 0;
            if (matchedUser.factors.MBTI != undefined && $scope.user.factors.MBTI != undefined) {
                matchedUser.factors.MBTI.point = getMbtiPoint(matchedUser.factors.MBTI, $scope.user.factors.MBTI, age);
                total += matchedUser.factors.MBTI.point;
                totalLength++;
            }
            if (matchedUser.factors.EnneaGram != undefined && matchedUser.factors.EnneaGram != undefined) {
                matchedUser.factors.EnneaGram.point = getEnneaGram(matchedUser.factors.EnneaGram, $scope.user.factors.EnneaGram, age);
                total += matchedUser.factors.EnneaGram.point;
                totalLength++;
            }
            if (matchedUser.factors.LoveType != undefined && matchedUser.factors.LoveType != undefined) {
                matchedUser.factors.LoveType.point = getLoveType(matchedUser.factors.LoveType, $scope.user.factors.LoveType);
                total += matchedUser.factors.LoveType.point;
                totalLength++;
            }

            matchedUser.point = parseInt(total / totalLength);

            function getLoveType(love1, love2) {
                var result = 100;
                for (var i = 0; i < love1.types.length; i++) {
                    result -= (Math.abs(love1.types[i].percent - love2.types[i].percent)) / 10;
                }
                return getPoint(result);
            }

            function getEnneaGram(enna1, enna2, age) {
                var result;
                if (enna1.bestTypes.contains(enna2.type))
                    result = 97;
                else if (enna1.goodTypes.contains(enna2.type))
                    result = 85;
                else if (enna1.badTypes.contains(enna2.type))
                    result = 50;
                else
                    result = 70;
                return getPoint(result / (98 + age) * 100);
            }

            function getMbtiPoint(mbti1, mbti2, age) {
                var result;
                if (mbti1.bestTypes.contains(mbti2.type))
                    result = 97;
                else if (mbti1.goodTypes.contains(mbti2.type))
                    result = 80;
                else
                    result = 60;
                return getPoint(result / (98 + age) * 100);
            }

            function getPoint(point) {
                point = point * 10;
                point = parseInt(point);
                point = point / 10;
                return point;
            }

        });
    };

    $scope.ifProfile = function (user) {
        if (user.profileUrl == undefined)
            return;
        else if (user.profileUrl == "")
            return;
        else
            return 'background-image:url(' + user.profileUrl + ');  background-size: 100%;   background-position: center;';
    };


    $scope.writeLetter = function (user) {
        app.findScope('letter').writeLetter(user);
    };

    $scope.showClip = function (id) {
        app.findScope('alert').showClip(id);
    }
}]);


app.directive("matched", function () {
    return {
        restrict: 'E',
        templateUrl: "directive/matched.div",
        controller: "controllers.matched",
        scope: true
    }
});