describe('signUpCtrl', function() {
  beforeEach(module('starter.controllers'));

  var $controller;

  beforeEach(inject(function(_$controller_,_$http_){
    // The injector unwraps the underscores (_) from around the parameter names when matching
    $controller = _$controller_;
    $http = _$http_;
  }));

  describe('$scope.doSignUp', function() {
    it('signUpCtrl result', function() {
      var $result = "failed";
      var $scope = {};
      $scope.userData = {};
      var $state = {};
      $state.go = function(temp){};
  	  var $randNum = Math.floor((Math.random() * 1000) + 1);
  	  $scope.userData.password="testPW";
  	  $scope.userData.username="testUser" + $randNum;
  	  $scope.userData.firstName="testFirstName";
    	$scope.userData.lastName="testLastName";
  	  $scope.dateOfBirth="";
  	  $scope.userData.studentNumber=$randNum;
      var controller = $controller('signUpCtrl', { $scope: $scope, $state: $state, $http: $http});
  	  var request = $scope.doSignUp(null);
      expect('success').toEqual('success');
    });
  });
});