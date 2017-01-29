angular.module('starter.services', [])

// Clubs services
.factory('BackendService', ['$http', function($http){

  var svc = {};

  svc.getClubs = function(){
    return $http.get('sampledata/clubs.json');
  }

  return svc;

}])
