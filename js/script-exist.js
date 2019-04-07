 var xmlHttp;  
 
 function isExistScript(url){
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
						}else{
							
							clearTimeout(timer);
						    xmlHttp.access = false;
							console.log('' +url+ '->net::error');
							return false;
						}
					}
	    }	 
		//start timer and wait 	
        xmlHttp.send(null); 
	 
 } 
 

 