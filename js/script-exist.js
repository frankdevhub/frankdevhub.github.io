 console.log('load script-exist'); 

 var listened = false;
 
 function isExistScript(url,callback){
	 //test only
     //url="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js";
	 console.log('url:' +url+ '');
	 var timeout = false;
	    if (window.ActiveXObject) 
         {  
          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
         }  
         else if (window.XMLHttpRequest)  
         {  
          xmlHttp = new XMLHttpRequest();    
         }   
		 
		//Add xmlHttp Property Listener
		Object.defineProperty(xmlHttp,'access', {
				set:function(access){
					this._access = access;
					console.log('set access property listener');
					if(this._readyState == 4){
						if(this._access == true){
							
							listened = true;
							if(typeof(callback)==='function'){
								callback();
							}
							console.log('google map api accessable');
							
						}else if(access == false){
							
							listened = true;
							console.log('google map api net error');
                            if(typeof(callback)==='function'){
								callback();
							}
						
						}
					}
				},
				get:function(){
					return this._access;
				}
			 
		});
 
		 
		console.log('add property access');
		xmlHttp.access = undefined;
		 
        xmlHttp.open("GET",url, true);  
		  
		var timer = window.setTimeout(function(){
			   clearTimeout(timer);
			
			   console.log('timer has been time out');
			   timeout = true;
               xmlHttp.abort();
			   console.log('' +url+ '->net::ERR_NETWORK_IO_SUSPENDED');
               xmlHttp.access = false;
			   
			   return false;
		},3000); 
		 
		 xmlHttp.onreadystatechange = function(){
			 //if(xmlHttp.readyState !== 4) continue;
					if(xmlHttp.readyState == 4){
						if(xmlHttp.status == 200){
							clearTimeout(timer);
							console.log('' +url+ '->net::access');
							xmlHttp.access = true; 
							 
							return true;
						}else if(xmlHttp.status == 404){
							
							clearTimeout(timer);
							console.log('' +url+ '->net::404error');
							xmlHttp.access = false;
							return false;
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
 

 