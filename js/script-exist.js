 function isExistScript(url){
	 console.log('url:'+url+'');
	 //test only
	 //url="https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js";
	 var xmlHttp;  
	 var timeout = false;
	    if (window.ActiveXObject) 
         {  
          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
         }  
         else if (window.XMLHttpRequest)  
         {  
          xmlHttp = new XMLHttpRequest();    
         }   
		  
		 xmlHttp.onreadystatechange = function(){
			 //if(xmlHttp.readyState !== 4) continue;
					if(xmlHttp.readyStatus == 4){
						if(xmlhttp.status == 200){
							return true;
						}else if(xmlhttp.status == 404){
							return false;
						}else{
							return false;
						}
					}
	    }	 
	 
		var timer = setTimeout(function(){
			   timeout = true;
			   clearTimeout();
               xmlHttp.abort();
			   timeout = true;
			   return false;
		},2000); 
		 
        xmlHttp.open("GET",url,true);  
		//start timer	
        xmlHttp.send(null); 

	
		return true;
		
 }
 
 
 
 