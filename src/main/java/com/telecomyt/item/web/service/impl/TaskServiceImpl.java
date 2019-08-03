
/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.*;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.web.mapper.TaskMapper;
import com.telecomyt.item.web.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Slf4j
@Service
@Transactional
public class TaskServiceImpl implements TaskService {



    @Autowired
private TaskMapper taskMapper;

    /**

     * @return
     */
    @Override
    public BaseResp<String> addTask(TaskDto taskDto) {
        TaskGroup taskGroup = new TaskGroup(taskDto);
        int addTaskGroupResult = taskMapper.insertGroup(taskGroup);
        if(addTaskGroupResult > 0){
            Integer groupId = taskGroup.getGroupId();
            List<String> taskCardId = taskDto.getTaskCardId();
            if(taskCardId == null){
                taskCardId = new ArrayList<>();
            }
            TaskDo taskDo = TaskDo.builder().groupId(groupId).taskCardId(taskCardId).build();
            int addTaskResult = taskMapper.insertTask(taskDo);
            if(addTaskResult == 0){
                //TODO 回滚
                log.info("新增任务失败。");
                //return new BaseResp<>(ResultStatus.FAIL);
            }
            //TODO  日程的创建 不是日程的开始 ， 日程的开始和结束日志先不考虑
            return new BaseResp<>(ResultStatus.SUCCESS);
        }else {
            log.info("新增任务组失败。");
            return new BaseResp<>(ResultStatus.FAIL);
        }
    }

    @Override
    public boolean insertGroup(String creatorCardid, String sheetTitle, String sheetDescribe, Date endTime) {
        return false;
    }

    @Override
    public boolean insertTask(String taskId, int groupId, int taskType, int taskState, int taskMain, Date taskEndTime, String taskFile) {
        return false;
    }


//    @Override
//    public boolean insertTask(String taskId, int groupId, int taskType, int taskState, int taskMain, Date taskEndTime,String taskFile) {
//     int flag = taskMapper.insertTask(taskId,groupId,taskType,taskState,taskMain,taskEndTime,taskFile);
//        return flag > 0 ? true : false;
//    }


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
    public List<TaskLog> queryMyLogByGroupId(String groupId) {
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
