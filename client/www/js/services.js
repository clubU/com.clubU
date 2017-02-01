angular.module('starter.services', [])


// Clubs services
/*.factory('BackendService', function($http){

  var svc = {};
  svc.getClubs = function(){
    return $http.get('sampledata/clubs.json');
  }

  svc.shuffleArray = function(){

    var m = svc.length, t, i;

    // While there remain elements to shuffle
    while (m) {
      // Pick a remaining element…
      i = Math.floor(Math.random() * m--);

      // And swap it with the current element.
      t = svc[m];
      svc[m] = svc[i];
      svc[i] = t;
    }
  }
  return svc;

})*/

.service('conn',function($state, $http) {
	this.url = 'http://localhost:8080/';
	this.headers = {
    	"Content-Type": "application/x-www-form-urlencoded"
    }
	this.dataTrans = function($method, $data, $path) {
    var capsul = {
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
    };

    if ($method == "GET") {
      delete capsul.transformRequest;
    }

		return $http(capsul);
	}
})
.service('tempData', function() {
  this.data = {};
})
;
