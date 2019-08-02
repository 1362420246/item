
/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.entity.Log;
import com.telecomyt.item.entity.Task;
import com.telecomyt.item.web.mapper.TaskMapper;
import com.telecomyt.item.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {



    @Autowired
private TaskMapper taskMapper;

    /**
     *
     * @param creator_Cardid
     * @param sheet_Title
     * @param sheet_Describe
     * @param end_Time
     * @return
     */
    @Override
    public boolean insertGroup(String creatorCardid, String sheetTitle, String sheetDescribe, Date endTime) {
        int flag = taskMapper.insertGroup(creatorCardid,sheetTitle,sheetDescribe,endTime);
        return flag > 0 ? true : false;
    }

    @Override
    public boolean insertTask(String taskId, int groupId, int taskType, int taskState, int taskMain, Date taskEndTime,String taskFile) {
     int flag = taskMapper.insertTask(taskId,groupId,taskType,taskState,taskMain,taskEndTime,taskFile);
        return flag > 0 ? true : false;
    }


    @Override
    public boolean insertLog(int groupId, Date logTime, String logPicture, String logCardId) {
        int flag = taskMapper.insertLog(groupId,logTime,logPicture,logCardId);
        return flag > 0 ? true : false;
    }

    @Override
    public List<Task> queryMyTaskById(String taskCardid) {
        return taskMapper.queryMyTaskById(taskCardid);
    }

    @Override
    public List<Log> queryMyLogByGroupId(String groupId) {
        return taskMapper.queryMyLogByGroupId(groupId);
    }
    @Override
    public boolean updateMyTaskByIdAndGroupId(String taskCardId,int groupId, int taskState) {
        int flag = taskMapper.updateMyTaskByIdAndGroupId(taskCardId,groupId,taskState);
        return flag > 0 ? true : false;
    }

    @Override
    public boolean deleteTask(String taskCardId,String groupId) {
        int flag = taskMapper.deleteTask(taskCardId, groupId);
        return flag > 0 ? true : false;
    }


}
