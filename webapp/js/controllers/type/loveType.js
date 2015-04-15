/**
 * Created by park on 15. 4. 6..
 */
app.controller('controllers.userTest.loveType', ['$http', '$scope', '$user','$timeout', function ($http, $scope, $user, $timeout) {
    $scope.user = $user;

    $scope.test = [
        {"type": "R", "question": "나는 '첫눈에 반한다'는 것이 가능하다고 생각한다."},
        {"type": "B", "question": "나는 한참 지난 다음에야 비로소 내가 사랑하고 있음을 알았다."},
        {"type": "F", "question": "우리들 사이의 일이 잘 풀리지 않으면 나는 소화가 잘 되지 않는다"},
        {"type": "L", "question": "현실적인 관점에서, 나는 사랑을 고백하기 전에 먼저 나의 장래 목표부터 생각해 보지 않으면 안된다."},
        {"type": "B", "question": "먼저 좋아하는 마음이 얼마 동안 있은 다음에 비로소 사랑이 생기게 되는 것이 원칙이다."},
        {"type": "P", "question": "애인에게 나의 태도를 다소 불확실하게 해 두는 것이 언제나 좋다 "},
        {"type": "L", "question": "우리가 처음 키스하거나 볼을 비볐을 때, 나는 성기에 뚜렷한 반응(발기, 축축함)이 오는 것을 느꼈다"},
        {"type": "B", "question": "전에 연애 상대였던 사람들 거의 모두와 나는 지금도 좋은 친구관계를 유지하고 있다."},
        {"type": "L", "question": "애인을 결정하기 전에 인생 설계부터 잘 해 두는 것이 좋다."},
        {"type": "F", "question": "나는 연애에 실패한 후 너무나 우울해져 자살까지도 생각해 본 적이 있다."},
        {"type": "F", "question": "나는 사랑에 빠지면 하도 흥분되어 잠을 이루지 못하는 때가 있다"},
        {"type": "A", "question": "애인이 어려운 처지에 빠지면 설사 그가 바보처럼 행동한다 하더라도 힘껏 도와주려고 노력한다."},
        {"type": "A", "question": "애인을 고통 받게 하기보다는 차라리 내가 고통 받겠다."},
        {"type": "P", "question": "연애하는 재미란 두 사람간의 관계를 발전시키면서 동시에 내가 원하는 것을 거기서 얻어내는 재주를 시험해 보는 데 있다."},
        {"type": "P", "question": "사랑하는 애인이라면 나에 관하여 다소 모르는 것이 있다 하더라도 그것 때문에 그렇게 속상해 하지는 않을 것이다."},
        {"type": "L", "question": "비슷한 배경을 가진 사람끼리 사랑하는 것이 가장 좋다. "},
        {"type": "R", "question": "우리는 만나자 마자 서로가 좋아서 키스를 했다."},
        {"type": "F", "question": "애인이 나에게 관심을 보이지 않으면 나는 온몸이 쑤시고 아프다."},
        {"type": "A", "question": "나의 애인이 행복하지 않으면 나도 결코 행복해질 수 없다."},
        {"type": "R", "question": "대개 제일 먼저 나의 관심을 끄는 것은 그 사람의 외모이다."},
        {"type": "B", "question": "최상의 사랑은 오랜 기간의 우정으로부터 싹튼다."},
        {"type": "F", "question": "나는 사랑에 빠지면 다른 일에는 도무지 집중하기 힘들다."},
        {"type": "R", "question": "그의 손을 처음 잡았을 때 나는 사랑의 가능성을 감지했다"},
        {"type": "A", "question": "나는 어느 사람하고 헤어지고 나면 그의 좋은 점을 발견하려고 무진 애를 쓴다."},
        {"type": "F", "question": "나의 애인이 다른 사람하고 같이 있는 것 같은 생각이 들면 도저히 견딜 수 없다"},
        {"type": "P", "question": "나의 애인 두 사람이 서로 알지 못하도록 교묘하게 재주부린 적이 적어도 한 번은 있었다."},
        {"type": "P", "question": "나는 매우 쉽고 빠르게 사랑했던 관계를 잊어버릴 수 있다"},
        {"type": "L", "question": "애인을 결정하는 데 한 가지 가장 고려해야 할 점은 그가 우리 가정을 어떻게 생각하는가 하는 것이다."},
        {"type": "B", "question": "사랑에서 가장 좋은 것은 둘이 함께 살며, 함께 가정을 꾸미고 그리고 함께 아이들을 키우는 일이다 "},
        {"type": "A", "question": "애인이 원하는 것을 위해서 라면 나는 기꺼이 내 가 원하는 것을 희생시킬 수 있다"},
        {"type": "L", "question": "배우자를 결정하는 데 있어서 가장 먼저 고려해야 할 점은 그가 좋은 부모가 될 수 있겠는가 여부이다."},
        {"type": "B", "question": "키스나 포옹이나 성 관계는 서둘러서는 안된다. 그것들은 서로 충분히 친밀해지면 자연스럽게 이루어지는 것이다."},
        {"type": "R", "question": "나는 매력적인 사람들과 바람 피는 것을 좋아한다."},
        {"type": "P", "question": "나와 다른 사람들과 사이에 있었던 일을 애인이 알게 된다면 매우 속상해 할 것이다."},
        {"type": "R", "question": "나는 연애를 시작하기 전부터 나의 애인이 될 사람의 모습을 분명히 정해 놓고 있었다."},
        {"type": "A", "question": "만일 나의 애인이 다른 사람의 아기를 갖고 있다면, 나는 그 아기를 내자식처럼 키우고 사랑하며 보살펴 줄 것이다."},
        {"type": "B", "question": "우리가 언제부터 서로 사랑하게 되었는지 정확히 알 수 없다."},
        {"type": "L", "question": "나는 결혼하고 싶지 않은 사람하고는 진정한 사랑을 할 수 없을 것 같다."},
        {"type": "F", "question": "나는 질투 같은 것은 하고 싶지 않지만, 나의 애인이 다른 사람에게 관심을 가진다면 참을 수 없을 것 같다."},
        {"type": "A", "question": "내가 애인에게 방해물이 된다면, 차라리 나는 포기하겠다."},
        {"type": "R", "question": "나는 애인의 것과 똑같은 옷, 모자, 자전거. 자동차 등을 갖고 싶다"},
        {"type": "L", "question": "나는 연애하고 싶지 않은 사람하고는 데이트도 하고 싶지 않다."},
        {"type": "F", "question": "우리들의 사랑은 이미 끝났다고 생각될 때도, 그를 다시 보면 옛날 감정 이 되살아 나는 때가 적어도 한번쯤은 있었다."},
        {"type": "A", "question": "내가 가지고 있는 것은 무엇이든지 나의 애인이 마음대로 써도 좋다."},
        {"type": "F", "question": "애인이 잠시라도 나에게 무관심해지면. 나는 그의 관심을 끌기 위하여 때로는 정말 바보 같은 짓도 할 때가 있다."},
        {"type": "P", "question": "깊이 사귀고 싶지는 않아도, 어떤 상대가 나의 데이트 신청에 응하는지를 시험해 보는 것도 재미있는 일이다."},
        {"type": "L", "question": "상대를 택할 때 고려해야 할 한 가지 중요한 점은 그가 자신의 직업을 어떻게 생각하는가 하는 것이다 "},
        {"type": "A", "question": "애인과 만나거나 전화한 지 한참 되었는데도 아무 소식이 없다면, 그에게 그럴 만한 이유가 있기 때문일 것이다."},
        {"type": "L", "question": "나는 누구와 깊게 사귀기 전에 우리가 아기를 가지게 될 경우 그 쪽의 유전적 배경이 우리와 잘 맞는 지부터 먼저 생각해 본다."},
        {"type": "B", "question": "가장 좋은 연애 관계란 가장 오래 지속되는 관계이다."}];


    $scope.result = {};
    $scope.result.R = {};
    $scope.result.R.max = 7;
    $scope.result.R.title = "Passionate";
    $scope.result.R.description = "좋아하는 사람을 만나면 '첫눈에 반했다'는 생각이 들고 두 사람의 관계가 순식간에 가까워진다면 정열적인 사랑을 추구하는 사람이다. 이런 부류의 사람들은 상대방의 외모(부분적이든,전반적이든)에 많은 비중을 두는 경향이 있다. 자신의 속마음을 쉽게 털어놓고, 상대방에 대해서도 속속들이 알고 싶어한다. 남의 눈을 별로 의식하지 않고 신체적인 접촉을 즐기거나 상대방을 이상화시켜 과대평가하는 경향이 심하다. 첫눈에 황홀감을 경험하는 것만큼 헤어져야 하는 상황에서는 심한 절망감을 경험한다.";
    $scope.result.B = {};
    $scope.result.B.max = 8;
    $scope.result.B.title = "Friendship";
    $scope.result.B.description = "동료나 이성친구로 오랫동안 함께 지내는 사람에게 자기도 모르게 서서히 사랑하는 감정이 생기는 경우이다. 여기에 속하는 사람들은 상대에게 첫눈에 반해 황홀감을 느끼진 않지만 하는 일이나 취미가 비슷하고 허물없이 지내는 사람에게서 외모보다는 상호간의 신뢰감을 기반으로 사랑의 감정을 싹틔운다. 서로 잘 알고 정서적으로 안정되어 있기 때문에 서로 떨어져 있어도 초조해 하지 않으며 애정표현이 유난스럽지 않다. 비교적 덤덤한 관계를 유지하나, 결혼을 한 경우 이혼율은 낮은 편이다.";
    $scope.result.F = {};
    $scope.result.F.max = 9;
    $scope.result.F.title = "Possessive";
    $scope.result.F.description = "상대방을 완전하게 소유하는 것을 사랑이라고 생각하기 때문에 상대에게 매우 헌신적이며, 상대에게도 이를 요구한다. 상대에 대한 헌신에 보답을 받지 못하거나 배신당했을 때는 강한 분노감을 나타낸다. 사랑하고 사랑받는 것에 의해 자신의 존재가 확인되는 경향이 있기 때문에 상대에게 '사랑해?' 또는 '얼마나 좋아해'라고 확인하는 일이 많다. 이들은 사랑에 너무 많은 것을 기대하기 때문에 헤어져 있을 때 견디지 못하고 상대가 다른 이성에게 조금만 관심을 갖게 되면 심한 질투심을 느낀다.";
    $scope.result.A = {};
    $scope.result.A.max = 9;
    $scope.result.A.title = "Selfless";
    $scope.result.A.description = "소위 아가페적인 사랑, 헌신적인 사랑이다. 자기자신의 욕구보다 사랑하는 사람을 더 배려하며 사랑이란 베푸는 것이지 받는 것이 아니라고 생각한다. 따라서 상대가 자신에게 실망을 시키거나 배신한 경우마저도 실망하지 않고 자비심을 베풀며 무시하는 학대하는 상대에게도 인내심을 갖고 헌신한다. 고난과 역경에서도 인내심을 발휘한다. 상대가 떠나도 오랜 시간 동안 돌아오기를 기대하면서 참아낸다. 철저한 자기통제 이면에는 변화에 대한 두려움과 열등감이 숨겨진 경우들이 있다.";
    $scope.result.L = {};
    $scope.result.L.max = 10;
    $scope.result.L.title = "Logical";
    $scope.result.L.description = "짝을 찾을 때 경제수준이나, 학력, 가정환경, 외모 등 차후에 갈등의 소지가 될 수 있는 요인들을 사전에 고려하는 사람들이 여기에 속한다. 이 사람들은 현실적이고 이성적 사랑을 추구하기 때문에 관계가 장기적으로 지속되기 어려운 대상은 처음부터 포기한다. 상대방과 자신의 장단점을 고려하고 사랑이란 일종의 공정성에 기반을 둔 거래라고 생각하기 때문에 책임질 수 없는 불장난을 하지는 않는 경향이 있다. 사랑을 시작할 때와 마찬가지로 끝낼 때도 서로 상처받지 않고 결별할 수 있는 방법들을 모색하는 경향이 있다.";
    $scope.result.P = {};
    $scope.result.P.max = 7;
    $scope.result.P.title = "Game-Playing";
    $scope.result.P.description = "유희적 사랑을 추구하는 사람은 사랑을 책임져야 하는 것으로 보기보다는 인생을 즐기기 위한 하나의 수단으로 생각한다. 여러 가지 취미생활을 할 수 있는 것과 마찬가지로 파트너도 한 사람으로 만족하지 못하는 경향이 있다. 사랑도 게임처럼 재미있게 할 수 있어야 되기 때문에 책임감이나 의무감에 많은 비중을 두지 않는다. 섹스를 추구하기 때문에 다양하고 노련한 취향과 매너를 보여줄 수는 있으나 지나치게 친밀해지는 것을 경계하고 사랑하는 사람과 헤어져도 심한 고통을 겪지 않는다.";

    $scope.$watch('test', compute, true);
    $scope.done = 0;

    function compute() {

        $scope.disabled = true;
        $timeout(function(){
            $scope.disabled = false;
        }, app.setting.questionDelay);


        $scope.result.R.value = 0;
        $scope.result.B.value = 0;
        $scope.result.F.value = 0;
        $scope.result.A.value = 0;
        $scope.result.L.value = 0;
        $scope.result.P.value = 0;
        $scope.resultType;
        var max = 0;
        $scope.done = 0;
        $scope.test.forEach(function (q) {
            if ($scope.resultType == undefined) {
                $scope.resultType = q.type;
            }
            if (q.answer != undefined)
                $scope.done++;
            if (q.answer == 'y') {
                $scope.result[q.type].value++;
                $scope.result[q.type].percent = parseInt(($scope.result[q.type].value / $scope.result[q.type].max) * 100);
                if ($scope.result[q.type].percent > $scope.result[$scope.resultType].percent)
                    $scope.resultType = q.type;
            }

        });
        if ($scope.done != 50)
            return;

        var resultString = "";
        for (var k in $scope.result) {
            resultString += k + $scope.result[k].percent + ",";
        }
        $scope.resultValue = resultString;
    };

    $scope.showResult = function () {
        $http(req("POST", "/api/test", {
            test: JSON.stringify({
                name: "LoveType",
                result: $scope.resultValue
            })
        })).success(function (response) {
            if (response.error) {
                error(response.errorMessage);
                return;
            }
            app.findScope('user').refresh();
        });

        var resultObj = {};
        resultObj.types = [];
        resultObj.types.push(new tp($scope.result.R.percent, "R"));
        resultObj.types.push(new tp($scope.result.B.percent, "B"));
        resultObj.types.push(new tp($scope.result.F.percent, "F"));
        resultObj.types.push(new tp($scope.result.A.percent, "A"));
        resultObj.types.push(new tp($scope.result.L.percent, "L"));
        resultObj.types.push(new tp($scope.result.P.percent, "P"));
        resultObj.type = $scope.result[$scope.resultType].title;

        function tp(percent, type) {
            this.percent = percent;
            this.type = type;
        }

        app.findScope('result').setResult("LoveType", resultObj, $user);
    }

}]);

app.directive("lovetype", function () {
    return {
        restrict: 'E',
        templateUrl: "directive/userTest/loveType.div",
        controller: "controllers.userTest.loveType",
        scope: true
    }
});