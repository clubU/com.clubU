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
  var clubsInfo = [
    {
        "id" : 1,
        "name" : "Taiwanese Student Association",
        "description" : "The Taiwan Republic of China Student Association at the University of Toronto (ROCSAUT) at UTSG aims at providing a platform for students with similar backgrounds or students who are interested in the Taiwanese culture to engage in events to meet new people and network.",
        "image" : "sampledata/img/rocsaut.jpg"
    },
    {
        "id" : 2,
        "name" : "International Cantonese Union",
        "description" : "The International Cantonese Union is a student organization at the University of Toronto, St George campus. We serve people who are interested in CANTONESE",
        "image" : "sampledata/img/icu.jpg"
    },
    {
        "id" : 3,
        "name" : "881 Drama Club",
        "description" : "The official fan page for the largest Mandarin speaking drama club at the University of Toronto. The club was established in 2001 as a sub-club of ROCSAUT",
        "image" : "sampledata/img/881.jpg"
    },
    {
        "id" : 4,
        "name" : "Magic Glasses Magician Club",
        "description" : "The closer you look, the less you see.",
        "image" : "sampledata/img/glasses.jpg"
    }
  ];

  var unsubscribedClub =    {
    c5: {
        "id" : 5,
        "name" : "Jogging club",
        "description" : "We love to jog! Join us every Wednesday event at 7pm!",
        "image" : "sampledata/img/jogging.jpg"
    },
    c6: {
        "id" : 6,
        "name" : "Mountain Climbing club",
        "description" : "We are a group of mountain climbing enthusiasts!",
        "image" : "sampledata/img/mountain.jpg"
    },
    c7: {
        "id" : 7,
        "name" : "Swimming club",
        "description" : "Come swim with us! Become the next Michael Phelps!",
        "image" : "sampledata/img/swimming.jpg"
    }
  };
  var eventsInfo = [
    {
        "id" : 1,
        "title" : "OTP",
        "time" : "Jan. 20, 2017",
        "description" : "Orientation event for the winter term",
        "location" : "40 St. George Street",
        "image" : "sampledata/img/OTP.jpg"
    },
    {
        "id" : 2,
        "title" : "Mentorship Program Orientation",
        "time" : "Feb 17, 2017",
        "description" : "This is event 2",
        "location" : "40 St. George Street",
        "image" : "sampledata/img/mentor.jpg"
    },
    {
        "id" : 3,
        "title" : "Semi-Formal",
        "time" : "Apr. 27, 2017",
        "description" : "Annual semi-formal event! Come and enjoy good food, music, and win prizes! ",
        "location" : "Eaton Chelsea Hotel",
        "image" : "sampledata/img/event2.jpg"
    },
    {
        "id" : 4,
        "title" : "Star Wars Movie Night",
        "time" : "May 4, 2017",
        "description" : "May the force be with you.",
        "location" : "TBD",
        "image" : "sampledata/img/starwars.jpg"
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
  $httpBackend.whenGET(/.*event\/1/, function() {return true;}). respond(eventsInfo);
  $httpBackend.whenGET(/.*club\/recommendations/, function() {return true;}). respond(unsubscribedClub);
  $httpBackend.whenGET(/.*student/, function() {return true;}). respond({"clubs": clubsInfo});
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
        templateUrl: 'templates/search.html',
        controller: 'searchCtrl'
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

  .state('app.club_side_profile', {
    url: '/club_side_profile',
    views: {
      'menuContent': {
        templateUrl: 'templates/club_side_profile.html',
        controller: 'ClubProfileCtrl'
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

  .state('app.edit_profile', {
    url: '/edit_profile',
    views: {
      'menuContent': {
        templateUrl: 'templates/edit_profile.html',
        controller: 'UserCtrl'
      }
    }
  })

  .state('app.edit_club_profile', {
    url: '/edit_club_profile',
    views: {
      'menuContent': {
        templateUrl: 'templates/edit_club_profile.html',
        controller: 'EditClubProfileCtrl'
      }
    }
  })

  .state('app.create_event', {
    url: '/create_event',
    views: {
      'menuContent': {
        templateUrl: 'templates/create_edit_event.html',
        controller: 'CreateEventCtrl'
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
