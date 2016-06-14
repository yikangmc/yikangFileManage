package com.yikangyiliao.base.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.Inet4Address;
import java.util.Properties;

import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.context.support.StaticApplicationContext;


/**
 * @author liushuaic
 * @date 2015/12/10 11:55
 * @desc system配置信息
 * **/
public class SystemProperties {
	
	private static Properties systemProperties=new Properties();
	
	public static String FilePath = "";
	public static Integer  multiple = 1;
	
	static {
	 try {
			InputStream  inputStream=SystemProperties.class.getResource("/system.properties").openStream();
			systemProperties.load(inputStream);
			
		    FilePath = SystemProperties.getPropertieValue("FilePath");
		   
		    multiple = Integer.parseInt(SystemProperties.getPropertieValue("multiple"));
			 
		    
			
	 	} catch (IOException e) {
				e.printStackTrace();
		}
	}
	
	/**
	 * @author liushuaic
	 * @date 2015/12/01 11:54
	 * @desc 获取systemProperties 文件中的值
	 * */
	public static String getPropertieValue(String key){
		return systemProperties.getProperty(key);
	}
	


	
	public static void main(String[] args) {
		String multiple=SystemProperties.getPropertieValue("multiple");
		System.out.println(multiple);
		
	}
	
	

}
