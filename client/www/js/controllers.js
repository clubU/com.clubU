var hostname = "http://localhost:8080/";

angular.module('starter.controllers', ['starter.services','ngCordova','ionic.contrib.ui.hscrollcards'])

.controller('AppCtrl', function($scope, $ionicModal, $timeout, $state, $window, $location, conn, userInfo) {
  $scope.doRefresh = function() {
    $scope.logout = function() {
      $location.path('/start');
      $window.location.reload();
    }

    $scope.profImg = {};
    $scope.profImg.data = "../img/blankProfile.jpeg";
    conn.dataTrans("GET", null, "student?username=" + userInfo.username).success(function(response) {
      if (response.image) {
        var $profId = response.image.id;
        conn.getImg("image/" + $profId, $scope.profImg);
      }
    }).finally(function(){
        $scope.$broadcast('scroll.refreshComplete');
      });
  }

  $scope.doRefresh();

})
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

    conn.dataTrans("POST", $data, "session").success(function(response) {
      userInfo.username = $data.username;
      userInfo.id = response.userId;
      userInfo.type = $data.type;
console.log(userInfo);
      conn.dataTrans("GET", null, "student/" + userInfo.id).success(function(response) {
      	userInfo.imageId = response.image? response.image.id : null;
console.log(userInfo);
      });

      $state.go('app.feed');
    })
    .error(function(data) {
    	msgbox.alert("Login Failed");
    });

  }
})
.controller('clubLoginCtrl', function($scope, $state, $http, $ionicSideMenuDelegate, conn, userInfo, msgbox) {
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

	  conn.dataTrans("POST", $data, "session").success(function(response) {
	  	userInfo.username = $data.username;
	  	userInfo.clubId = response.userId;
	  	userInfo.type = $data.type;
      	$state.go('app.club_side_profile');
      })
	  .error(function() {
	  	msgbox.alert("Login Failed");
	  });

    }

})

.controller('signUpCtrl', function($scope, $state, $http, conn, msgbox, $ionicSideMenuDelegate) {
  $ionicSideMenuDelegate.canDragContent(false);
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
	    $state.go('app.start');
	})
	.error(function(data) {
		msgbox.alert("Signup Failed");
	});
  };

})
.controller('clubSignUpCtrl', function($scope, $state, $http, conn, userInfo, msgbox, $ionicSideMenuDelegate) {
  $ionicSideMenuDelegate.canDragContent(false);
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
	  	$state.go('app.start');
  	})
  	.error(function() {
  		msgbox.alert("Signup Failed");
  	});
  };

})

.controller('UserCtrl', function($scope, conn, userInfo) {
	$scope.doRefresh = function ()  {
		conn.dataTrans("GET", null, "student?username=" + userInfo.username).success(function(response) {
			$scope.info = {
				firstName: response.firstName,
				lastName: response.lastName,
				userName: response.username,
				email: response.email,
				program: response.programOfStudy,
				year: response.yearOfStudy
			};

			$scope.profImg = {};
			if (!response.image)
				$scope.profImg.data = "../img/blankProfile.jpeg";
			else {
				var $profId = response.image.id;
				conn.getImg("image/" + $profId, $scope.profImg);
			}
		}).finally(function(){
			$scope.$broadcast('scroll.refreshComplete');
		});
	}

    $scope.doRefresh();
})

.controller('EditProfileCtrl', function($scope, $state, $http, $cordovaCamera, conn, userInfo) {
	$scope.data = {};
  $scope.openPhotoLibrary = function($action) {
       var options = {
           quality: 100,
           destinationType: Camera.DestinationType.FILE_URI,
           sourceType: Camera.PictureSourceType[$action],
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
           $cordovaFileTransfer.upload(conn.url + "image", targetPath, options).then(function(result) {
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
 	$scope.update = function() {
		conn.dataTrans("POST", $scope.data, "student/" + userInfo.id)
		.success(function(data) {
			$state.go('app.user');
		});
 	}
})

.controller ('FeedCtrl', function($scope, $state, $http, conn, userInfo) {
  $scope.doRefresh = function(){

	conn.dataTrans("GET", null, "student?username=" + userInfo.username)
	.success(function(data) {
	  var $events = [];
	  var $image = {};
	  data.clubs.forEach(function(club) {
	  	if (!club.image)
	  		return;

	  	var $clubImgId = club.image.id;
	  	if (!$image[$clubImgId]) {
	  		var picObj = {};
	  		$image[$clubImgId] = picObj;
	  		conn.getImg("image/" + $clubImgId, picObj);
	  	}
	  	club.events.forEach(function(event) {
	  		event.time = new Date(event.time);
	  		if (!event.image)
	  			return;
	  		var $eventImgId = event.image.id;
		  	if (!$image[$eventImgId]) {
		  		var picObj = {};
		  		$image[$eventImgId] = picObj;
		  		conn.getImg("image/" + $eventImgId, picObj);
		  	}
		  	event.eventImg = $image[$eventImgId];
		  	event.clubImg = $image[$clubImgId];

	  		$events.push(event);
	  	})
	  });

	  $events.sort(function(event1, event2) {
	  	return event1.time - event2.time;
	  });

	  $scope.events = $events;


	}).finally(function(){
      $scope.$broadcast('scroll.refreshComplete');
    });

	conn.dataTrans("GET", null, "club/recommendations?forStudentUsername=" + userInfo.username)
	.success(function(data) {
		$scope.clubs = data;
		data.forEach(function(club) {
			if (club.image) {
				var $clubImgId = club.image.id;
				conn.getImg("image/" + $clubImgId, club.image);
			}
		});
  	})

  };


  $scope.doRefresh();


})

.controller('ClubsCtrl', function($scope, $ionicActionSheet, conn, tempData, userInfo) {
  $scope.loading = false;
  $scope.doRefresh = function(){
  	$scope.loading = true;
    conn.dataTrans("GET", null, "student?username=" + userInfo.username).success(function(newItems) {
      $scope.loading = false;
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

.controller('ClubCtrl', function($scope, $stateParams, conn, userInfo) {
  var $clubInfo = {};
  href = window.location.href;
  clubId = href.match(/.*\/(\d+)$/)[1];
  var $subStat = false;

	var subUpdate = function () {
		if ($subStat) {
			$scope.btnText = 'Subscribed';
			$scope.buttonColour = "button-balanced";
		} else {
			$scope.btnText = 'Subscribe';
			$scope.buttonColour = "button-positive";
		}
	}

	conn.dataTrans("GET", null, "student?username=" + userInfo.username).success(function(response) {
		response.clubs.forEach(function(club) {
			if (club.id == clubId) {
				$subStat = true;
			}

		});

		subUpdate();
	});

  conn.dataTrans("GET", null, "club/" + clubId).success(function(data) {
    $clubInfo = data;
    $scope.clubName = $clubInfo.name;
    $scope.clubDesc = $clubInfo.description;
	var $clubImgId = $clubInfo.image.id;
	$scope.image = {};
	conn.getImg("image/" + $clubImgId, $scope.image);
	$scope.events = $clubInfo.events;
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
    if (!$subStat) {
	    conn.dataTrans("POST", {studentUsername: userInfo.username, clubId: $clubInfo.id},"subscription").success(function() {
	      console.log("subcribe successfully");
	    });
	} else {

	}

  	$subStat = !$subStat;
    subUpdate();

  }
})

.controller('ClubProfileCtrl', function($scope, $ionicActionSheet, $http, $ionicSideMenuDelegate, conn, userInfo) {
  $ionicSideMenuDelegate.canDragContent(false)
  $scope.eventData = {};
  var $clubInfo = {};
  clubId = userInfo.clubId;
	$scope.btnText = 'Subscribed';
	$scope.buttonColour = "button-balanced";

  conn.dataTrans("GET", null, "club/" + clubId).success(function(data) {
    $clubInfo = data;
    $scope.clubName = $clubInfo.name;
    $scope.clubDesc = $clubInfo.description;
	var $clubImgId = $clubInfo.image.id;
	$scope.image = {};
	conn.getImg("image/" + $clubImgId, $scope.image);
	$scope.events = $clubInfo.events;
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
})

.controller('searchCtrl', function($scope, conn, tempData) {
	$scope.searchWord = "";
  $scope.searchMsg = "Searching";
	$scope.loading = false;
	$scope.search = function() {
		$scope.loading = true;
		conn.dataTrans("GET", null, "club/search?keyword=" + $scope.searchWord).success(function(clubs) {
	    	$scope.clubs = clubs;
        $scope.loading = false;
	    });
	};

	$scope.getClub = function(id){
		tempData.clubId = id;
	};
})

.controller('EditClubProfileCtrl', function($scope, $ionicActionSheet, $http, $cordovaCamera, $ionicSideMenuDelegate, conn, userInfo, $state) {
  $scope.data = {};
  $ionicSideMenuDelegate.canDragContent(false)
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

  // Add funcitons for edit profile here
 	$scope.update = function() {
		conn.dataTrans("POST", $scope.data, "club/" + userInfo.clubId)
		.success(function(data) {
			$state.go('app.club_side_profile');
		});
 	}
})

.controller('CreateEventCtrl', function($scope, $state, $ionicActionSheet, $http, $cordovaCamera, $ionicSideMenuDelegate, userInfo, conn) {
  $ionicSideMenuDelegate.canDragContent(false);
  $scope.data = {};
  $scope.openPhotoLibrary = function() {
       var options = {
           quality: 100,
           destinationType: Camera.DestinationType.FILE_URI,
           sourceType: Camera.PictureSourceType.PHOTOLIBRARY,
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


     $scope.update = function() {
     	var $time = $scope.data.time.getTime() + $scope.data.date.getTime();
     	var $data = {
     		clubId: userInfo.clubId,
     		title: $scope.data.title,
     		time: $time,
     		location: $scope.data.location,
     		description: $scope.data.desc
     	}

     	conn.dataTrans("POST", $data, "event")
     	.success(function(response) {
     		$state.go('app.club_side_profile');
     	});

     }
})

.controller('EventCtrl', function($scope, $ionicActionSheet, $http, $ionicSideMenuDelegate) {
  $ionicSideMenuDelegate.canDragContent(false);

});
