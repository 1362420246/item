
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
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
    //,MultipartFile grouptaskFile  throws IOException
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
     * @param
     * @return
     */
   @PostMapping("/insertMyLog")
    public  BaseResp<String> insertMyLog(Integer groupId,@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date logTime, MultipartFile logFile, String logCardId, Integer logType,String fileTagging) throws IOException {
        if(logType == null || groupId == null || logCardId == null ){
            return new BaseResp<>(ResultStatus.INVALID_PARAM);
       }
       TaskLog taskLog = TaskLog.builder().groupId(groupId).logTime(logTime).logCardId(logCardId).logType(logType).build();

       if(logType == 0 || logType == 1){
            //如果文件不为空，写入上传路径
            if(logFile.isEmpty()) {
                return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"任务上报失败，请选择文件");
            }
            //上传文件路径
            String taskLogPath = FileUtil.getHomePath() + CommonConstants.REPORTING_PATH ;
            //上传文件名
            String taskLogFilename = logFile.getOriginalFilename();
            File LogPath = new File(taskLogPath,taskLogFilename);
            //判断路径是否存在，如果不存在就创建一个
            if (!LogPath.getParentFile().exists()) {
                LogPath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            File saveTaskLogFile = new File(taskLogPath + taskLogFilename);
            logFile.transferTo(saveTaskLogFile);
            //存储的路径（相对路径）
            taskLog.setFilePath(CommonConstants.REPORTING_PATH + taskLogFilename);
            //访问路径（uri）
            taskLog.setFileUrl(CommonConstants.REPORTING_PATH + taskLogFilename);
            log.info("上报文件保存路径："+saveTaskLogFile.getAbsolutePath());
            log.info("上报文件保存路径2："+ FileUtil.getHomePath() + CommonConstants.REPORTING_PATH + taskLogFilename);
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
     * @param taskCardId
     * @return
     */
     @GetMapping("/getMyTaskById")
     public  BaseResp<List> getMyTaskById(String taskCardId){
       return taskService.queryMyTaskById(taskCardId);

     }

    /**
     * 得到任务详情
     * @param groupId
     * @return
     */
     @GetMapping("/getTaskDetailed")
     public BaseResp<TaskDescribe> getMyTaskDetailed(Integer groupId){
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
     * 修改任务
     */
    @PutMapping("/updateTask")
    public BaseResp<String> updateTask (TaskGroup taskGroup) {
        return taskService.updateTask(taskGroup);
    }

    /**
     * 修改个人在任务中的状态
     */
    @PutMapping("/updateMyTaskState")
    public BaseResp<String> updateMyTaskState (String taskCardId, Integer groupId, Integer taskState) {
        return taskService.updateMyTaskByIdAndGroupId(taskCardId, groupId, taskState);
    }
    /**
     * 人物创建人员结束任务
     */
    /**
     * 修改个人在任务中的状态(执行人修改)
     */
    @PutMapping("/updateTaskState")
    public BaseResp<String> updateTaskState (  Integer groupId, Integer taskState) {
        return taskService.updateTaskByIdAndGroupId( groupId, taskState);
    }
    /**
     * 删除任务同时删除日志
     */
    @DeleteMapping("/deleteMyTask")
    public BaseResp<String> deleteMyTask(String creatorCardId,Integer groupId){
        return taskService.deleteTask(creatorCardId,groupId);
    }

}


//    /**
//     * 得到个人任务列表
//     */
//    @GetMapping("/getTaskList")
//    public BaseResp<List> getTaskList(String taskCardId,Integer groupId){
//        return  taskService.queryMyTaskById(taskCardId,groupId);
//        Map<String, Object> modleMap = new HashMap<>();
//        List<Task> task = taskService.queryMyTaskById(taskCardid);
//        return new BaseResp<>(ResultStatus.SUCCESS,task);
//        if(task != null) {
//            modleMap.put("task",task);
//        }else {
//            modleMap.put("task","0");
//        }
//        return modleMap;
//}
