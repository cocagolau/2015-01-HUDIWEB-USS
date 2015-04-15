/**
 * Created by park on 15. 4. 4..
 */


app.controller('controllers.userTest.enneagram', ['$http', '$user', '$scope', '$timeout', function ($http, $user, $scope, $timeout) {

    $scope.user = $user;


    var test = {};
    test.a = [];
    test.a.push({question: "나는 독립적인 편이고 자기주장을 잘 한다."});
    test.a.push({question: "나는 목표를 설정하고 그 일을 추진해 나간다. 그리고 그것이 성취되기를 원한다."});
    test.a.push({question: "나는 가만히 앉아 있는 것을 좋아하지 않는다."});
    test.a.push({question: "나는 큰 일을 성취하고 영향력을 행사하기를 원한다."});
    test.a.push({question: "나는 정면 대결을 원하지는 않지만 사람들이 나를 통제하는 것도 좋아하지 않는다."});
    test.a.push({question: "대개의 경우 나는 내가 원하는 것을 잘 알고 있다."});
    test.a.push({question: "나는 일도 노는 것도 열심히 한다."});

    test.b = [];
    test.b.push({question: "나는 조용하게 혼자 있는 것을 좋아한다."});
    test.b.push({question: "나는 사회적인 활동에 주의를 쏟지 않으며 대체로 내 의견을 강하게 주장하지 않는다."});
    test.b.push({question: "나는 앞에 나서거나 다른 사람과 경쟁하는 것을 그리 좋아하지 않는다."});
    test.b.push({question: "사람들은 나를 몽상가라고 말한다."});
    test.b.push({question: "내 상상의 세계 안에서는 많은 흥미로운 일들이 벌어진다."});
    test.b.push({question: "나는 적극적이고 활동적이기라기보다는 조용한 성격이다."});

    test.c = [];
    test.c.push({question: "나는 아주 책임감이 강하고 헌신적이다."});
    test.c.push({question: "나는 내 의무를 다하지 못할 때 아주 기분이 나쁘다."});
    test.c.push({question: "나는 사람들이 필요할 때 그들을 위해 내가 그 자리에 있다는 것을 알아 주었으면 좋겠다."});
    test.c.push({question: "나는 그들을 위해 최선을 다할 것이다."});
    test.c.push({question: "이따금씩 나는 사람들이 나를 알아 주든 알아 주지 않든 그들을 위해 큰 희생을 한다."});
    test.c.push({question: "나는 내 자신을 제대로 돌보지 않는다."});
    test.c.push({question: "나는 해야 할 일을 한 다음에 시간이 나면 휴식을 취하거나 내가 원하는 일을 한다."});

    test.x = [];
    test.x.push({question: "나는 대개 긍정적인 자세로 생활하며, 모든 일이 나에게 유리한 쪽으로 풀린다고 느낀다."});
    test.x.push({question: "나는 나의 열정을 쏟을 수 있는 여러 가지 방법을 찾는다."});
    test.x.push({question: "나는 사람들과 함께 하고 사람들이 행복해지도록 돕는 것을 좋아한다."});
    test.x.push({question: "나는 나와 마찬가지로 다른 사람들도 잘 지내기를 바란다.(항상 기분이 좋은 것은 아니다. 그러나 나는 다른사람에게 그렇게 보이기를 원한다)"});
    test.x.push({question: "나는 다른 사람들에게 항상 긍정적으로 보이고자 노력하기 때문에 때로는 내 자신의 문제를 다루는 것을 미루기도 한다."});

    test.y = [];
    test.y.push({question: "나는 어떤 것에 대해 강한 감정을 갖는다."});
    test.y.push({question: "대부분의 사람들은 내가 모든 것에 대해 불만을 갖고 있다고 생각한다."});
    test.y.push({question: "나는 사람들과 함께 있을 때 그들이 어떤 사람인지, 무엇을 기대할 수 있는지를 알기 원한다."});
    test.y.push({question: "어떤 일에 내가 화가 났을 때 나는 사람들이 그것에 대해 반응하고 나만큼 그 일을 해결하려고 노력해 주기를 원한다."});
    test.y.push({question: "나는 규칙을 알고 있다. 하지만 사람들이 내게 무엇을 하라고 지시하는 것을 좋아하지 않는다."});
    test.y.push({question: "나는 내 스스로 결정하기를 원한다."});

    test.z = [];
    test.z.push({question: "나는 스스로를 잘 통제하고 논리적이다."});
    test.z.push({question: "나는 느낌을 다루는 것을 편안해하지 않는다. 나는 효율적이고 완벽하게 일을 처리하며 혼자 일하는 것을 좋아한다."});
    test.z.push({question: "문제나 개인적인 갈등이 있을 때 나는 그 상황에 감정이 끼여들지 않도록 한다."});
    test.z.push({question: "어떤 사람들은 내가 너무 차고 초자연하다고 말하지만 나는 감정 때문에 중요한 일을 그르치고 싶지 않다."});
    test.z.push({question: "나는 사람들이 나를 화나게 할 때 대부분의 경우 반응을 보이지 않는다."});

    $scope.test = test;


    $scope.done = 0;

    $scope.$watch('test', compute, true);

    function compute() {

        $scope.disabled = true;
        $timeout(function () {
            $scope.disabled = false;
        }, app.setting.questionDelay);


        $scope.done = 0;

        $scope.type = false;

        var a = 0;
        $scope.test.a.forEach(function (q) {
            if (q.answer != undefined) {
                $scope.done++;
                return;
            }
            if (q.answer == 'y')
                a++;
        });

        var b = 0;
        $scope.test.b.forEach(function (q) {
            if (q.answer != undefined) {
                $scope.done++;
                return;
            }
            if (q.answer == 'y')
                b++;
        });

        var c = 0;
        $scope.test.c.forEach(function (q) {
            if (q.answer != undefined) {
                $scope.done++;
                return;
            }
            if (q.answer == 'y')
                c++;
        });

        var x = 0;
        $scope.test.x.forEach(function (q) {
            if (q.answer != undefined) {
                $scope.done++;
                return;
            }
            if (q.answer == 'y')
                x++;
        });

        var y = 0;
        $scope.test.y.forEach(function (q) {
            if (q.answer != undefined) {
                $scope.done++;
                return;
            }
            if (q.answer == 'y')
                y++;
        });

        var z = 0;
        $scope.test.z.forEach(function (q) {
            if (q.answer != undefined) {
                $scope.done++;
                return;
            }
            if (q.answer == 'y')
                z++;
        });

        if ($scope.done != 36)
            return;

        var abc = Math.max(a, b, c);
        var abcString;
        if (abc == a)
            abcString = 'a';
        if (abc == c)
            abcString = 'c';
        if (abc == b)
            abcString = 'b';

        var xyz = Math.max(x, y, z);
        var xyzString;
        if (xyz == y)
            xyzString = 'y';
        if (xyz == x)
            xyzString = 'x';
        if (xyz == z)
            xyzString = 'z';

        var result;
        switch (abcString + xyzString) {
            case 'ax':
                result = 7;
                break;
            case 'ay':
                result = 8;
                break;
            case 'az':
                result = 3;
            case 'bx':
                result = 9;
                break;
            case 'by':
                result = 4;
                break;
            case 'bz':
                result = 5;
                break;
            case 'cx':
                result = 2;
                break;
            case 'cy':
                result = 6;
                break;
            case 'cz':
                result = 1;
                break;
        }

        var gender = app.findScope('user').user.gender;
        if (gender == 1) {
            gender = 'M'
        } else if (gender == 2) {
            gender = 'F'
        }

        $scope.type = gender + result;
    };

    $scope.showResult = function () {
        app.findScope('result').setResult("EnneaGram", $scope.type);
        var test = {};
        test.name = "EnneaGram";
        test.result = $scope.type;
        $http(req("POST", "/api/test", {test: JSON.stringify(test)})).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            app.findScope('user').refresh();
        });
    };

}]);

app.directive("enneagram", function () {
    return {
        restrict: 'E',
        templateUrl: "directive/userTest/enneagram.div",
        controller: "controllers.userTest.enneagram",
        scope: true
    }
});


