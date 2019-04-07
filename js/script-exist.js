 var xmlHttp;  
 
 function isExistScript(url){
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
		 
		 
        xmlHttp.open("GET",url,true);  
		  
		var timer = setTimeout(function(){
			   console.log('timer has been time out');
			   timeout = true;
               xmlHttp.abort();
			   console.log('' +url+ '->net::ERR_NETWORK_IO_SUSPENDED');
			   
			   $.holdReady(false); 
			   
			   return false;
		},2000); 
		 
		 xmlHttp.onreadystatechange = function(){
			 //if(xmlHttp.readyState !== 4) continue;
					if(xmlHttp.readyStatus == 4){
						if(xmlhttp.status == 200){
							console.log('' +url+ '->net::access');
							clearTimeout(timer);
							$.holdReady(false); 
							return true;
						}else if(xmlhttp.status == 404){
							console.log('' +url+ '->net::404error');
							clearTimeout(timer);
							return false;
						}else{
							console.log('' +url+ '->net::error');
							clearTimeout(timer);
							return false;
						}
						
						$.holdReady(false); 
					}
	    }	 
	 
		$.holdReady(true); 
		//start timer and wait 	
        xmlHttp.send(null); 
 }
 
 