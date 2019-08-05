

package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.*;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.web.mapper.TaskMapper;
import com.telecomyt.item.web.service.TaskService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

@Slf4j
@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    /**
     * 新增组
     * @param taskDto
     * @return
     */
    @Override
    public BaseResp<String> addTask(TaskDto taskDto) {
        TaskGroup taskGroup = new TaskGroup(taskDto);
        int addTaskGroupResult = taskMapper.insertGroup(taskGroup);
        if(addTaskGroupResult > 0){
            Integer groupId = taskGroup.getGroupId();
            Integer taskType = taskDto.getTaskType();
            Integer taskState = taskDto.getTaskState();
            Integer taskMain = taskDto.getTaskMain();
            LocalDateTime  taskEndTime = taskDto.getTaskEndTime();
            List<String> taskCardIds = taskDto.getTaskCardIds();
            List<String> taskCopierIds = taskDto.getTaskCopierIds();
            String taskFile = taskDto.getTaskFile();
            if(taskCardIds == null){
                taskCardIds = new ArrayList<>();
            }

            TaskDo executorTaskDo = TaskDo.builder().taskCardIds(taskCardIds).groupId(groupId).taskType(1).taskState(taskState).taskMain(taskMain).taskEndTime(taskEndTime).taskFile(taskFile).build();
            int addExecutorTaskResult = taskMapper.insertTask(executorTaskDo);
            if(addExecutorTaskResult == 0){
                //TODO 回滚
                log.info("新增任务执行人失败。");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResp<>(ResultStatus.FAIL);
            }
            //TODO
            TaskDo copierTaskDo = TaskDo.builder().taskCopierIds(taskCopierIds).groupId(groupId).taskType(2).taskState(taskState).taskMain(taskMain).taskEndTime(taskEndTime).taskFile(taskFile).build();
            int addTaskCoperResult = taskMapper.insertTask(copierTaskDo);
            if(addTaskCoperResult == 0){
                //TODO 回滚
                log.info("新增任务抄送人失败。");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                //return new BaseResp<>(ResultStatus.FAIL);
            }
            return new BaseResp<>(ResultStatus.SUCCESS);
        }else {
            log.info("新增任务组失败。");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new BaseResp<>(ResultStatus.FAIL);
        }
    }

    /**
     * 新增日志（未处理上传）
     */
    @Override
    public  BaseResp<String> insertLog(Integer groupId, Date logTime, String logPicture, String logCardId,Integer logType) {
        int flag = taskMapper.insertLog(groupId,logTime,logPicture,logCardId,logType);
        if(flag == 0){
            //TODO 回滚
            log.info("新增日志失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return new BaseResp<>(ResultStatus.FAIL);
        }else{
            return new BaseResp<>(ResultStatus.SUCCESS);
        }

    }

    /**
     * 查询个人任务详情
     */
    @Override
    public BaseResp<List> queryMyTaskById(String taskCardId,Integer groupId){
        List<Task> allTesk = taskMapper.queryMyTaskById(taskCardId,groupId);
        if(allTesk == null) {
          return new BaseResp<>(ResultStatus.FAIL);
        }else{
          return new BaseResp<>(ResultStatus.SUCCESS,allTesk);
        }
    }
    //查询新增任务详情
    @Override
    public BaseResp<List> queryNewTask(String taskCardId,Integer groupId){
        List<Task> newTask = taskMapper.queryNewTask(taskCardId,groupId);
        if(newTask == null) {
            return new BaseResp<>(ResultStatus.FAIL);
        }else{
            return new BaseResp<>(ResultStatus.SUCCESS,newTask);
        }
    }
    //查询其它任务详情
    @Override
    public BaseResp<List> queryOtherTask(String taskCardId,Integer groupId){
        List<Task> otherTask = taskMapper.queryOtherTask(taskCardId,groupId);
        if(otherTask == null) {
            return new BaseResp<>(ResultStatus.FAIL);
        }else{
            return new BaseResp<>(ResultStatus.SUCCESS,otherTask);
        }
    }

    /**
     * 任务详情
     */
    @Override
    public BaseResp<TaskLog> queryMyTaskLog(Integer groupId) {
        TaskLog taskLog = taskMapper.queryMyTaskLogById(groupId);
        if(taskLog == null) {
            return new BaseResp<>(ResultStatus.FAIL);
        }else{
            return new BaseResp<>(ResultStatus.SUCCESS,taskLog);
        }
    }

    /**
     * 更改个人任务状态
     */
    @Override
    public BaseResp<String> updateMyTaskByIdAndGroupId(String taskCardId,Integer groupId, Integer taskState) {

        int flag = taskMapper.updateMyTaskByIdAndGroupId(taskCardId,groupId,taskState);
        if(flag > 0){
            return new BaseResp<>(ResultStatus.SUCCESS);
        }else{
            return new BaseResp<>(ResultStatus.FAIL);

        }

    }

    /**
     * 删除任务
     */
    @Override
    public BaseResp<String> deleteTask(String taskCardId,Integer groupId) {
        int taskResult = taskMapper.deleteTask(taskCardId, groupId);
        if(taskResult > 0){
           int LogResult = taskMapper.deleteTaskLog(groupId);
           if(LogResult > 0){
                return new BaseResp<>(ResultStatus.SUCCESS);
           }
        }
        return new BaseResp<>(ResultStatus.FAIL);
    }
//    @Override
//    public boolean insertGroup(String creatorCardid, String sheetTitle, String sheetDescribe, Date endTime) {
//        return false;
//    }
//
//    @Override
//    public boolean insertTask(String taskId, int groupId, int taskType, int taskState, int taskMain, Date taskEndTime, String taskFile) {
//        return false;
//    }


//    @Override
//    public boolean insertTask(String taskId, int groupId, int taskType, int taskState, int taskMain, Date taskEndTime,String taskFile) {
//     int flag = taskMapper.insertTask(taskId,groupId,taskType,taskState,taskMain,taskEndTime,taskFile);
//        return flag > 0 ? true : false;
//    }


}
