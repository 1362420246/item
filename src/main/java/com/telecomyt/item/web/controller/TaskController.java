
package com.telecomyt.item.web.controller;

import com.telecomyt.item.constant.CommonConstants;
import com.telecomyt.item.dto.TaskDescribe;
import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.TaskGroup;
import com.telecomyt.item.entity.TaskLog;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.utils.BeanValidator;
import com.telecomyt.item.utils.FileUtil;
import com.telecomyt.item.web.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/taskController")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 新增组
     */
    @PostMapping("/insertNewTask")
    public BaseResp<String> insertNewTask(TaskDto taskDto,MultipartFile groupTaskFile) throws IOException {
        BeanValidator.check(taskDto);
        return taskService.addTask(taskDto,groupTaskFile);
    }

    /**
     * 新增任务日志（未处理上传）
     */
    @PostMapping("/insertNewLog")
    public  BaseResp<String> insertNewLog(Integer groupId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date logTime, String logPicture, String logCardId, Integer logType){
        return taskService.insertLog(groupId,logTime,logPicture,logCardId,logType);
    }

    /**
     * 新增任务日志（处理上传）
     * @param logType 上传类型 0-图片 1-文档 2-文字描述
     * @return
     */
   @PostMapping("/insertMyLog")
    public  BaseResp<String> insertMyLog(
            @RequestParam("groupId") Integer groupId,
            @RequestParam("logCardId") String logCardId,
            @RequestParam("logType") Integer logType,
            String fileTagging ,
            MultipartFile logFile ) throws IOException {
       if(logType == null || groupId == null || logCardId == null ){
            return new BaseResp<>(ResultStatus.INVALID_PARAM);
       }
       TaskLog taskLog = TaskLog.builder().groupId(groupId).logTime(new Date()).logCardId(logCardId).logType(logType).build();
       if(logType == 0 || logType == 1){
            //如果文件不为空，写入上传路径
            if(logFile == null || logFile.isEmpty()) {
                return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"任务上报失败，请选择文件");
            }
            //上传文件路径
            String taskLogPath = FileUtil.getHomePath() + CommonConstants.REPORTING_PATH ;
            //上传文件名
            String taskLogFilename = logFile.getOriginalFilename();
            File logPath = new File(taskLogPath,taskLogFilename);
            //判断路径是否存在，如果不存在就创建一个
            if (!logPath.getParentFile().exists()) {
                logPath.getParentFile().mkdirs();
            }
            logFile.transferTo(logPath);
            //存储的路径（相对路径）
            taskLog.setFilePath(CommonConstants.REPORTING_PATH + taskLogFilename);
            //访问路径（uri）
            taskLog.setFileUrl(CommonConstants.REPORTING_PATH + taskLogFilename);
            taskLog.setFileName(taskLogFilename);
            log.info("上报文件保存路径："+ logPath.getAbsolutePath());
            log.info("上报文件访问uri："+ CommonConstants.REPORTING_PATH + taskLogFilename);
        }else if(logType == 2){
            taskLog.setFileTagging(fileTagging);
        }else{
            return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"上报失败，上报类型错误");
        }
        return taskService.insertMyLog(taskLog);
    }


    /**
     * 查询个人有关的所有任务
     * @param taskCardId 身份证号
     * @param title 标题模糊搜索
     * @param groupStatus 任务组状态状态  正常0 结束1
     * @param type   1：我创建的 2.我执行的 3.我是抄送人
     */
     @GetMapping("/getMyTaskById")
     public  BaseResp<List> getMyTaskById(
             @RequestParam(name = "taskCardId") String taskCardId ,
             @RequestParam(name = "groupStatus") Integer groupStatus ,
             @RequestParam(name = "type[]" ) List<Integer> type ,
             String title ){
       return taskService.queryMyTaskById(taskCardId , title , groupStatus ,type);

     }

    /**
     * 得到任务详情
     * @param groupId
     * @return
     */
     @GetMapping("/getTaskDetailed")
     public BaseResp<TaskDescribe> getMyTaskDetailed(
             @RequestParam("groupId") Integer groupId){
         return taskService.queryTaskDetailed(groupId);
     }
    /**
     * 查询新增任务（可进行拒绝或者点击开始操作）
     * @param taskCardId
     * @param
     * @return
     */
    @GetMapping("/getNewTask")
    public BaseResp<List> getNewTask(String taskCardId ){
        return taskService.queryNewTask(taskCardId);
    }

//    /**
//     * /查询进行、过期、拒绝任务
//     * @param taskCardId
//     * @param
//     * @return
//     */
//    @GetMapping("/getOtherTask")
//    public BaseResp<List> getOtherTask(String taskCardId ){
//        return taskService.queryOtherTask(taskCardId);
//    }

    /**
     * 查询任务日志
     */
    @GetMapping("/getMyTaskLog")
    public BaseResp<List> queryMyTaskLog(Integer groupId){
        return taskService.queryMyTaskLog(groupId);
    }

    /**
     * 修改任务（创建人修改）
     */
    @PostMapping("/updateTask")
    public BaseResp<String> updateTask (TaskGroup taskGroup ,MultipartFile groupTaskFile) throws IOException {
        if(StringUtils.isEmpty(taskGroup.getCreatorCardId()) || taskGroup.getGroupId() == null){
            return new BaseResp<>(ResultStatus.INVALID_PARAM);
        }
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
            groupTaskFile.transferTo(groupFilePath);
            //存储的路径（相对路径）
            taskGroup.setGroupFilepath(CommonConstants.REPORTING_PATH + groupFileName);
            //访问路径（uri）
            taskGroup.setGroupFileurl(CommonConstants.REPORTING_PATH + groupFileName);
            log.info("上报文件保存路径："+groupFilePath.getAbsolutePath());
            log.info("上报文件访问uri："+ CommonConstants.REPORTING_PATH + groupFileName);
        }

        return taskService.updateTask(taskGroup);
    }

    /**
     * 修改个人在任务中的状态
     * @param taskCardId  执行人id
     * @param groupId 组id
     * @param taskState 0未开始 1进行中 2拒绝 3已完成  4逾期
     * @return
     */
    @PutMapping("/updateMyTaskState")
    public BaseResp<String> updateMyTaskState (
            @RequestParam("taskCardId") String taskCardId,
            @RequestParam("groupId")Integer groupId,
            @RequestParam("taskState")Integer taskState) {
        return taskService.updateMyTaskByIdAndGroupId(taskCardId, groupId, taskState);
    }

    /**
     * 修改任务中的状态(创建人结束任务)
     */
    @PutMapping("/updateTaskState")
    public BaseResp<String> updateTaskState (@RequestParam("groupId") Integer groupId) {
        return taskService.updateTaskByIdAndGroupId( groupId );
    }
    /**
     * 删除任务同时删除日志(任务创建人 撤回)
     */
    @DeleteMapping("/deleteMyTask")
    public BaseResp<String> deleteMyTask(@RequestParam("groupId") Integer groupId){
        return taskService.deleteTask(groupId);
    }

}