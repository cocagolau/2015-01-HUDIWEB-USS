var app = angular.module("uss", []);

app.controller('userController', function ($scope) {
    function User(name1, name2, profile_photo, company, job, phone, intro) {
        this.name1 = name1;
        this.name2 = name2;
        this.profile_photo = profile_photo;
        this.company = company;
        this.job = job;
        this.phone = phone;
        this.intro = intro;
    }

    $scope.show = function(){
      alert();
    };

    var users = $scope.users = [];

    //$scope.user = new User("FULL STACK", "WOOLIM RYU", "http://a.fod4.com/images/user_photos/1335039/b892600c3e15fb4dd218c3fe081d8b52_fullsize.jpg", "GOOGLE", "BOSS", "etc", "HI EVERYONE");

    users.push(new User("FULL STACK", "WOOLIM RYU", "http://a.fod4.com/images/user_photos/1335039/b892600c3e15fb4dd218c3fe081d8b52_fullsize.jpg", "GOOGLE", "BOSS", "etc", "HI EVERYONE"));
})
;