
package com.telecomyt.item.web.controller;

import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.TaskLog;
import com.telecomyt.item.utils.BeanValidator;
import com.telecomyt.item.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

@RestController
@RequestMapping("/taskController")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * 新增组
     */
    @PostMapping("/insertNewTask")
    public BaseResp<String> insertNewTask(@RequestBody TaskDto taskDto){
        BeanValidator.check(taskDto);
        return taskService.addTask(taskDto);
    }

    /**
     * 新增任务日志
     */
    @PostMapping("/insertNewLog")
    public  BaseResp<String> insertNewLog(Integer groupId, @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")  Date logTime, String logPicture, String logCardId, Integer logType){
        return taskService.insertLog(groupId,logTime,logPicture,logCardId,logType);
    }

    /**
     * 得到个人任务列表
     */
    @GetMapping("/getTaskList")
    public BaseResp<List> getTaskList(String taskCardId,Integer groupId){
        return  taskService.queryMyTaskById(taskCardId,groupId);
//        Map<String, Object> modleMap = new HashMap<>();
//        List<Task> task = taskService.queryMyTaskById(taskCardid);
//        return new BaseResp<>(ResultStatus.SUCCESS,task);
//        if(task != null) {
//            modleMap.put("task",task);
//        }else {
//            modleMap.put("task","0");
//        }
//        return modleMap;
    }
    //查询新增任务（可进行拒绝或者点击开始操作）
    @GetMapping("/getNewTask")
    public BaseResp<List> getNewTask(String taskCardId,Integer groupId){
        return taskService.queryNewTask(taskCardId,groupId);
    }

    //查询进行、过期、拒绝任务
    @GetMapping("/getOtherTask")
    public BaseResp<List> getOtherTask(String taskCardId,Integer groupId){
        return taskService.queryOtherTask(taskCardId,groupId);
    }

    /**
     * 查询任务日志
     */
    @GetMapping("/getMyTaskLog")
    public BaseResp<TaskLog> queryMyTaskLog(Integer groupId){
        return taskService.queryMyTaskLog(groupId);
    }

    /**
     * 修改个人在任务中的状态
     */
    @PutMapping("/updateMyTaskState")
    public BaseResp<String> updateMyTaskState (String taskCardId, Integer groupId, Integer taskState) {
        return taskService.updateMyTaskByIdAndGroupId(taskCardId, groupId, taskState);
    }

    /**
     * 删除任务同时删除日志
     */
    @DeleteMapping("/deleteMyTask")
    public BaseResp<String> deleteMyTask(String taskCardId,Integer groupId){
        return taskService.deleteTask(taskCardId,groupId);
    }

}
