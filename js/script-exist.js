 function isExistScript(url){
	 var xmlHttp ;  
	 var timeOut = false;
        if (window.ActiveXObject)  
         {  
          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
         }  
         else if (window.XMLHttpRequest)  
         {  
          xmlHttp = new XMLHttpRequest();    
         }   
        xmlHttp.open("GET",url,false);  
        xmlHttp.send();  
		//timer start
	    setTimeOut(function(){
			timeOut = true;
			xmlHttp.abort();
		},2000);
        if(xmlHttp.readyStatus==4){
            if(xmlhttp.status==200)return true;
            else if(xmlhttp.status==404)return false;
            else return false;
        }  
        return false;  
        else  
        return true;  
	 
 }
 
 
 