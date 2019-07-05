package com.frankdevhub.seo.data.logging;

import com.frankdevhub.seo.message.logging.ConsoleSender;
import com.frankdevhub.seo.message.logging.ELoggerImpl;


/**
 * <p>Title:Logger.java</p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p>  
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>  
 * @author frankdevhub   
 * @date:2019-04-20 22:43
 */

public class Logger {
	private Class<?> class1;
	private org.slf4j.Logger logger;
	private ConsoleSender sender = new ConsoleSender();

	public Logger(){
		
	}
	
	public Logger(Class<?> class1) {
		this.class1 = class1;
	}

	public Logger(org.slf4j.Logger logger) {
		this.logger = logger;
	}

	public ELoggerImpl begin() {
		if (this.logger == null) {
			return new ELoggerImpl(this.sender, this.class1);
		}
		return new ELoggerImpl(this.sender, this.logger.getClass());
	}
}
