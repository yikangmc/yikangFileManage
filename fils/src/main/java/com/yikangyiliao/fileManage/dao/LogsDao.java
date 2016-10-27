package com.yikangyiliao.fileManage.dao;

import java.util.List;

import com.yikangyiliao.fileManage.entity.Logs;

public interface LogsDao {
    int deleteByPrimaryKey(Long logsId);

    int insert(Logs record);

    int insertSelective(Logs record);

    Logs selectByPrimaryKey(Long logsId);

    int updateByPrimaryKeySelective(Logs record);

    int updateByPrimaryKey(Logs record);
    
    int logsInsertBatch(List<Logs> logsList);
}