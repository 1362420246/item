

package com.telecomyt.item.web.mapper;

import com.telecomyt.item.entity.TaskDo;
import com.telecomyt.item.entity.TaskGroup;
import com.telecomyt.item.entity.TaskLog;
import com.telecomyt.item.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

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
     *增加日志
     * @param groupId
     * @param logTime
     * @param logPicture
     * @param logCardId
     * @return
     */
   int insertLog(@Param("groupId") int groupId, @Param("logTime") Date logTime, @Param("logPicture") String logPicture, @Param("logCardId") String logCardId);

    /**
     *  查询任务列表
     * @param taskCardId
     * @return
     */
   List<Task> queryMyTaskById(String taskCardId);

    /**
     *查询任务日志
     * @param groupId
     * @return
     */
   TaskLog queryMyTaskLogById(int groupId);
    /**
     *改变个人任务状态
     * @param taskCardId
     * @param groupId
     * @param taskState
     * @return
     */
    int updateMyTaskByIdAndGroupId(@Param("taskCardId") String taskCardId, @Param("groupId") int groupId, @Param("taskState") int taskState);

    /**
     *删除任务
     * @param taskCardId
     * @param groupId
     * @return
     */
   int deleteTask(String taskCardId, int groupId);

    /**
     * 删除任务日志
     * @param groupId
     * @return
     */

   int deleteTaskLog(int groupId);


}












