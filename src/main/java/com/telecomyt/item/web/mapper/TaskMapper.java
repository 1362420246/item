

package com.telecomyt.item.web.mapper;

import com.telecomyt.item.dto.*;
import com.telecomyt.item.entity.*;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

public interface TaskMapper {
    /**
     *x新增组
     * @return
     */
    int insertGroup(TaskGroup taskGroup);

    /**
     *新增任务
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
     * 新增任务日志（处理上传）
     * @param taskLog
     * @return
     */
   int insertMyLog(TaskLog taskLog);
//   /**
//     *  查询任务列表
//     * @param taskCardId
//     * @return
//     */
//   List<Task> queryMyTaskById(@Param("taskCardId") String taskCardId,@Param("groupId")Integer groupId);

    /**
     * 查询个人所有任务
     * @param creatorCardId
     * @param title
     * @return
     */
    List<TaskSelect> queryMyCreatTask(@Param("creatorCardId") String creatorCardId, @Param("tile")String title);
    List<TaskSelect> queryMyAcceptTask(@Param("taskCopierId")String taskCopierId, @Param("tile")String title);
    List<TaskSelect> queryMyCopperTask(@Param("taskCardId")String taskCardId, @Param("tile")String title);

    /**
     * 下两个为查询新增任务和其他任务
     * @param
     *
     */
   List<Task> queryNewTask(@Param("taskCardId") String taskCardId);
//   List<Task>queryOtherTask(@Param("taskCardId") String taskCardId);
    /**
     *查询任务日志
     * @param groupId
     * @return
     */
    List<TaskLog> queryMyTaskLogById(Integer groupId);
    /**
     * 查询任务详情
     */
    TaskDescribe queryTaskDetailed(Integer groupId);
    /**
     * g更改任务
     */
    int updateTask(TaskGroup taskGroup);
    /**
     *改变个人任务状态
     * @param taskCardId
     * @param groupId
     * @param taskState
     * @return
     */
    int updateMyTaskByIdAndGroupId(@Param("taskCardId") String taskCardId, @Param("groupId") Integer groupId, @Param("taskState") Integer taskState);
    /**
     * g更改任务状态（创建者）
     */
    int updateTaskByIdAndGroupId ( @Param("groupId") Integer groupId, @Param("taskState") Integer taskState);
    /**
     *删除任务
     * @param creatorCardId
     * @param groupId
     * @return
     */
   int deleteTask(@Param("creatorCardId") String creatorCardId,@Param("groupId") Integer groupId);

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

    /**
     *  查询执行人身份照
     */
    List<TaskIdState> queryTaskCardId(Integer groupId);

    /**
     * 查询抄送人身份照
     */
    List<TaskIdState> queryTaskCopierId(Integer groupId);
}












