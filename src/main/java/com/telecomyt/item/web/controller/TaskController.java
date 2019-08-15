
package com.telecomyt.item.web.controller;

import com.telecomyt.item.constant.CommonConstant;
import com.telecomyt.item.dto.TaskDescribe;
import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.TaskGroup;
import com.telecomyt.item.entity.TaskLog;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.exception.BasicException;
import com.telecomyt.item.utils.BeanValidator;
import com.telecomyt.item.utils.FileUtil;
import com.telecomyt.item.utils.ImageUtils;
import com.telecomyt.item.web.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public BaseResp<Map> insertNewTask(TaskDto taskDto, MultipartFile groupTaskFile) throws IOException {
        BeanValidator.check(taskDto);
        return taskService.addTask(taskDto,groupTaskFile);
    }

//    /**
//     * 新增任务日志（未处理上传）
//     */
//    @PostMapping("/insertNewLog")
//    public  BaseResp<String> insertNewLog(Integer groupId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date logTime, String logPicture, String logCardId, Integer logType){
//        return taskService.insertLog(groupId,logTime,logPicture,logCardId,logType);
//    }

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
            MultipartFile logFile ) throws Exception {
       if(logType == null || groupId == null || logCardId == null ){
            return new BaseResp<>(ResultStatus.INVALID_PARAM);
       }
       TaskLog taskLog = TaskLog.builder().groupId(groupId).logTime(new Date()).logCardId(logCardId).logType(logType).build();
       if(logType == 0 || logType == 1){
            //如果文件不为空，写入上传路径
            if(logFile == null || logFile.isEmpty()) {
                return new BaseResp<>(ResultStatus.FAIL.getErrorCode(),"任务上报失败，请选择文件");
            }
           //上传文件名
           String taskLogFilename = logFile.getOriginalFilename();
           //上传文件路径
           String taskLogPath = null ;
            if(logType == 0 ){
                taskLogPath = FileUtil.getUpload( CommonConstant.TASK_LOG_PICTURE_PATH,logCardId);
            }else {
                taskLogPath = FileUtil.getUpload( CommonConstant.TASK_LOG_FILE_PATH,logCardId);
            }
            File logPath = new File(taskLogPath,taskLogFilename);
            //判断路径是否存在，如果不存在就创建一个
            if (!logPath.getParentFile().exists()) {
                logPath.getParentFile().mkdirs();
            }
            logFile.transferTo(logPath);
            //存储的路径（相对路径）
            taskLog.setFilePath(FileUtil.getRelativePath(logPath.getAbsolutePath()));
            //访问路径（uri）
            taskLog.setFileUrl(FileUtil.getRelativePath(logPath.getAbsolutePath()));
            taskLog.setFileName(taskLogFilename);
            log.info("上报文件保存路径："+ logPath.getAbsolutePath());
          if(logType == 0){
              String zoomPath = FileUtil.getUpload( CommonConstant.TASK_LOG_PICTURE_ZOOM_PATH,logCardId);
              File zoomFile = new File(zoomPath);
              //判断路径是否存在，如果不存在就创建一个
              if (!zoomFile.exists()) {
                  zoomFile.mkdirs();
              }
              ImageUtils.zoomFixedSize(logPath.getAbsolutePath() ,zoomPath , taskLogFilename);
              taskLog.setFileZoomPath( FileUtil.getRelativePath(zoomPath)  + taskLogFilename);
              taskLog.setFileZoomUrl( FileUtil.getRelativePath(zoomPath) + taskLogFilename );
          }
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
             @RequestParam("groupId") Integer groupId ,
             @RequestParam("cardid") String cardid){
         return taskService.queryTaskDetailed(groupId , cardid);
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
     * @param state 为1的时候 删除源文件
     */
    @PostMapping("/updateTask")
    public BaseResp<String> updateTask (TaskGroup taskGroup ,MultipartFile groupTaskFile ,@RequestParam(value = "state",defaultValue = "0")  Integer state) throws IOException {
        if(StringUtils.isEmpty(taskGroup.getCreatorCardId()) || taskGroup.getGroupId() == null){
            return new BaseResp<>(ResultStatus.INVALID_PARAM);
        }
        if(groupTaskFile != null && !groupTaskFile.isEmpty()) {
            //上传文件名
            String groupFileName = groupTaskFile.getOriginalFilename();
            String taskGroupFilePath = FileUtil.getUpload( CommonConstant.TASK_FILE_PATH,taskGroup.getCreatorCardId()); ;
            File groupFilePath = new File(taskGroupFilePath,groupFileName);
            //判断路径是否存在，如果不存在就创建一个
            if (!groupFilePath.getParentFile().exists()) {
                groupFilePath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            groupTaskFile.transferTo(groupFilePath);
            //存储的路径（相对路径）
            taskGroup.setGroupFilepath(FileUtil.getRelativePath(groupFilePath.getAbsolutePath()));
            //访问路径（uri）
            taskGroup.setGroupFileurl(FileUtil.getRelativePath(groupFilePath.getAbsolutePath()));
            taskGroup.setGroupFilename(groupFileName);
            log.info("上报文件保存路径："+groupFilePath.getAbsolutePath());
        }else {
            if( state == 1){
                taskGroup.setGroupFilepath("");
                taskGroup.setGroupFileurl("");
                taskGroup.setGroupFilename("");
            }
        }
        return taskService.updateTask(taskGroup);
    }

    /**
     * 修改个人在任务中的状态
     * @param taskCardId  执行人id
     * @param groupId 组id
     * @param taskState 0未开始 1进行中 2拒绝 3已完成  4逾期
     * @param reason  拒绝理由 可以不写
     * @return
     */
    @PutMapping("/updateMyTaskState")
    public BaseResp<String> updateMyTaskState (
            @RequestParam("taskCardId") String taskCardId,
            @RequestParam("groupId")Integer groupId,
            @RequestParam("taskState")Integer taskState,
            String reason) {
        return taskService.updateMyTaskByIdAndGroupId(taskCardId, groupId, taskState,reason);
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
     * @param reason 撤回理由 可以没有
     */
    @DeleteMapping("/deleteMyTask")
    public BaseResp<String> deleteMyTask(@RequestParam("groupId") Integer groupId ,String reason){
        return taskService.deleteTask(groupId ,reason);
    }

}