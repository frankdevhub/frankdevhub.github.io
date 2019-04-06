 function isExistScript(url){
	 console.log('url:'+url+'');
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
        xmlHttp.open("GET",url,true);  
        xmlHttp.send();  
		//timer start
	    setTimeout(function(){
			timeOut = true;
			xmlHttp.abort();
		},2500);
        if(xmlHttp.readyStatus==4){
            if(xmlhttp.status==200)return true;
            else if(xmlhttp.status==404)return false;
            else return false;
        }  
	 
 }
 
 
 