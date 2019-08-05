

package com.telecomyt.item.web.mapper;

import com.telecomyt.item.dto.TaskVo;
import com.telecomyt.item.entity.TaskDo;
import com.telecomyt.item.entity.TaskGroup;
import com.telecomyt.item.entity.TaskLog;
import com.telecomyt.item.entity.Task;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

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
     * @param taskDo
     * @return
     */
    int insertCoperTask(TaskDo taskDo);

    /**
     *增加日志
     * @param groupId
     * @param logTime
     * @param logPicture
     * @param logCardId
     * @return
     */
   int insertLog(@Param("groupId") Integer groupId, @Param("logTime") Date logTime, @Param("logPicture") String logPicture, @Param("logCardId") String logCardId,@Param("logType") Integer logType);
    /**
     *  查询任务列表
     * @param taskCardId
     * @return
     */
   List<Task> queryMyTaskById(@Param("taskCardId") String taskCardId,@Param("groupId")Integer groupId);


   List<Task> queryNewTask(@Param("taskCardId") String taskCardId,@Param("groupId")Integer groupId);
   List<Task>queryOtherTask(@Param("taskCardId") String taskCardId,@Param("groupId")Integer groupId);
    /**
     *查询任务日志
     * @param groupId
     * @return
     */
   TaskLog queryMyTaskLogById(Integer groupId);
    /**
     *改变个人任务状态
     * @param taskCardId
     * @param groupId
     * @param taskState
     * @return
     */
    int updateMyTaskByIdAndGroupId(@Param("taskCardId") String taskCardId, @Param("groupId") Integer groupId, @Param("taskState") Integer taskState);

    /**
     *删除任务
     * @param taskCardId
     * @param groupId
     * @return
     */
   int deleteTask(@Param("taskCardId") String taskCardId,@Param("groupId") Integer groupId);

    /**
     * 删除任务日志
     * @param groupId
     * @return
     */

   int deleteTaskLog(Integer groupId);

    /**
     * 按照身份证和时间去查询任务
     * @param cardid 身份证号
     * @param startTime 开始时间
     * @param endTime 结束时间
     */
    List<TaskVo> getTaskByCardIdAndDate(
            @Param("cardid") String cardid, @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

}












