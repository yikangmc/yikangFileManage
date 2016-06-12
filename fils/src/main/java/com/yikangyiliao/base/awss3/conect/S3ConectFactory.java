package com.yikangyiliao.base.awss3.conect;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.ServletOutputStream;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import com.amazonaws.SDKGlobalConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.GroupGrantee;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.Permission;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

public class S3ConectFactory {
	
	

	private static	String bucketName = "biophoto";
	
	private static String accessKey="AKIAO7PK4YPM2THJMFUA";
	
	private static String secretKey="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";
	
	private static String accessUrl="s3.cn-north-1.amazonaws.com.cn";
	
	
	
	
	
	/**
	 * @author liushuaic
	 * @date 2015/09/13
	 * 
	 * */
	public static AmazonS3 ConnectFactory(){
		
		System.setProperty(SDKGlobalConfiguration.ENABLE_S3_SIGV4_SYSTEM_PROPERTY, "true");
		
		AWSCredentials  credentials = new BasicAWSCredentials(accessKey, secretKey);
		ClientConfiguration clientConfig = new ClientConfiguration();
		clientConfig.setProtocol(Protocol.HTTPS);
		
		AmazonS3 conn = new AmazonS3Client(credentials, clientConfig);
		conn.setEndpoint(accessUrl);
		
		return  conn;
	}
	
	
	
	/**
	 * 
	 * @author liushuaic
	 * @date 2015/09/14 
	 * 存放图片文件
	 * 
	 * **/
	public static boolean putImgeFile(AmazonS3 con,String bucketName,String fileName,  File file){
		
		PutObjectResult putObjectResult=con.putObject(bucketName,fileName,file);

		return true;
		
	}

	/**
	 * 
	 * @author liushuaic
	 * @date 2015/09/14 
	 * 存放图片文件
	 * 
	 * **/
	public static boolean putImgeFile(AmazonS3 con,String bucketName,String fileName,InputStream inputStream,String contentType){
		ObjectMetadata objectMetaData= new ObjectMetadata();
		objectMetaData.setContentType(contentType);
		
		//设置权限列表
		AccessControlList acl = new AccessControlList();
		//设置所有人，有读权限。
//		acl.grantPermission(GroupGrantee.AllUsers, Permission.ReadAcp);
		//想设置打开下载地址
		acl.grantPermission(GroupGrantee.AllUsers, Permission.Read);
		
		
		PutObjectRequest putObject=new PutObjectRequest(bucketName, fileName, inputStream,objectMetaData);
		putObject.withAccessControlList(acl);
		PutObjectResult putObjectResult=con.putObject(putObject);
		return true;
	}
	
	
	/**
	 * @author liushuaic
	 * @date 2015/09/14 14:29
	 * 生成文件的请求url
	 * 
	 * **/
	public static String getFileURL(AmazonS3 con,String bucketName,String fileName){
		GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName,fileName);

		return con.generatePresignedUrl(request).toString();
	}
	
	
	/**
	 * @author liushuaic
	 * @date 2015/09/14 14:29
	 * 生成文件的请求url
	 * 
	 * **/
	public static URL getResourceURL(AmazonS3 con,String bucketName,String fileName){ 
		AmazonS3Client amazonS3Client=(AmazonS3Client) con;
		return amazonS3Client.getUrl(bucketName, fileName);
		 
	}
	/**
	 * @author liushuaic
	 * @date 2015/09/14 14:57
	 * 获取文件
	 * */
	public static void getFile(AmazonS3 con,String bucketName,String fileName,ServletOutputStream outPutStream){
		
		S3Object object=con.getObject(bucketName,fileName);
		S3ObjectInputStream inputStream= object.getObjectContent();
		byte[] inputBuffer=new byte[12];
		try {
			while(inputStream.read(inputBuffer) !=-1){
				 outPutStream.write(inputBuffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) throws FileNotFoundException {
//		AmazonS3  amazonS3=S3ConectFactory.ConnectFactory();
//		File file=new File("/Users/liushuaic/1.jpg");
//		InputStream input=new FileInputStream(file);
//		S3ConectFactory.putImgeFile(amazonS3, bucketName, "a.jpg", input,"image/jpeg");
		
		AmazonS3 con=S3ConectFactory.ConnectFactory();
		URL url=S3ConectFactory.getResourceURL(con, bucketName, "a.jpg");
		System.out.println(url.toString());
	}
	
}
