/**<script async defer onerror="javascript:void(0)"
src="https://maps.googleapis.com/maps/api/js?key=AIzaSyCFQKvF06HHSlm6BUeACn153xWu7ZQHujc&callback=initMap">
</script> **/

console.log('load google-map-api-net.js');

if(xmlHttp.access != undefined){
	drawGoogleMap(xmlHttp.access);
}
	
var listened = false;
	
//Add xmlHttp Property Listener
Object.defineProperty(xmlHttp,'access', {
		set:function(access){
			if(xmlHttp.readyState == 4){
				if(access == true){
					
					listened = true;
					drawGoogleMap(xmlHttp)
					console.log('google map api accessable');
					
				}else if(access == false){
					
					listened = true;
					console.log('google map api net error');
					drawGoogleMap(listened);
				
				}
			}
		}               	
})



function drawGoogleMap(xmlHttp){
	SyntaxHighlighter.all();
	if(listened){
		if(xmlHttp.access == true){
		    dynamicloadjs(google_map_api,initMap);
		}else{
			   console.log('show error dialog');
			   var netErrorDialog = jqueryAlert({
				'style'   : 'pc',
				'title'   : '<span style="color:red">ERR_CONNECTION_REFUSED</span>',
				'content' : '<span>Google Map API Service is Not Reachable!</span>',
				            
				'modal'   : true,
				'contentTextAlign' : 'center',
				'width'   : 'auto',
				'buttons' :{
					'Close' : function(){
						netErrorDialog.close();
						//using local google map javascript api
						
						var url = "{{site.url}}/js/maps-google-api.js";
						console.log('using local url:' +url+ '');
						dynamicloadjs(google_map_api,initMap);
					
						
					}
				}
			})
			  // netErrorDialog.show();
		
		}
	
	}
}

 