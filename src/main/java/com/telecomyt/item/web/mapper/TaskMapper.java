
/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */

package com.telecomyt.item.web.mapper;

import com.telecomyt.item.entity.Log;
import com.telecomyt.item.entity.Task;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;


public interface TaskMapper {
    /**
     *
     * @param creatorCardid
     * @param sheetTitle
     * @param sheetDescribe
     * @param endTime
     * @return
     */
    int insertGroup(@Param("creatorCardid") String creatorCardid, @Param("sheetTitle") String sheetTitle, @Param("sheetDescribe") String sheetDescribe, @Param("endTime") Date endTime);

    /**
     *
     * @param taskId
     * @param groupId
     * @param taskType
     * @param taskState
     * @param taskMain
     * @param taskEndTime
     * @param taskFile
     * @return
     */
    int insertTask(@Param("taskId") String taskId, @Param("groupId") int groupId, @Param("taskType") int taskType, @Param("taskState") int taskState, @Param("taskMain") int taskMain, @Param("taskEndTime") Date taskEndTime, @Param("taskFile") String taskFile);

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
   List<Log> queryMyLogByGroupId(String groupId);

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












