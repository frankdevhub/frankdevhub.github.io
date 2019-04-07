 var xmlHttp;  
 
 function isExistScript(){
	 //test only
     url="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js";
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
		 
		 
        xmlHttp.open("GET",url, true);  
		  
		var timer = window.setTimeout(function(){
			   clearTimeout(timer);
			
			   console.log('timer has been time out');
			   timeout = true;
               xmlHttp.abort();
			   console.log('' +url+ '->net::ERR_NETWORK_IO_SUSPENDED');

			   return false;
		},3000); 
		 
		 xmlHttp.onreadystatechange = function(){
			 //if(xmlHttp.readyState !== 4) continue;
					if(xmlHttp.readyState == 4){
						if(xmlHttp.status == 200){
							console.log('' +url+ '->net::access');
							 
							return true;
						}else if(xmlHttp.status == 404){
							
							console.log('' +url+ '->net::404error');
							return false;
						}else{
						
							console.log('' +url+ '->net::error');
							return false;
						}
							clearTimeout(timer);
					}
	    }	 
		//start timer and wait 	
        xmlHttp.send(null); 
	 
 } 
 

 