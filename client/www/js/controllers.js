var hostname = "http://localhost:8080/";

angular.module('starter.controllers', [])

.controller('AppCtrl', function($scope, $ionicModal, $timeout, $state) {

  // With the new view caching in Ionic, Controllers are only called
  // when they are recreated or on app start, instead of every page change.
  // To listen for when this page is active (for example, to refresh data),
  // listen for the $ionicView.enter event:
  //$scope.$on('$ionicView.enter', function(e) {
  //});
/*
  // Form data for the login modal
  $scope.loginData = {};

  // Create the login modal that we will use later
  $ionicModal.fromTemplateUrl('templates/login.html', {
    scope: $scope
  }).then(function(modal) {
    $scope.modal = modal;
  });

  // Triggered in the login modal to close it
  $scope.closeLogin = function() {
    $scope.modal.hide();
  };

  // Open the login modal
  $scope.login = function() {
    $scope.modal.show();
  };

  // Go to signup page
  $scope.goToSignUp = function() {
    $scope.modal.hide();
    $state.go('app.signup');
  };

  // Perform the login action when the user submits the login form
  $scope.doLogin = function() {
    console.log('Doing login', $scope.loginData);

    // Simulate a login delay. Remove this and replace with your login
    // code if using a login system
    $timeout(function() {
      $scope.closeLogin();
    }, 1000);

    $state.go('app.user');
  };
*/
})

.controller('loginCtrl', function($scope, $state, $http) {
    $scope.loginData = {};

    $scope.goToSignUp = function() {
      $state.go('app.signup');
    };

    $scope.doLogin = function() {
        console.log("LOGIN user: " + $scope.loginData.username + " - PW: " + $scope.loginData.password);
        $state.go('app.clubs');
    }

})
.controller('clubLoginCtrl', function($scope, $state, $http) {
    $scope.loginData = {};

    $scope.goToSignUp = function() {
      $state.go('app.club_signup');
    };

    $scope.doLogin = function (form) {

      var request = $http({
        method: 'POST',
        url: 'http://localhost:8080/user',
        headers: {
          "Content-Type": "application/x-www-form-urlencoded"
        },
        data: {
          username: $scope.loginData.username,
          password: $scopy.loginData.password
        },


      });
        console.log("LOGIN club: " + $scope.loginData.username + " - PW: " + $scope.loginData.password);
        $state.go('app.user');
    }

})

.controller('signUpCtrl', function($scope, $state, $http) {
  $scope.userData = {};
  $scope.doSignUp = function (form) {

    var request = $http({
      method: 'POST',
      url: 'http://localhost:8080/user',
      headers: {
        "Content-Type": "application/x-www-form-urlencoded"
      },
      data: {
        password: $scope.userData.password,
        username: $scope.userData.username,
        firstName: $scope.userData.firstName,
        lastName: $scope.userData.lastName,
        dateOfBirth: $scope.dateOfBirth,
        studentId: $scope.userData.studentNumber,
        email: "keke"
      },
      transformRequest: function(obj) {
        var str = [];
        for(var p in obj)
        str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
        return str.join("&");
      }
    });

    $state.go('app.user');
    request.success(function(data){

    })

  };

})
.controller('clubSignUpCtrl', function($scope, $state, $http) {
  $scope.userData = {};
  $scope.doClubSignUp = function (form) {
   var request = $http({
      method : 'POST',
      url : 'http://localhost:8080/user',
//      crossDomain:  true,
      data : {password: $scope.userData.password, username: $scope.username, firstName: $scope.userData.firstName, lastName: $scope.userData.lastName, dateOfBirth: $scope.dateOfBirth, studentId: $scope.userData.studentNumber, email: "keke"},
      header : {}
    });
    console.log("SIGNUP user: " + $scope.userData.username + " - PW: " + $scope.userData.password + " - Username: " + $scope.userData.username + " - DOB " + $scope.userData.dateOfBirth);
    $state.go('app.user');
    request.success(function(data){
      if (data == '1'){
        alert("congrats");
      }
      else {
        alert("boooo");
      }
    })

  };

})

.controller('ClubsCtrl', function($scope) {
  $scope.clubs = [
    { title: 'ROCSAUT', id: 1 },
    { title: '881', id: 2 },
    { title: 'ICU', id: 3 },
    { title: 'EngSoc', id: 4 }
  ];
})

.controller('UserCtrl', function($scope) {

})

.controller('ClubCtrl', function($scope, $stateParams) {
  $scope.name = 'ROCSAUT';
  $scope.description = 'The Taiwan Republic of China Student Association at the University of Toronto (ROCSAUT) at UTSG aims at providing a platform for students with similar backgrounds or students who are interested in the Taiwanese culture to engage in events to meet new people and network. 多倫多大學台灣同學會歡迎不管是台灣背景還是對台灣文化有興趣的同學參加我們社團!';

  $scope.events = [
    { title: 'OTP',
      time: 'September 20, 2017',
      desc: 'Orientation to welcome new comers',
      location: 'ES1055'
    },
    {
      title: 'Mix n Mingle',
      time: 'October 3, 2017',
      desc: 'come meet new people',
      location: 'GB404'
    },
    {
      title: 'Semi-Formal',
      time: 'Jan. 27, 2017',
      desc: 'Dress up and come eat or something',
      location: 'Eaton Chelsea'
    }
  ];
  /*
   * if given group is the selected group, deselect it
   * else, select the given group
   */
  $scope.toggleItem= function(item) {
    if ($scope.isItemShown(item)) {
      $scope.shownItem = null;
    } else {
      $scope.shownItem = item;
    }
  };
  $scope.isItemShown = function(item) {
    return $scope.shownItem === item;
  };
});
