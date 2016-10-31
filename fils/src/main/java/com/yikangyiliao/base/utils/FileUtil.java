package com.yikangyiliao.base.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.util.json.JSONException;
import com.amazonaws.util.json.JSONObject;
import com.yikangyiliao.base.awss3.conect.S3ConectFactory;
import com.yikangyiliao.fileManage.entity.Logs;
import com.yikangyiliao.fileManage.manage.LogsManage;

public class FileUtil {

	@Autowired
	private LogsManage logsManage;

	public static String getFileSuffix(String fileName) {
		return fileName.substring(fileName.lastIndexOf("."), fileName.length());
	}

	public static String getUniqueFileName(String fileName) {
		String suffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		UUID uuid = UUID.randomUUID();
		return uuid + suffix;

	}

	public static String getUniqueNewFileName(String fileName) {
		int length = fileName.indexOf(".");
		String result = fileName.substring(0, length);
		String newSuffix = fileName.substring(fileName.lastIndexOf("."), fileName.length());
		String newString = "!icon";
		String newFilName = result + newString + newSuffix;
		return newFilName;
	}

	public static void updateFolderInnerContent() throws FileNotFoundException {
		File file = new File("/Users/liushuaic/工作/yikangyiliao/portal/素材/项目图片/PICC管的换药维护与护理/");
		AmazonS3 con = S3ConectFactory.ConnectFactory();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File f : files) {
				S3ConectFactory.putImgeFile(con, "/portals", f.getName(), new FileInputStream(f),
						"application/octet-stream");
				String fileUrl = S3ConectFactory.getResourceURL(con, "/portals", f.getName()).toString();
				System.out.println(f.getName() + "--" + fileUrl);
			}
		}

	}

	/**
	 * 日志解析
	 * @return
	 */
	public static List<Logs> parsingLog() {
		String path=SystemProperties.getPropertieValue("oldFilePath");
		
		List<Logs> logsList = new ArrayList<Logs>();
		List<String> pathList = getFileName(path);
		StringBuffer sBuffer = new StringBuffer();
		if(pathList.size()>0){
			for(int i=0;i<pathList.size();i++){
				String filePath=path+pathList.get(i);
				File file = new File(filePath);
				try {
					if (file.isFile() && file.exists()) { // 判断文件是否存在
						InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");// 考虑到编码格式
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTxt = null;
						while ((lineTxt = bufferedReader.readLine()) != null) {
							String ary[] = lineTxt.split("-->");
							//时间-->ios/android/服务器/-->userId-->json
							/*
							 * { equipment:(设备标识) 
							 * operationType:(操作类型:0 打开，1 关闭，2 无操作)
							 * openTime:(每日打开app时间) 
							 * closeTime:(每日退出app时间)
							 * taglibId:(统计标签日点击量)
							 * homePageBanner:(统计首页banner点击量，默认写1，没有就空着)
							 * communityPageBanner:(社区banner点击量，默认写1，没有就空着) 
							 * url:{
							 * pageIdentify：(哪一页) 
							 * typeIdentify:(文章、问题、帖子等)
							 * typeID:（文章ID，问题ID等） 
							 * startTime:(进入此页面时间) 
							 * endTime:(退出此页面时间)
							 * }
							 */
							if(ary.length==4){
								Logs logs = new Logs();
								logs.setUserId(Long.valueOf(ary[2].toString()));
								String jsonString = ary[3];
								try {
									JSONObject jsonObject = new JSONObject(jsonString);
									String equipment=jsonObject.getString("equipment");
									equipment=equipment.equals("")?"0":equipment;
									String operationType=jsonObject.getString("operationType");
									operationType=operationType.equals("")?"0":operationType;
									String openTime=jsonObject.getString("openTime");
									openTime=openTime.equals("")?"1998-01-01 00:00:00":openTime;
									String closeTime=jsonObject.getString("closeTime");
									closeTime=closeTime.equals("")?"1998-01-01 00:00:00":closeTime;
									String taglibId=jsonObject.getString("taglibId");
									taglibId=taglibId.equals("")?"0":taglibId;
									String homePageBanner=jsonObject.getString("homePageBanner");
									homePageBanner=homePageBanner.equals("")?"0":homePageBanner;
									String communityPageBanner=jsonObject.getString("communityPageBanner");
									communityPageBanner=communityPageBanner.equals("")?"0":communityPageBanner;
									String url=jsonObject.getString("url");
									JSONObject urlJsonString = new JSONObject(url);
									String pageIdentify=urlJsonString.getString("pageIdentify");
									pageIdentify=pageIdentify.equals("")?"0":pageIdentify;
									String typeIdentify=urlJsonString.getString("typeIdentify");
									typeIdentify=typeIdentify.equals("")?"0":typeIdentify;
									String typeID = urlJsonString.getString("typeId");
									typeID=typeID.equals("")?"-1":typeID;
									String startTime=urlJsonString.getString("startTime");
									startTime=startTime.equals("")?"1998-01-01 00:00:00":startTime;
									String endTime = urlJsonString.getString("endTime");
									endTime=endTime.equals("")?"1998-01-01 00:00:00":endTime;
									logs.setEquipment(equipment);
									logs.setOperationType(Integer.valueOf(operationType));
									SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
									Date openDate = sdf.parse(openTime);
									logs.setOpenTime(openDate);
									Date closeDate = sdf.parse(openTime);
									logs.setCloseTime(closeDate);
									logs.setTaglibId(Long.valueOf(taglibId));
									logs.setHomePageBanner(Integer.valueOf(homePageBanner));
									logs.setCommunityPageBanner(Integer.valueOf(communityPageBanner));
									logs.setPageIdentify(Integer.valueOf(pageIdentify));
									logs.setTypeIdentify(typeIdentify);
									logs.setTypeId(Long.valueOf(typeID));
									Date startDate = sdf.parse(startTime);
									logs.setStartTime(startDate);
									Date endDate = sdf.parse(endTime);
									logs.setEndTime(endDate);
									Date createDate = new Date();
									logs.setCreateTime(createDate);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								logsList.add(logs);
							}else{
								System.out.println("日志格式有误");
							}
						}
						read.close();
						sBuffer.append(filePath);
						if(i!=pathList.size()-1){
							sBuffer.append(",");
						}
					} else {
						System.out.println("找不到指定的文件");
					}
					
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				SystemProperties.setPropertieValue("filePath", sBuffer.toString());
			}
		}
		return logsList;
	}
	
	/**
	 * @desc 获取文件名
	 * @author houyt
	 * @date 2016/10/19 17:28
	 */
	public static List<String> getFileName(String path){
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return null;
        }
        List<String> pathList = new ArrayList<String>();
        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (fs.isDirectory()) {
                System.out.println(fs.getName() + " [目录]");
            } else {
            	pathList.add(fs.getName());
                System.out.println(fs.getName());
            }
        }
        return pathList;
	}
	
	/**
	 * @desc 移除已读完的文件
	 * @author houyt
	 * @date 2016/10/19
	 */
	public static void deleteFile(){
		String filePath = SystemProperties.getPropertieValue("filePath");
		String pathList[] = filePath.split(",");
		for(int i=0;i<pathList.length;i++){
			File file = new File(pathList[i]);
			file.delete();
		}
	}

	public static void main(String[] args) {
		 String path = "D:/logs"; // 路径
		          File f = new File(path);
		          if (!f.exists()) {
		              System.out.println(path + " not exists");
		              return;
		          }
		  
		          File fa[] = f.listFiles();
		          for (int i = 0; i < fa.length; i++) {
		              File fs = fa[i];
		              if (fs.isDirectory()) {
		                  System.out.println(fs.getName() + " [目录]");
		              } else {
		                  System.out.println(fs.getName());
		              }
		          }
	}
	
	
}