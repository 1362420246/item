

package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.constant.CommonConstants;
import com.telecomyt.item.dto.*;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.*;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.utils.FileUtil;
import com.telecomyt.item.utils.OperationUtils;
import com.telecomyt.item.web.mapper.TaskMapper;
import com.telecomyt.item.web.service.TaskAsynService;
import com.telecomyt.item.web.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private TaskAsynService taskAsynService;

    @Value("${parameter.picture.ip}")
    private String ip ;

    /**
     * 新增组
     * @param taskDto
     * @return
     */
    @Override
    public BaseResp<String> addTask(TaskDto taskDto, MultipartFile groupTaskFile) throws IOException {
        TaskGroup taskGroup = new TaskGroup(taskDto);
        if(groupTaskFile != null && !groupTaskFile.isEmpty()) {
            //上传文件路径
            String taskGroupFilePath = FileUtil.getHomePath() + CommonConstants.REPORTING_PATH ;
            //上传文件名
            String groupFileName = groupTaskFile.getOriginalFilename();
            File groupFilePath = new File(taskGroupFilePath,groupFileName);
            //判断路径是否存在，如果不存在就创建一个
            if (!groupFilePath.getParentFile().exists()) {
                groupFilePath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            File  taskGroupFile = new File(taskGroupFilePath + groupFileName);
            groupTaskFile.transferTo(taskGroupFile);
            //存储的路径（相对路径）
            taskGroup.setGroupFilepath(CommonConstants.REPORTING_PATH + groupFileName);
            //访问路径（uri）
            taskGroup.setGroupFileurl(CommonConstants.REPORTING_PATH + groupFileName);
            taskGroup.setGroupFilename(groupFileName);
            log.info("上报文件保存路径："+taskGroupFile.getAbsolutePath());
            log.info("上报文件访问uri："+ CommonConstants.REPORTING_PATH + groupFileName);
        }
        int addTaskGroupResult = taskMapper.insertGroup(taskGroup);
        if(addTaskGroupResult > 0){
            Integer groupId = taskGroup.getGroupId();
            LocalDateTime  taskEndTime = taskDto.getTaskEndTime();
            List<String> taskCardIds = taskDto.getTaskCardIds();
            TaskDo executorTaskDo = TaskDo.builder().taskCardIds(taskCardIds).groupId(groupId).taskType(1).taskEndTime(taskEndTime).build();
            int addExecutorTaskResult = taskMapper.insertTask(executorTaskDo);
            if(addExecutorTaskResult == 0){
                log.info("新增任务执行人失败。");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return new BaseResp<>(ResultStatus.FAIL);
            }
            List<String> taskCopierIds = taskDto.getTaskCopierIds();
            if(taskCopierIds != null && taskCopierIds.size() > 0){
                TaskDo copierTaskDo = TaskDo.builder().taskCopierIds(taskCopierIds).groupId(groupId).taskType(2).taskEndTime(taskEndTime).build();
                int addTaskCoperResult = taskMapper.insertCoperTask(copierTaskDo);
                if(addTaskCoperResult == 0){
                    log.info("新增任务抄送人失败。");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new BaseResp<>(ResultStatus.FAIL);
                }
            }
            //添加日志
            TaskLog taskLog = TaskLog.builder().groupId(groupId).logTime(new Date()).
                    logCardId(taskGroup.getCreatorCardId()).logType(3).build();
            taskMapper.insertMyLog(taskLog);
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
     * 查询个人有关的所有任务
     * @param taskCardId 身份证号
     * @param title 标题模糊搜索
     * @param groupStatus 任务组状态状态 正常0 结束1
     * @param type  1：我创建的 2.我执行的 3.我是抄送人
     */
    @Override
    public BaseResp<List> queryMyTaskById(String taskCardId, String title, Integer groupStatus, List<Integer> type) {
        List<TaskSelect> lists = new ArrayList<>();
        if(type.contains(1)){
            List<TaskSelect> taskGroups = taskMapper.queryMyCreatTask(taskCardId ,title ,groupStatus);
            lists.addAll(taskGroups);
        }
        if(type.contains(2)){
            List<TaskSelect> acceptId = taskMapper.queryMyAcceptTask(taskCardId ,title ,groupStatus);
            lists.addAll(acceptId);
        }
        if(type.contains(3)){
            List<TaskSelect> copyId = taskMapper.queryMyCopperTask(taskCardId ,title , groupStatus);
            lists.addAll(copyId);
        }
        if(lists.size() > 0){
            List<Integer> overdues = new ArrayList<>();
            lists.forEach(taskSelect -> {
                if(taskSelect.getIsOverdue() == 0){
                    LocalDateTime taskEndTime = taskSelect.getTaskEndTime();
                    if (taskEndTime.isBefore(LocalDateTime.now())) {
                        taskSelect.setIsOverdue(1);
                        overdues.add(taskSelect.getGroupId());
                    }
                }
            });
            taskAsynService.updateOverdue(overdues);
        }
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
        String groupFileUrl = describeResult.getGroupFileUrl();
        if(StringUtils.isNotBlank(groupFileUrl)){
            describeResult.setGroupFileUrl( ip + groupFileUrl);
        }
        List<TaskLog> taskLogs = describeResult.getTaskLogs();
        //上传的图片和文件添加ip
        taskLogs.stream().filter(log -> StringUtils.isNotBlank(log.getFileUrl())).
                forEach(log -> log.setFileUrl( ip + log.getFileUrl()));
        taskLogs.forEach(log ->{
            UserVo user = OperationUtils.getUserByCardId(log.getLogCardId());
            log.setLogUser(user);
        });
        //查询执行者
        List<TaskIdState> taskCardId = taskMapper.queryTaskCardId(groupId);
        describeResult.setTaskCardId(taskCardId);
        //查询抄送者
        List<TaskIdState> taskCopierId = taskMapper.queryTaskCopierId(groupId);
        describeResult.setTaskCopierId(taskCopierId);
        //获取创建的用户信息
        String creatorCardId = describeResult.getCreatorCardId();
        UserVo creatorUser = OperationUtils.getUserByCardId(creatorCardId);
        describeResult.setCreatorUser(creatorUser);
        //抄送的用户信息
        List<String> taskCopierIds = taskCopierId.stream().map(TaskIdState::getCardId).collect(Collectors.toList());
        List<UserVo> taskCopierUsers = OperationUtils.getUsersByCardIds(taskCopierIds);
        describeResult.setTaskCopierUsers(taskCopierUsers);
        //获取执行的用户信息
        List<UserVo> taskCardUsers = OperationUtils.getUsersByTaskIdState(taskCardId);
        describeResult.setTaskCardUsers(taskCardUsers);
        return new BaseResp<>(ResultStatus.SUCCESS,describeResult);
    }

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
        List<TaskLog> taskLog = taskMapper.queryMyTaskLogById(groupId);
        if(taskLog == null) {
            return new BaseResp<>(ResultStatus.FAIL);
        }else{
            //上传的图片和文件添加ip
            taskLog.stream().filter(log -> StringUtils.isNotBlank(log.getFileUrl())).
                    forEach(log -> log.setFileUrl( ip + log.getFileUrl()));
            return new BaseResp<>(ResultStatus.SUCCESS,taskLog);
        }
    }

    /**
     * 修改任务
     */
    @Override
    public BaseResp<String> updateTask(TaskGroup taskGroup) {
        int flag = taskMapper.updateTask(taskGroup);
        if (flag > 0) {
            return new BaseResp<>(ResultStatus.SUCCESS);
        } else {
            return new BaseResp<>(ResultStatus.FAIL);
        }
    }
    /**
     * 修改个人在任务中的状态
     * @param taskCardId  执行人id
     * @param groupId 组id
     * @param taskState 0未开始 1进行中 2拒绝 3已完成  4逾期
     * @param reason  拒绝理由 可以不写
     */
    @Override
    public BaseResp<String> updateMyTaskByIdAndGroupId(String taskCardId, Integer groupId, Integer taskState, String reason) {
        int flag = taskMapper.updateMyTaskByIdAndGroupId(taskCardId,groupId,taskState);
        if(flag > 0){
            //  6.开始任务（执行者 ） 7.完成任务（执行者 ） 8.拒绝任务（执行者，可以带理由 ）
            int logType ;
            switch (taskState){
                case 1:
                    logType = 6;
                    break;
                case 2:
                    logType = 8;
                    break;
                case 3:
                    logType = 7;
                    break;
                default:
                    logType = 0;
                    break;
            }
            if(logType != 0){
                TaskLog taskLog = TaskLog.builder().groupId(groupId).logTime(new Date()).
                        logCardId(taskCardId).logType(logType).fileTagging(reason).build();
                taskMapper.insertMyLog(taskLog);
            }
            return new BaseResp<>(ResultStatus.SUCCESS);
        }else{
            return new BaseResp<>(ResultStatus.FAIL);
        }
    }
    /**
     * 更改任务状态（创建人结束任务）
     */
    @Override
    @Transactional(transactionManager = "transactionManager" ,propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public BaseResp<String> updateTaskByIdAndGroupId( Integer groupId) {
        int flag = taskMapper.updateTaskByIdAndGroupId(groupId);
        if(flag > 0){
            TaskGroup taskGroup = taskMapper.getTaskGroupByGroupId(groupId);
            if(taskGroup.getTaskEndTime().isBefore(LocalDateTime.now())){
                int result = taskMapper.updateOverdue(groupId);
                if(result == 0){
                    log.info("修改逾期失败。事务回滚");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return new BaseResp<>(ResultStatus.FAIL);
                }
            }
            TaskLog taskLog = TaskLog.builder().groupId(groupId).logTime(new Date()).
                    logCardId(taskGroup.getCreatorCardId()).logType(4).build();
            taskMapper.insertMyLog(taskLog);
            return new BaseResp<>(ResultStatus.SUCCESS);
        }else{
            return new BaseResp<>(ResultStatus.FAIL);

        }

    }

    /**
     * 删除任务
     */
    @Override
    public BaseResp<String> deleteTask(Integer groupId, String reason) {
        int taskResult = taskMapper.deleteTask(groupId);
        if(taskResult > 0){
            TaskGroup taskGroup = taskMapper.getTaskGroupByGroupId( groupId);
            TaskLog taskLog = TaskLog.builder().groupId(groupId).logTime(new Date()).
                    logCardId(taskGroup.getCreatorCardId()).logType(5).fileTagging(reason).build();
            taskMapper.insertMyLog(taskLog);
            return new BaseResp<>(ResultStatus.SUCCESS);
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
