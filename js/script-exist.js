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
			   timeout = true;
               xmlHttp.abort();
			   console.log('' +url+ '->net::ERR_NETWORK_IO_SUSPENDED');
			   return false;
		},2000); 
		 
		 xmlHttp.onreadystatechange = function(){
			 //if(xmlHttp.readyState !== 4) continue;
					if(xmlHttp.readyStatus == 4){
						if(xmlhttp.status == 200){
							clearTimeout(timer);
							return true;
						}else if(xmlhttp.status == 404){
							clearTimeout(timer);
							return false;
						}else{
							clearTimeout(timer);
							return false;
						}
					}
	    }	 
	 
		$.holdReady(true); 
		//start timer and wait 	
        xmlHttp.send(null); 
 }
 
 
 
 