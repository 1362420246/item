
/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */
package com.telecomyt.item.web.service;

import com.telecomyt.item.dto.ScheduleDto;
import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.dto.resp.BaseResp;
import com.telecomyt.item.entity.TaskLog;
import com.telecomyt.item.entity.Task;

import java.util.Date;
import java.util.List;

/**
 *
 */

public interface TaskService {

//    增加组
        BaseResp<String> addTask(TaskDto taskDto);
    /**
     *
     */
    boolean insertGroup(String creatorCardid, String sheetTitle, String sheetDescribe, Date endTime);

    /**
     *
     */
    boolean insertTask(String taskId, int groupId, int taskType, int taskState, int taskMain, Date taskEndTime, String taskFile);

    /**
     *
     */
    boolean insertLog(int groupId, Date logTime, String logPicture, String logCardId);

    /**
     *
     */
    List<Task> queryMyTaskById(String taskCardid);

    /**
     *
     */
    List<TaskLog> queryMyLogByGroupId(String groupId);

    /**
     *
     */
    boolean deleteTask(String taskCardId, String groupId);

    /**
     *
     */
    boolean updateMyTaskByIdAndGroupId(String taskCardId, int groupId, int taskState);

    /**
     *
     */


}
