
/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

package com.telecomyt.item.web.mapper;

import com.telecomyt.item.dto.ScheduleDto;
import com.telecomyt.item.dto.TaskDto;
import com.telecomyt.item.entity.TaskDo;
import com.telecomyt.item.entity.TaskGroup;
import com.telecomyt.item.entity.TaskLog;
import com.telecomyt.item.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface TaskMapper {
    /**
     *

     * @return
     */
    int insertGroup(TaskGroup taskGroup);

    /**
     *

     * @return
     */
    int insertTask(TaskDo taskDo);

    /**
     *
     * @param groupId
     * @param logTime
     * @param logPicture
     * @param logCardId
     * @return
     */
   int insertLog(@Param("groupId") int groupId, @Param("logTime") Date logTime, @Param("logPicture") String logPicture, @Param("logCardId") String logCardId);

    /**
     *
     * @param taskCardid
     * @return
     */
   List<Task> queryMyTaskById(String taskCardid);

    /**
     *
     * @param groupId
     * @return
     */
   List<TaskLog> queryMyLogByGroupId(String groupId);

    /**
     *
     * @param taskCardId
     * @param groupId
     * @return
     */
   int deleteTask(String taskCardId, String groupId);

    /**
     *
     * @param taskCardId
     * @param groupId
     * @param taskState
     * @return
     */
   int updateMyTaskByIdAndGroupId(@Param("taskCardId") String taskCardId, @Param("groupId") int groupId, @Param("taskState") int taskState);
}












