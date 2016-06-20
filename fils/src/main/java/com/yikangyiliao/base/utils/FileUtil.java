package com.yikangyiliao.base.utils;

import java.io.File;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;
import com.amazonaws.services.s3.AmazonS3;
import com.yikangyiliao.base.awss3.conect.S3ConectFactory;

public class FileUtil
{

  public static String getFileSuffix(String fileName)
  {
    return fileName.substring(fileName.lastIndexOf("."), fileName.length());
  }

  public static  String getUniqueFileName(String fileName)
  {
    String suffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
    UUID uuid = UUID.randomUUID(); 
    return  uuid + suffix;
     
  }

  public static String getUniqueNewFileName(String fileName){
	  int length = fileName.indexOf(".");  
	  String result = fileName.substring(0,length);
	  String newSuffix = fileName.substring(fileName.lastIndexOf("."),fileName.length());
	  String newString = "!icon";
	  String newFilName = result+newString +newSuffix;
	  return newFilName;
  }
  
  
  public static void updateFolderInnerContent() throws FileNotFoundException{
	  File file=new File("/Users/liushuaic/工作/yikangyiliao/portal/素材/项目图片/PICC管的换药维护与护理/");
	  AmazonS3 con=S3ConectFactory.ConnectFactory();
		 if( file.isDirectory()){
			File[] files= file.listFiles();
			 for(File f:files){
					S3ConectFactory.putImgeFile(con, "/portals",f.getName(), new FileInputStream(f),"application/octet-stream");
					String fileUrl=S3ConectFactory.getResourceURL(con, "/portals", f.getName()).toString();
					System.out.println( f.getName()+"--"+fileUrl);
			  }
		 }
	  
  }
  
  public static void main(String[] args)
  {
    String fileName = "dd.dd.img";
    String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
    UUID uuid = UUID.randomUUID();
    System.out.println(uuid + suffix);  
	  try {
		updateFolderInnerContent();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
  
  
  
  
  
  
  
  
}