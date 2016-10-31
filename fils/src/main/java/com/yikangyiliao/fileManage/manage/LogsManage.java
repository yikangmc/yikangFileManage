package com.yikangyiliao.fileManage.manage;

import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.yikangyiliao.fileManage.dao.LogsDao;
import com.yikangyiliao.fileManage.entity.Logs;

@Component
public class LogsManage {
	
	@Autowired
	private LogsDao logsDao;
	
	/**
	 * @author houyt
	 * @date 2016/09/29 14:17
	 * @desc 日志批量入库
	 * @param logsList
	 * @return
	 */
	public int insertLogs(List<Logs> logsList){
		long beginTime = System.currentTimeMillis();
         System.out.println("开始插入...");
         WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
         SqlSessionFactory sqlSessionFactory = context.getBean(SqlSessionFactory.class);
         SqlSession session = null;
         int result = 1;
         try {
             session = sqlSessionFactory.openSession(ExecutorType.BATCH, false);
             int batchCount = 1000;// 每批commit的个数
             int batchLastIndex = batchCount;// 每批最后一个的下标
             for (int index = 0; index < logsList.size();) {
                 if (batchLastIndex > logsList.size()) {
                     batchLastIndex = logsList.size();
                     result = result * session.insert("com.yikangyiliao.fileManage.dao.LogsDao.logsInsertBatch",logsList.subList(index, batchLastIndex));
                    // session.commit();
                     System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
                     break;// 数据插入完毕，退出循环
                 } else {
                     result = result * session.insert("com.yikangyiliao.fileManage.dao.LogsDao.logsInsertBatch",logsList.subList(index, batchLastIndex));
                    // session.commit();
                     System.out.println("index:" + index+ " batchLastIndex:" + batchLastIndex);
                     index = batchLastIndex;// 设置下一批下标
                     batchLastIndex = index + (batchCount - 1);
                 }
             }
         } catch (Exception e) {
             e.printStackTrace();
             session.rollback();
         } finally {
             if (session != null) {
                 session.close();
             }
         }
		long endTime = System.currentTimeMillis();
		System.out.println("插入完成，耗时 " + (endTime - beginTime) + " 毫秒！");
		return logsDao.logsInsertBatch(logsList);
	}
}
