var hostname = "http://localhost:8080/";

angular.module('starter.controllers', ['starter.services','ngCordova','ionic.contrib.ui.cards'])

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


.controller('loginCtrl', function($scope, $state, $http, $ionicSideMenuDelegate, conn, userInfo, msgbox) {
  $ionicSideMenuDelegate.canDragContent(false)
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

    conn.dataTrans("POST", $data, "session").success(function() {
      userInfo.username = $data.username;
      $state.go('app.feed');
    });
  }


})
.controller('clubLoginCtrl', function($scope, $state, $http, $ionicSideMenuDelegate, conn) {
    $ionicSideMenuDelegate.canDragContent(false);
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

	  	conn.dataTrans("POST", $data, "session").success(function() {

      });
      $state.go('app.club_side_profile');
    }

})

.controller('signUpCtrl', function($scope, $state, $http, conn,msgbox) {
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
	})
	.error(function(data) {
		msgbox.alert("sign up failed");
	});
  };

})
.controller('clubSignUpCtrl', function($scope, $state, $http, conn, userInfo) {
  $scope.userData = {};
  $scope.doClubSignUp = function (form) {
  	var $data = {
  		username: $scope.userData.username,
  		password: $scope.userData.password,
  		name: $scope.userData.clubName,
  		email: $scope.userData.email
  	}

  	conn.dataTrans("POST", $data, "club")
  	.success(function(data) {
  		userInfo.username = $data.username;
	  	$state.go('app.user');
  	});
  };

})

.controller('UserCtrl', function($scope, conn, userInfo) {
    conn.dataTrans("GET", userInfo.username, "student").success(function(response) {
        $scope.info = {
		    firstName: response.firstName,
		    lastName: response.lastName,
		    userName: response.username,
		    email: response.email,
		    program: response.programOfStudy,
		    year: response.yearOfStudy
		};
    })
})

.controller('EditProfileCtrl', function($scope, $state, $http, $cordovaCamera) {
  $scope.openPhotoLibrary = function() {
       var options = {
           quality: 100,
           destinationType: Camera.DestinationType.FILE_URI,
           sourceType: Camera.PictureSourceType.CAMERA,
           allowEdit: true,
           encodingType: Camera.EncodingType.JPEG,
           popoverOptions: CameraPopoverOptions,
           saveToPhotoAlbum: false
       };

       $cordovaCamera.getPicture(options).then(function(imageData) {

           //console.log(imageData);
           //console.log(options);

          // var url = "http://mydomein.com/upload.php";
           //target path may be local or url
           var targetPath = imageData;
           var filename = targetPath.split("/").pop();
           var options = {
               fileKey: "file",
               fileName: filename,
               chunkedMode: false,
               mimeType: "image/jpg"
           };
           $cordovaFileTransfer.upload(url, targetPath, options).then(function(result) {
               console.log("SUCCESS: " + JSON.stringify(result.response));
               alert("success");
               alert(JSON.stringify(result.response));
           }, function(err) {
               console.log("ERROR: " + JSON.stringify(err));
               alert(JSON.stringify(err));
           }, function (progress) {
               // constant progress updates
               $timeout(function () {
                   $scope.downloadProgress = (progress.loaded / progress.total) * 100;
               })
           });

       }, function(err) {
           // error
           console.log(err);
       });
     }
  // enter edit user function here
})

.controller ('FeedCtrl', function($scope, $state, $http, conn, userInfo) {
  $scope.doRefresh = function(){
	conn.dataTrans("GET", userInfo.username, "student")
	.success(function(data) {
	  $scope.user = data;
	  $scope.events = [];
	  var $allEventPromise = [];
	  data.clubs.forEach(function(club) {
	    var dataPromise = conn.dataTrans("GET", null, "event/" + club.id)
	    .success(function(events) {
	      $scope.events = $scope.events.concat(events);
	    });

	    $allEventPromise.push(dataPromise);
	  });

	  Promise.all($allEventPromise).then(function(data) {
		  $scope.events.sort(function(event1_, event2_) {
		  	var event1 = new Date(event1_);
		  	var event2 = new Date(event2_);
		  	if (event1 > event2)
		  		return 1;
		  	else if (event1 == event2)
		  		return 0;
		  	else
			  	return -1;
		  });
	  });

	}).finally(function(){
      $scope.$broadcast('scroll.refreshComplete');
    });


	conn.dataTrans("GET", userInfo.username, "club/recommendations")
	.success(function(data) {
		$scope.clubs = data;
  	})

  };


  $scope.doRefresh();


})

.controller('ClubsCtrl', function($scope, $ionicActionSheet, conn, tempData, userInfo) {

  $scope.doRefresh = function(){
    conn.dataTrans("GET", userInfo.username, "student").success(function(newItems) {
      $scope.clubs = newItems.clubs;
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
    tempData.clubId = id;
  };


})

.controller('ClubCtrl', function($scope, $stateParams, conn, tempData) {


  $scope.btnText = 'Subscribe';
  $scope.buttonColour = "button-positive";

  var $clubInfo = {};
  href = window.location.href;
  clubId = href.match(/.*\/(\d)$/)[1];
  conn.dataTrans("GET", null, "club/" + clubId).success(function(data) {
    $clubInfo = data;
    $scope.image = $clubInfo.image;
    $scope.name = $clubInfo.name;
    $scope.about = $clubInfo.description;
  });


  $eventList = {};
  conn.dataTrans("GET", null, "event/" + clubId).success(function(data) {
    $eventList = data;
    $scope.events = $eventList;
  });

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

  $scope.subscribe = function() {
    $scope.btnText = 'Subscribed';
    $scope.buttonColour = "button-balanced";

    conn.dataTrans("POST", {studentUsername: "temptest", clubId: $clubInfo.id},"subscription").success(function() {
      console.log("subcribe successfully");
    });
  }
})

.controller('ClubProfileCtrl', function($scope, $ionicActionSheet, $http) {

  $scope.eventData = {};
  $scope.addEvent = function(form){
    var $data = {
      title: $scope.eventData.title,
      date: $scope.eventData.date,
      desc: $scope.eventData.description
    };
    $http.post('sampledata/events.json', $scope.eventData);
  };

})

.controller('searchCtrl', function($scope, conn) {
	$scope.searchWord = "";
	$scope.search = function() {
		conn.dataTrans("GET", $scope.searchWord, "club").success(function(clubs) {
	    	$scope.clubs = clubs;
	    });
	}

	$scope.getClub = function(id){
		tempData.clubId = id;
	};
})

.controller('EditClubProfileCtrl', function($scope, $ionicActionSheet, $http) {

  // Add funcitons for edit profile here

})

.controller('CreateEventCtrl', function($scope, $ionicActionSheet, $http) {

})

.controller('EventCtrl', function($scope, $ionicActionSheet, $http) {

});
