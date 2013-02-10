 var geocoder, map;
 function initialize(address) {  
   geocoder = new google.maps.Geocoder(),
   latlng = new google.maps.LatLng(0, 0),  
   mapOptions = {  
     zoom: 8,  
     center: latlng,  
     mapTypeId: google.maps.MapTypeId.ROADMAP  
   };  
   map = new google.maps.Map(document.getElementById("map"), mapOptions);  
   geocoder.geocode({ 'address': address }, function (results, status) {  
	     if (status == google.maps.GeocoderStatus.OK) {  
	       map.setCenter(results[0].geometry.location);  
	       var marker = new google.maps.Marker({  
	         map: map,  
	         position: results[0].geometry.location  
	       });  
	     }  
	   });  
 }  
 
$(function() {
	 var address = $('#street').text() + ', ' + $('#city').text();
	 initialize(address);
});
