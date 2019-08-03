
package com.telecomyt.item.web.service;

import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.TaskLog;
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
    BaseResp<String> addTask(TaskDto taskDto);
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
    BaseResp<String> insertLog(int groupId, Date logTime, String logPicture, String logCardId);

    /**
     * 查询个人任务列表
     * @param taskCardId
     * @return
     */
    BaseResp<List> queryMyTaskById(String taskCardId);

    /**
     * 查询日志信息
     * @param groupId
     * @return
     */
    BaseResp<TaskLog> queryMyTaskLog(int groupId);

    /**
     * 更改个人任务状态
     * @param taskCardId
     * @param groupId
     * @param taskState
     * @return
     */
    BaseResp<String> updateMyTaskByIdAndGroupId(String taskCardId, int groupId, int taskState);

    /**
     * 删除任务
     * @param taskCardId
     * @param groupId
     * @return
     */
    BaseResp<String> deleteTask(String taskCardId, int groupId);


}
