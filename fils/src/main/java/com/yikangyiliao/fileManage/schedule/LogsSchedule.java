package com.yikangyiliao.fileManage.schedule;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yikangyiliao.base.utils.FileUtil;
import com.yikangyiliao.fileManage.entity.Logs;
import com.yikangyiliao.fileManage.manage.LogsManage;

/**
 * @author houyt
 * @date 2016-09-14 10:43
 * @desc 日志批量入库任务
 **/
@Component(value="logsSchedule")
public class LogsSchedule {
	
	@Autowired
	private LogsManage logsManage;
	
	private Logger logger=LoggerFactory.getLogger(getClass());

	/**
	 * 
	 * @author houyt
	 * @date 2016-09-29 17:43
	 * @desc 读取日志文件批量入库
	 * 
	 * **/
	public void readLogs() {
		logger.info("LogsSchedule --> readLogs");
		try {
			List<Logs> logsList = FileUtil.parsingLog();
			if(logsList.size()>0){
				logsManage.insertLogs(logsList);
			}
		} catch (Exception e) {
			logger.error("LogsSchedule --> readLogs --> message:"+e.getMessage());
		}
	}

}
