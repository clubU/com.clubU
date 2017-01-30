var hostname = "http://localhost:8080/";

angular.module('starter.controllers', ['starter.services'])


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

.controller('loginCtrl', function($scope, $state, $http, conn) {
    $scope.loginData = {};

    $scope.goToSignUp = function() {
      $state.go('app.signup');
    };

    $scope.doLogin = function() {
	  	var $data = {
	  		username: $scope.loginData.username,
	  		password: $scope.loginData.password,
	  		type: "1"
	  	}

	  	conn.dataTrans("POST", $data, "session");
        $state.go('app.feed');
    }

})
.controller('clubLoginCtrl', function($scope, $state, $http, conn) {
    $scope.loginData = {};

    $scope.goToSignUp = function() {
      $state.go('app.signup');
    };

    $scope.doLogin = function() {
	  	var $data = {
	  		username: $scope.loginData.username,
	  		password: $scope.loginData.password,
	  		type: "2"
	  	}

	  	conn.dataTrans("POST", $data, "session");
        $state.go('app.feed');
    }

})

.controller('signUpCtrl', function($scope, $state, $http, conn) {
  $scope.userData = {};
  $scope.doSignUp = function (form) {
  	var $data = {
        password: $scope.userData.password,
        username: $scope.userData.username,
        firstName: $scope.userData.firstName,
        lastName: $scope.userData.lastName,
        programOfStudy: $scope.userData.fieldOfStudy,
        studentNumber: $scope.userData.studentNumber,
        email: $scope.userData.email
	};

	conn.dataTrans("POST", $data, "student")
	.success(function(data) {
	    $state.go('app.user');
	});
  };

})
.controller('clubSignUpCtrl', function($scope, $state, $http, conn) {
  $scope.userData = {};
  $scope.doClubSignUp = function (form) {
  	var $data = {
  		username: $scope.userData.username,
  		password: $scope.userData.password,
  		name: $scope.userData.clubName,
  		email: $scope.userData.email
  	}

  	conn.dataTrans("POST", $data, "club");
  	$state.go('app.user');
  };

})

.controller('UserCtrl', function($scope) {
  $scope.info = {
    firstName: 'Spongebob',
    lastName: 'Cornelia',
    userName: 'student1',
    email: 'email@lol.com',
    program: 'ECE',
    year: '4',
  };
})

.controller ('FeedCtrl', function($scope, $state, $http, FeedService) {
  $scope.doRefresh = function(){
    FeedService.getEvents().success(function(newItems) {
      $scope.events = newItems;
    })
    .finally(function(){
      $scope.$broadcast('scroll.refreshComplete');
    });
  };
  $scope.doRefresh();

  //$scope.doRefresh();
})

.controller('ClubsCtrl', function($scope, $ionicActionSheet, BackendService) {

  $scope.doRefresh = function(){
    BackendService.getClubs().success(function(newItems) {
      $scope.clubs = newItems;
    })
    .finally(function() {
      // Stop the ion-refresher from spinning (not needed in this view)
      $scope.$broadcast('scroll.refreshComplete');
    });
  };

  $scope.doRefresh();
  $scope.getRandomIndex = function(length){
    return Math.floor(Math.random() * length);
  };

  $scope.getClub = function(id){
    alert(id);
  };

})

.controller('ClubCtrl', function($scope, $stateParams) {

  $scope.name = 'ROCSAUT';
  $scope.about = 'The Taiwan Republic of China Student Association at the University of Toronto (ROCSAUT) at UTSG aims at providing a platform for students with similar backgrounds or students who are interested in the Taiwanese culture to engage in events to meet new people and network. 多倫多大學台灣同學會歡迎不管是台灣背景還是對台灣文化有興趣的同學參加我們社團!';

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
