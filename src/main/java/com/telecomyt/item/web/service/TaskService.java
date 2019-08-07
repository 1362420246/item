
package com.telecomyt.item.web.service;

import com.telecomyt.item.dto.TaskDescribe;
import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.Task;
import com.telecomyt.item.entity.TaskGroup;
import com.telecomyt.item.entity.TaskLog;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */


public interface TaskService {
    /**
     * 新增组
     * @param taskDto
     * @return
     */
    //,MultipartFile grouptaskFile   throws IOException
    BaseResp<String> addTask(TaskDto taskDto,MultipartFile groupTaskFile) throws IOException;
//    /**
//     *
//     */
//    boolean insertGroup(String creatorCardid, String sheetTitle, String sheetDescribe, Date endTime);
//
//    /**
//     *
//     */
//    boolean insertTask(String taskId, int groupId, int taskType, int taskState, int taskMain, Date taskEndTime, String taskFile);

    /**
     * 新增日志
     * @param groupId
     * @param logTime
     * @param logPicture
     * @param logCardId
     * @return
     */
    BaseResp<String> insertLog(Integer groupId, Date logTime, String logPicture, String logCardId,Integer logType);
    BaseResp<String> insertMyLog(TaskLog taskLog);

//    /**
//     * 查询个人任务列表
//     * @param taskCardId
//     * @return
//     */
//    BaseResp<sLit> queryMyTaskById(String taskCardId,Integer groupId);
    //查询个人所有任务
      BaseResp<List> queryMyTaskById(String taskCardId);
      //查询任务详情
      BaseResp<TaskDescribe>  queryTaskDetailed(Integer groupId);


    /**
     * 查询新增加任务
     * @param taskCardId
     * @param
     * @return
     */
    BaseResp<List> queryNewTask(String taskCardId);

//    /**
//     * 查询过期、拒绝、进行中任务
//     * @param taskCardId
//     * @param
//     * @return
//     */
//    BaseResp<List> queryOtherTask(String taskCardId);
    /**
     * 查询日志信息
     * @param groupId
     * @return
     */
    BaseResp<List> queryMyTaskLog(Integer groupId);

    /**
     * 修改任务
     */
    BaseResp<String> updateTask(TaskGroup taskGroup);
    /**
     * 更改个人任务状态
     * @param taskCardId
     * @param groupId
     * @param taskState
     * @return
     */
    BaseResp<String> updateMyTaskByIdAndGroupId(String taskCardId, Integer groupId, Integer taskState);
    /**
     * 更改任务状态（创建者）
     */
    BaseResp<String> updateTaskByIdAndGroupId( Integer groupId, Integer taskState);
    /**
     *
     * 删除任务
     * @param creatorCardId
     * @param groupId
     * @return
     */
    BaseResp<String> deleteTask(String creatorCardId, Integer groupId);


}
