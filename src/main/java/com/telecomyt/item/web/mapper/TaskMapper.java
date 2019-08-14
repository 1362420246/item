

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
     * 查询个人有关的所有任务
     * @param creatorCardId 身份证号
     * @param title 标题模糊搜索
     * @param groupStatus 任务组状态状态
     */
    List<TaskSelect> queryMyCreatTask(@Param("creatorCardId") String creatorCardId, @Param("title") String title,@Param("groupStatus") Integer groupStatus);

    /**
     * 查询个人有关的执行的任务
     * @param taskCardId 身份证号
     * @param title 标题模糊搜索
     * @param groupStatus 状态
     */
    List<TaskSelect> queryMyAcceptTask(@Param("taskCardId") String taskCardId, @Param("title") String title,@Param("groupStatus") Integer groupStatus);

    /**
     * 查询个人有关的抄送的任务
     * @param taskCopierId 身份证号
     * @param title 标题模糊搜索
     * @param groupStatus 状态
     */
    List<TaskSelect> queryMyCopperTask(@Param("taskCopierId") String taskCopierId, @Param("title") String title,@Param("groupStatus") Integer groupStatus);


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
     * g更改任务状态（创建人结束任务）
     */
    int updateTaskByIdAndGroupId ( @Param("groupId") Integer groupId);
    /**
     *删除任务
     * @param groupId
     * @return
     */
   int deleteTask(@Param("groupId") Integer groupId);

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

    /**
     * 通过 组id 查询任务组
     * @param groupId 组id
     * @return
     */
    TaskGroup getTaskGroupByGroupId(Integer groupId);

    /**
     * 修改逾期
     * @param groupId 组id
     */
    int updateOverdue(Integer groupId);

    /**
     * 根据组id 获取执行人
     */
    List<String> getExecutorByGroupId(Integer groupId);

    /**
     * 查询执行者拒绝的任务
     */
    List<String> getDeleteTask(String cardid, LocalDateTime startTime, LocalDateTime endTime);
}












