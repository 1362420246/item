
package com.telecomyt.item.web.controller;

import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.TaskLog;
import com.telecomyt.item.utils.BeanValidator;
import com.telecomyt.item.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public  BaseResp<String> insertNewLog(int groupId, Date logTime, String logPicture, String logCardId,int logType){
        return taskService.insertLog(groupId,logTime,logPicture,logCardId,logType);
    }

    /**
     * 得到个人任务列表
     */
    @GetMapping("/getTaskList")
    public BaseResp<List> getTaskList(String taskCardId){
        return  taskService.queryMyTaskById(taskCardId);
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

    /**
     * 查询任务日志
     */
    @GetMapping("/getMyTaskLog")
    public BaseResp<TaskLog> queryMyTaskLog(int groupId){
        return taskService.queryMyTaskLog(groupId);
    }

    /**
     * 修改个人在任务中的状态
     */
    @PutMapping("/updateMyTaskState")
    public BaseResp<String> updateMyTaskState (String taskCardId, int groupId, int taskState) {
        return taskService.updateMyTaskByIdAndGroupId(taskCardId, groupId, taskState);
    }

    /**
     * 删除任务同时删除日志
     */
    @DeleteMapping("/deleteMyTask")
    public BaseResp<String> deleteMyTask(String taskCardId,int groupId){
        return taskService.deleteTask(taskCardId,groupId);
    }

}
