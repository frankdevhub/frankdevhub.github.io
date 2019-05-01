package com.frankdevhub.test.localfiles;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

/**
 * <p>Title:LargeFileCopyTest.java</p>  
 * <p>Description: </p>  
 * <p>Copyright: Copyright (c) 2019</p>  
 * <p>Company: www.frankdevhub.site</p>
 * <p>github: https://github.com/frankdevhub</p>  
 * @author frankdevhub   
 * @date:2019-04-24 11:59
 */

public class LargeFileCopyPrepare {

	private static final String FILE_DIR = "D:/googleTest";

	public static void copy(int times) throws IOException {
		File source = new File(FILE_DIR).listFiles()[0];
		// String sourceName = source.getName();
		String fileName = source.getName();
		// System.out.println(sourceName);
		for (int i = 0; i < times; i++) {
			String prefix = fileName.substring(0, fileName.lastIndexOf("."));
			String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
			String newFileName = prefix + Integer.toString(i+1)+"." + suffix;
            System.out.println(newFileName);
			
			File dest = new File(FILE_DIR + File.separator+newFileName);
			System.out.println(dest.getAbsolutePath());
			FileUtils.copyFile(source, dest);
		}

	}

	public static void main(String[] args) throws IOException {
		/*long start = System.currentTimeMillis();
		copy(5000);
		long end = System.currentTimeMillis();
		
		System.out.println(String.format("cost time mills:%s", end-start));*/
		long cost = 132081;
		//System.out.println(cost/(60*1000));
	}
	
}
