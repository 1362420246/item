/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */



package com.telecomyt.item.web.controller;


import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.Group;
import com.telecomyt.item.entity.Log;
import com.telecomyt.item.entity.Task;
import com.telecomyt.item.enums.ResultStatus;
import com.telecomyt.item.web.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/taskController")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/insertNewTask")
    public boolean insertNewTask(String creatorCardid, String sheetTitle, String sheetDescribe, Date endTime,
                                 String taskId, int taskType, int taskState, int taskMain,
                                 Date taskEndTime,String taskFile){
        Group group = new Group();
        boolean flag0 = taskService.insertGroup(creatorCardid,sheetTitle,sheetDescribe,endTime);
        try {
            int groupId = group.getGroupId();
            if(flag0){
                boolean flag1 =taskService.insertTask(taskId,groupId,taskType,taskState,taskMain,taskEndTime,taskFile);
                if(!flag1){
                    return false; }
            }else {
                return false;}

        } catch (Exception e) {
            e.printStackTrace(); }

        return true;
    }
    @PostMapping("/insertNewLog")
    public  boolean insertNewLog(int groupId, Date logTime, String logPicture, String logCardId){
               boolean flag = taskService.insertLog(groupId,logTime,logPicture,logCardId);
               if(!flag){
                   return false;}

        return true;
    }
    @GetMapping("/getTaskList")
    public BaseResp<List> getTaskList(String taskCardid){
//        Map<String, Object> modleMap = new HashMap<>();
        List<Task> task = taskService.queryMyTaskById(taskCardid);
        return new BaseResp<>(ResultStatus.SUCCESS,task);
//        if(task != null) {
//            modleMap.put("task",task);
//        }else {
//            modleMap.put("task","0");
//        }
//        return modleMap;
    }

    @GetMapping("/getTaskAndLogList")
     public Map<String,Object> getTaskList(String taskCardid,String groupId){
        Map<String, Object> modleMap = new HashMap<>();
        List<Task> task = taskService.queryMyTaskById(taskCardid);
        if(task != null) {
            modleMap.put("task",task);
        }else {
            modleMap.put("task","00");
        }
        List<Log> log = taskService.queryMyLogByGroupId(groupId);
        if(log != null) {
            modleMap.put("log",log);
        }else {
            modleMap.put("log","0");
        }
        return modleMap;
    }
    @PatchMapping("/updateMyTaskState")
    public boolean updateMyTaskState (String taskCardId, int groupId, int taskState){
         boolean flag = taskService.updateMyTaskByIdAndGroupId(taskCardId,groupId,taskState);
         if(!flag){
             return  false;}
         return true;
    }


    @DeleteMapping("/deleteMyTask")
       public boolean deleteMyTask(String taskCardId,String groupId){
           boolean flag = taskService.deleteTask(taskCardId,groupId);
           if(!flag){
               return false;
           }
          return true;
    }




}
