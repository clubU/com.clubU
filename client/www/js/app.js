// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js

//angular.module('starter', ['ionic', 'starter.controllers','ngMockE2E'])
//.run(function($ionicPlatform, $httpBackend) {
angular.module('starter', ['ionic', 'starter.controllers'])
.run(function($ionicPlatform) {
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
        controller: 'EditProfileCtrl'
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

  .state('app.event', {
    url: '/event/:id',
    views: {
      'menuContent': {
        templateUrl: 'templates/event.html',
        controller: 'EventCtrl'
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
  })
  .state('app.edit_event', {
    url: '/edit_event',
    views: {
      'menuContent': {
        templateUrl: 'templates/create_edit_event.html',
        controller: 'EditEventCtrl'
      }
    }
  });
  // if none of the above states are matched, use this as the fallback
  $urlRouterProvider.otherwise('/app/start');
});
