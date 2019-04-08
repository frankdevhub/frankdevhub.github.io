function dynamicloadcss(url){
	
	var head = documents.getElementsByTagName('head')[0];
	var link = document.createElement('link');
	
	link.type = 'type/css';
	link.rel = 'stylesheet';
	link.href = url;
	
	head.appendChild(link);
}