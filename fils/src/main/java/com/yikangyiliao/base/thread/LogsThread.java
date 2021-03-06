package com.yikangyiliao.base.thread;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yikangyiliao.base.utils.FileUtil;
import com.yikangyiliao.fileManage.entity.Logs;
import com.yikangyiliao.fileManage.manage.LogsManage;

@Component(value="logsThread")
public class LogsThread implements Runnable{
	

	@Autowired
	private LogsManage logsManage;
	
	private Logger logger=LoggerFactory.getLogger(getClass());
	
	public void run(){
		while(true){
			try{
				List<Logs> logsList = FileUtil.parsingLog();
				if(null != logsList){
					if(logsList.size()>0){
						int resultNum = logsManage.insertLogs(logsList);
						if(resultNum>0){
							FileUtil.deleteFile();
						}
					}else{
						Thread.sleep(1000);
					}
				}else{
					Thread.sleep(1000);
				}
				
			}catch(Exception exception){
				exception.printStackTrace();
				logger.error("LogsSchedule --> readLogs --> message:"+exception.getMessage());
			}
		}
	}

}
