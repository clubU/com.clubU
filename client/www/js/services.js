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

}])

.factory('FeedService', ['$http', function($http){
  var svc = {};
  svc.getEvents = function(){
    return $http.get('sampledata/events.json');
  }



  return svc;
}])

})*/


.service('conn',function($state, $http, $cordovaFileTransfer) {
	//this.url = 'http://localhost:8080/';
	this.url = 'http://ec2-54-187-175-7.us-west-2.compute.amazonaws.com:8080/';
	this.headers = {
    	"Content-Type": "application/x-www-form-urlencoded"
    };
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
	};
	this.getImg = function($path, $imgObj) {
		return $http({
			method: 'GET',
			url: this.url + $path,
			responseType: 'arraybuffer'
		}).then(function(response) {
			var binary = '';
			var bytes = new Uint8Array(response.data);
			var len = bytes.byteLength;
			for (var i = 0; i < len; i++) {
			  binary += String.fromCharCode(bytes[i]);
			}

			$imgObj.data = "data:image/png;base64," + window.btoa(binary);
		});
	};
})
.service('tempData', function() {
  this.data = {};
})
.service('userInfo', function() {
	this.username = "";
})
.service('msgbox', function($ionicPopup) {
	this.alert = function($string) {
		$ionicPopup.alert({
		title: $string
		});
	};
})
;
