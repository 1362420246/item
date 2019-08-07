

package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.dto.TaskDescribe;
import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.TaskIdState;
import com.telecomyt.item.dto.TaskSelect;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.*;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.web.mapper.TaskMapper;
import com.telecomyt.item.web.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
//    ,MultipartFile groupTaskFile  throws IOException
    @Override
    public BaseResp<String> addTask(TaskDto taskDto) {
        TaskGroup taskGroup = new TaskGroup(taskDto);

        int addTaskGroupResult = taskMapper.insertGroup(taskGroup);
        if(addTaskGroupResult > 0){
            Integer groupId = taskGroup.getGroupId();
            Integer taskType = taskDto.getTaskType();
            Integer taskState = taskDto.getTaskState();
            LocalDateTime  taskEndTime = taskDto.getTaskEndTime();
            List<String> taskCardIds = taskDto.getTaskCardIds();
            List<String> taskCopierIds = taskDto.getTaskCopierIds();
            if(taskCardIds == null){
                taskCardIds = new ArrayList<>();
            }

            TaskDo executorTaskDo = TaskDo.builder().taskCardIds(taskCardIds).groupId(groupId).taskType(1).taskState(taskState).taskEndTime(taskEndTime).build();
            int addExecutorTaskResult = taskMapper.insertTask(executorTaskDo);
            if(addExecutorTaskResult == 0){
                //TODO 回滚
                log.info("新增任务执行人失败。");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResp<>(ResultStatus.FAIL);
            }
            //TODO
            TaskDo copierTaskDo = TaskDo.builder().taskCopierIds(taskCopierIds).groupId(groupId).taskType(2).taskState(taskState).taskEndTime(taskEndTime).build();
            int addTaskCoperResult = taskMapper.insertCoperTask(copierTaskDo);
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
     * 新增日志（处理上传）
     * @param taskLog
     * @return
     */


    @Override
    public BaseResp<String> insertMyLog(TaskLog taskLog) {
        int resultNewLog = taskMapper.insertMyLog(taskLog);
        if(resultNewLog > 0){
            return new BaseResp<>(ResultStatus.SUCCESS);
        }
        return new BaseResp<>(ResultStatus.FAIL);
    }

    /**
     * 查询与我有关的所有任务
     * @param taskCardId
     * @return
     */
    @Override
    public BaseResp<List> queryMyTaskById(String taskCardId) {
      List<TaskSelect> lists = new ArrayList<>();
        String creatorCardId = taskCardId;
        String taskCopierId = taskCardId;
        List<TaskSelect> taskGroups = taskMapper.queryMyCreatTask(creatorCardId);
        List<TaskSelect> copyId = taskMapper.queryMyCopperTask(taskCopierId);
        List<TaskSelect> acceptId = taskMapper.queryMyAcceptTask(taskCardId);
        lists.addAll(taskGroups);
        lists.addAll(copyId);
        lists.addAll(acceptId);
        return new BaseResp<>(ResultStatus.SUCCESS,lists);
    }

    /**
     * 查询任务详情
     * @param groupId
     * @return
     */
    @Override
    public BaseResp<TaskDescribe> queryTaskDetailed(Integer groupId) {
        TaskDescribe describeResult = taskMapper.queryTaskDetailed(groupId);
        if(describeResult == null){
            return new BaseResp<>(ResultStatus.FAIL);
        }
        List<TaskIdState> taskCardId = taskMapper.queryTaskCardId(groupId);
        describeResult.setTaskCardId(taskCardId);
        List<TaskIdState> taskCopierId = taskMapper.queryTaskCopierId(groupId);
        describeResult.setTaskCopierId(taskCopierId);
        return new BaseResp<>(ResultStatus.SUCCESS,describeResult);

    }


//    @Override
//        public  BaseResp<String> insertMyLog(Integer groupId, Date logTime, MultipartFile logPicture, String logCardId, Integer logType,String fileTagging) throws IOException {
//        if(logType == null || groupId == null || logCardId == null ){
//            return new BaseResp<>(ResultStatus.INVALID_PARAM);
//        }
//        TaskLog taskLog = TaskLog.builder().groupId(groupId).logCardId(logCardId).logType(logType).build();
//        if(logType == 0 || logType == 1){
//            //如果文件不为空，写入上传路径
//            if(logPicture.isEmpty()) {
//                return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"上报失败，请选择文件");
//            }
//            //上传文件路径
//            String Logpath = FileUtil.getHomePath() + CommonConstants.REPORTING_PATH ;
//            //上传文件名
//            String filename = logPicture.getOriginalFilename();
//            File filepath = new File(Logpath,filename);
//            //判断路径是否存在，如果不存在就创建一个
//            if (!filepath.getParentFile().exists()) {
//                filepath.getParentFile().mkdirs();
//            }
//            //将上传文件保存到一个目标文件当中
//            File saveLogFile = new File(Logpath + filename);
//            logPicture.transferTo(saveLogFile);
//            //存储的路径（相对路径）
//            taskLog.setLogPath(CommonConstants.REPORTING_PATH + filename);
//            //访问路径（uri）
//            taskLog.setLogUrl(CommonConstants.REPORTING_PATH + filename);
//            log.info("上报文件保存路径："+saveLogFile.getAbsolutePath());
//            log.info("上报文件保存路径2："+ FileUtil.getHomePath() + CommonConstants.REPORTING_PATH + filename);
//            log.info("上报文件访问uri："+ CommonConstants.REPORTING_PATH + filename);
//        }else if(logType == 4){
//            taskLog.setFileTagging(fileTagging);
//        }else{
//            return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"上报失败，上报类型错误");
//        }
//           int logResult = taskMapper.insertMyLog(taskLog);
//        if(logResult > 0){
//            return new BaseResp<>(ResultStatus.SUCCESS);
//        }
//        return new BaseResp<>(ResultStatus.FAIL);
//    }

//    /**
//     * 查询个人任务详情
//     */
//    @Override
//    public BaseResp<List> queryMyTaskById(String taskCardId,Integer groupId){
//        List<Task> allTesk = taskMapper.queryMyTaskById(taskCardId,groupId);
//        if(allTesk == null) {
//          return new BaseResp<>(ResultStatus.FAIL);
//        }else{
//          return new BaseResp<>(ResultStatus.SUCCESS,allTesk);
//        }
//    }

    /**
     * 查询新增任务详情
     * @param taskCardId
     * @param
     * @return
     */
    @Override
    public BaseResp<List> queryNewTask(String taskCardId){
        List<Task> newTask = taskMapper.queryNewTask(taskCardId);
        if(newTask == null) {
            return new BaseResp<>(ResultStatus.FAIL);
        }else{
            return new BaseResp<>(ResultStatus.SUCCESS,newTask);
        }
    }

    /**
     * 查询其它任务详情
     * @param taskCardId
     * @param groupId
     * @return
     */
//    @Override
//    public BaseResp<List> queryOtherTask(String taskCardId,Integer groupId){
//        List<Task> otherTask = taskMapper.queryOtherTask(taskCardId,groupId);
//        if(otherTask == null) {
//            return new BaseResp<>(ResultStatus.FAIL);
//        }else{
//            return new BaseResp<>(ResultStatus.SUCCESS,otherTask);
//        }
//    }

    /**
     * 任务详情
     */
    @Override
    public BaseResp<List> queryMyTaskLog(Integer groupId) {
        List<TaskGroup> taskLog = taskMapper.queryMyTaskLogById(groupId);
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
     * 更改任务状态（创建人）
     */
    @Override
    public BaseResp<String> updateTaskByIdAndGroupId( Integer groupId, Integer taskState) {

        int flag = taskMapper.updateTaskByIdAndGroupId(groupId,taskState);
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
    public BaseResp<String> deleteTask(String creatorCardId,Integer groupId) {
        int taskResult = taskMapper.deleteTask(creatorCardId, groupId);
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

//        if(groupTaskFile.isEmpty()) {
//            return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"上传失败，请选择文件");
//        }
//        //上传文件路径
//        String taskGroupFilePath = FileUtil.getHomePath() + CommonConstants.REPORTING_PATH ;
//        //上传文件名
//        String groupFileName = groupTaskFile.getOriginalFilename();
//        File GroupFilePath = new File(taskGroupFilePath,groupFileName);
//        //判断路径是否存在，如果不存在就创建一个
//        if (!GroupFilePath.getParentFile().exists()) {
//            GroupFilePath.getParentFile().mkdirs();
//        }
//        //将上传文件保存到一个目标文件当中
//        File  TaskGroupFile = new File(taskGroupFilePath + groupFileName);
//        groupTaskFile.transferTo(TaskGroupFile);
//        //存储的路径（相对路径）
//        taskGroup.setTaskFilePath(CommonConstants.REPORTING_PATH + groupFileName);
//        //访问路径（uri）
//        taskGroup.setTaskFileUrl(CommonConstants.REPORTING_PATH + groupFileName);
//        log.info("上报文件保存路径："+TaskGroupFile.getAbsolutePath());
//        log.info("上报文件保存路径2："+ FileUtil.getHomePath() + CommonConstants.REPORTING_PATH + groupFileName);
//        log.info("上报文件访问uri："+ CommonConstants.REPORTING_PATH + groupFileName);
