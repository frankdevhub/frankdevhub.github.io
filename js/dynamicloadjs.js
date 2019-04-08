function dynamicloadjs(url){
	
   var head = document.getElementsByTagName('head')[0];
        var script = document.createElement('script');
        script.type = 'text/javascript';
        script.src = url;

        if(typeof(callback)=='function'){
            script.onload = script.onreadystatechange = function () {
                if (!this.readyState || this.readyState === "loaded" || this.readyState === "complete"){
                    callback();
                    script.onload = script.onreadystatechange = null;
                }
            };
        }
        head.appendChild(script);
		
}