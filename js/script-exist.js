 !function isExistScript(url){
	 var xmlHttp ;  
        if (window.ActiveXObject)  
         {  
          xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");  
         }  
         else if (window.XMLHttpRequest)  
         {  
          xmlHttp = new XMLHttpRequest();    
         }   
        xmlHttp.open("post",url,false);  
        xmlHttp.send();  
        if(xmlHttp.readyStatus==4){
            if(xmlhttp.status==200)return true;
            else if(xmlhttp.status==404)return false;
            else return false;
        }  
        return false;  
        else  
        return true;  
	 
 }
 
 
 