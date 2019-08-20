package com.telecomyt.item.dto;

import com.telecomyt.item.entity.TaskLog;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author ZhangSF
 * @Date 2019/8/6 18:00
 * @Version 1.0
 */
@Data


public class TaskDescribe implements Serializable {
     private static final long serialVersionUID = 344789766099230866L;
     private Integer groupId ;
     private String sheetDescribe;
     private String creatorCardId;
     private List<TaskIdState> taskCardId;
     private LocalDateTime taskEndTime;
     private List<TaskIdState> taskCopierId;
     private String  groupFileUrl;
     private String  groupFileName;
     private LocalDateTime taskCreatTime;
     private List<TaskLog> taskLogs ;
     private String sheetTitle;

     /**
      * 是否逾期
      */
     private Integer is0verdue;
     /**
      *  任务组状态
      */
     private Integer groupStatus;

     /**
      * 创建人用户信息
      */
     private UserVO creatorUser;
     /**
      * 执行人用户信息
      */
     private List<UserVO> taskCardUsers;
     /**
      * 抄送人用户信息
      */
     private List<UserVO> taskCopierUsers;

     /**
      *  0-创建者1-接受任务  2-抄送任务
      */
     private Integer taskType;

}
