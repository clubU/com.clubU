angular.module('starter.services', [])


// Clubs services
.factory('BackendService', ['$http', function($http){

  var svc = {};

  svc.getClubs = function(){
    return $http.get('sampledata/clubs.json');
  }

  return svc;

}])

.service('conn',function($state, $http) {
	this.url = 'http://localhost:8080/';
	this.headers = {
    	"Content-Type": "application/x-www-form-urlencoded"
    }
	this.dataTrans = function($method, $data, $path) {
		return $http({
			method: $method,
			url: this.url + $path,
			headers: this.headers,
			data: $data,
			transformRequest: function(obj) {
				var str = [];
				for(var p in obj)
					str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
				return str.join("&");
			}
		});
	}
})
;
