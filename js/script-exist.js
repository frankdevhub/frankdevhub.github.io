 function isExistScript(url){
	 console.log('url:'+url+'');
	 //test url:https://cdn.bootcss.com/jquery/3.3.1/jquery.min.js
	 var xmlHttp ;  
	 var timeOut = false;
     setTimeout(sendXMLHttp(url),2500);	 
	 	
			   
	if(xmlHttp.readyStatus==4){
	if(xmlhttp.status==200)return false;
	else if(xmlhttp.status==404)return true;
	else return false;
    }
	return true;
 }
 
 
 function sendXMLHttp(url){
	  if (window.ActiveXObject) 
         {  
          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
         }  
         else if (window.XMLHttpRequest)  
         {  
          xmlHttp = new XMLHttpRequest();    
         }   
        xmlHttp.open("GET",url,false);  
        xmlHttp.send(null); 
        console.log('xmlHttp start sending');		
		//timer start   
	 
 }
 
 
 