 function isExistScript(url){
	 console.log('url:'+url+'');
	 //test url:https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js
	 var xmlHttp;  
	 if (window.ActiveXObject) 
         {  
          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
         }  
         else if (window.XMLHttpRequest)  
         {  
          xmlHttp = new XMLHttpRequest();    
         }   
        xmlHttp.open("GET",url,true);  
		//start timer
		var timer = setTimeout(function(){
               xmlHttp.abort();
		},2500);	 
        xmlHttp.send(null); 
        console.log('xmlHttp start sending');	 
	    
		xmlHttp.onreadystatechange = function(){
				if(xmlHttp.readyStatus==4){
					if(xmlhttp.status==200)return true;
					else if(xmlhttp.status==404)return false;
					else return false;
				}
				return false;
		}	 
			   

 }
 
 
 
 