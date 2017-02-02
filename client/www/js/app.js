// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('starter', ['ionic', 'starter.controllers','ngMockE2E'])

.run(function($ionicPlatform, $httpBackend) {

  $ionicPlatform.ready(function() {
    // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
    // for form inputs)
    if (window.cordova && window.cordova.plugins.Keyboard) {
      cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
      cordova.plugins.Keyboard.disableScroll(true);

    }
    if (window.StatusBar) {
      // org.apache.cordova.statusbar required
      StatusBar.styleDefault();
    }
  });
//////////////////////////////////////////////////////////////////////////////httpbackend test data
  var clubsInfo = {
    c1: {
        "id" : 1,
        "name" : "ROCSAUT",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "image" : "sampledata/img/rocsaut.jpg"
    },
    c2: {
        "id" : 2,
        "name" : "ICU",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "image" : "sampledata/img/icu.jpg"
    },
    c3: {
        "id" : 3,
        "name" : "881",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "image" : "sampledata/img/881.jpg"
    },
    c4: {
        "id" : 4,
        "name" : "Magic Glasses",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "image" : "sampledata/img/glasses.jpg"
    }
  };

  var unsubscribedClub =    {
    c5: {
        "id" : 5,
        "name" : "Jogging club",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "image" : "sampledata/img/jogging.jpg"
    },
    c6: {
        "id" : 6,
        "name" : "Mountain Climbing club",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "image" : "sampledata/img/mountain.jpg"
    },
    c7: {
        "id" : 7,
        "name" : "Swimming club",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "image" : "sampledata/img/swimming.jpg"
    }
  };
  var eventsInfo = [
    {
        "id" : 1,
        "title" : "OTP",
        "time" : "Sept 20, 2017",
        "description" : "OTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTPOTP",
        "location" : "40 St George",
        "image" : "sampledata/img/OTP.jpg"
    },
    {
        "id" : 2,
        "title" : "Event 2",
        "time" : "June 9, 1969",
        "description" : "This is event 2",
        "location" : "Mars",
        "image" : "sampledata/img/event2.jpg"
    },
    {
        "id" : 3,
        "title" : "Event 3",
        "time" : "August 7, 1994",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "location" : "tbd",
        "image" : "sampledata/img/event3.jpg"
    },
    {
        "id" : 4,
        "title" : "Event 4",
        "time" : "May 5, 2017",
        "description" : "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Ut, nesciunt hic aut? Saepe nihil autem nesciunt, ab quisquam animi, aperiam fugit? Ut velit a, in perspiciatis error inventore. Dolorum, eligendi.",
        "location" : "secret",
        "image" : "sampledata/img/event4.jpg"
    }

  ];

  $httpBackend.whenGET(/templates\/.*/).passThrough();
  $httpBackend.whenPOST(/.*session/).respond(200);
  $httpBackend.whenGET(/.*club\/\d/, function() {return true;}).respond(function(method,url) {
    $num = url.match(/.*\/(\d)$/)[1];
    $target = clubsInfo["c" + $num];
    if (!$target)
    	$target = unsubscribedClub["c" + $num];
    return [200,$target];
  });
  $httpBackend.whenGET(/.*event/, function() {return true;}). respond(eventsInfo);
  $httpBackend.whenGET(/.*club\/recommendations/, function() {return true;}). respond(unsubscribedClub);
  $httpBackend.whenGET(/.*student/, function() {return true;}). respond(clubsInfo);
  $httpBackend.whenPOST(/.*subscription/, function() {return true;}).respond(function(method,url,data) {
    console.log(data);
    $numStr = "c" + data.match(/.*(\d)$/)[1];
    $target = unsubscribedClub[$numStr];
    if ($target) {
    	clubsInfo[$numStr] = $target;
    	delete unsubscribedClub[$numStr];
    }
console.log(unsubscribedClub);
  });
})

.config(function($stateProvider, $urlRouterProvider) {
  $stateProvider

    .state('app', {
    url: '/app',
    abstract: true,
    templateUrl: 'templates/menu.html',
    controller: 'AppCtrl'
  })

  .state('app.start', {
    url: '/start',
    views: {
      'menuContent': {
        templateUrl: 'templates/start.html'
      }
    }
  })

  .state('app.feed', {
    url: '/home_feed',
    views: {
      'menuContent': {
        templateUrl: 'templates/home_feed.html',
        controller: 'FeedCtrl'
      }
    }
  })

  .state('app.search', {
    url: '/search',
    views: {
      'menuContent': {
        templateUrl: 'templates/search.html'
      }
    }
  })

  .state('app.login', {
    url: '/login',
    views: {
      'menuContent': {
        templateUrl: 'templates/login.html',
        controller: 'loginCtrl'
      }
    }
  })
  .state('app.club_login', {
    url: '/club_login',
    views: {
      'menuContent': {
        templateUrl: 'templates/club_login.html',
        controller: 'clubLoginCtrl'
      }
    }
  })

  .state('app.signup', {
      url: '/signup',
      views: {
        'menuContent': {
          templateUrl: 'templates/signup.html',
          controller: 'signUpCtrl'
        }
      }
  })

  .state('app.club_signup', {
      url: '/club_signup',
      views: {
        'menuContent': {
          templateUrl: 'templates/club_signup.html',
          controller: 'clubSignUpCtrl'
        }
      }
  })

  .state('app.browse', {
      url: '/browse',
      views: {
        'menuContent': {
          templateUrl: 'templates/browse.html'
        }
      }
    })

  .state('app.user', {
    url: '/user',
    views: {
      'menuContent': {
        templateUrl: 'templates/user.html',
        controller: 'UserCtrl'
      }
    }
  })

  .state('app.clubs', {
    url: '/clubs',
    views: {
      'menuContent': {
        templateUrl: 'templates/clubs.html',
        controller: 'ClubsCtrl'
      }
    }
  })

  .state('app.single', {
    url: '/clubs/:id',
    views: {
      'menuContent': {
        templateUrl: 'templates/club.html',
        controller: 'ClubCtrl'
      }
    }
  });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/start');
});
