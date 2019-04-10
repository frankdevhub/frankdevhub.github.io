 console.log('load google-api-script-exist'); 
 
 var xmlHttp;
 
!function createXMLHttp(){
	    if (window.ActiveXObject) 
        {  
          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
        }  
        else if (window.XMLHttpRequest)  
        {  
          xmlHttp = new XMLHttpRequest();    
        }   
		 
	xmlHttp.access = undefined; 
}();
 
 function isExistGoogleApiScript(url,callback){
	 //test only
     //url="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js";
	 console.log('url:' +url+ '');
	 var timeout = false;
	
		//Add xmlHttp Property Listener
		Object.defineProperty(xmlHttp,'access', {
				set:function(access){
					this._access = access;
					console.log('set access property listener');
					    if( access && this._listened){
							if(typeof(callback)==='function'){
								callback();
							}
							console.log('google map api accessable');
						}else if(!access && this._listened){
							 if(typeof(callback)==='function'){
								callback();
							}
						}
				},
				get:function(){
					return this._access;
				}
			 
		});
 
		 
		console.log('add property access');
		 
        xmlHttp.open("GET",url, true);  
		  
		var timer = window.setTimeout(function(){
			   clearTimeout(timer);
			
			   console.log('timer has been time out');
			   timeout = true;
               xmlHttp.abort();
			   console.log('' +url+ '->net::ERR_NETWORK_IO_SUSPENDED');
               xmlHttp.access = false;
			   
			   //return false;
		},3000); 
		 
		 xmlHttp.onreadystatechange = function(){
			 //if(xmlHttp.readyState !== 4) continue;
					if(xmlHttp.readyState == 4){
						if(xmlHttp.status == 200){
							clearTimeout(timer);
							console.log('' +url+ '->net::access');
							xmlHttp.access = true; 
							 
							//return true;
						}else if(xmlHttp.status == 404){
							
							clearTimeout(timer);
							console.log('' +url+ '->net::404error');
							xmlHttp.access = false;
							//return false;
						}/**else{
							
							clearTimeout(timer);
						    xmlHttp.access = false;
							console.log('' +url+ '->net::error');
							return false;
						}**/
					}
	    }	 
		//start timer and wait 	
        xmlHttp.send(null); 
	 
 } 
 
function drawGoogleMap(){
	SyntaxHighlighter.all();
	if(xmlHttp){
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
						console.log('close error dialog and using local javascript api');
							
						var url = "/js/maps-google-api.js";
						console.log('using local url:' +url+ '');
						dynamicloadjs(url,initMap);
					
						
					}
				}
			})
			  // netErrorDialog.show();
		
		}
	
	}
}

 