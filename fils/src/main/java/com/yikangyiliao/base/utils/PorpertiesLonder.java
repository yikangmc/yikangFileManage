package com.yikangyiliao.base.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.yikangyiliao.base.thread.LogsThread;


/**
 * @author liushuaic
 * @date 2015/10/10 10:41
 * @desc 加载一些基本的信息
 * 队列容器，是需要，在一个单独的地方存储的。
 * 这样才不会出现，问题。
 * **/
public class PorpertiesLonder implements ApplicationContextAware {

	
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		
		/**
		 * @author bry
		 * @date  2016/10/09 11:42
		 * @desc 举报消息推送
		 * 
		 */
		LogsThread logsThread = (LogsThread) applicationContext.getBean("logsThread");
		Thread threadLogs = new Thread(logsThread);
		threadLogs.start();
	}

}
