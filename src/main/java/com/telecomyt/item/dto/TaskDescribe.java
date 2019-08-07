package com.telecomyt.item.dto;

import com.telecomyt.item.entity.Task;
import com.telecomyt.item.entity.TaskLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
         private LocalDateTime taskCreatTime;
         private List<TaskLog> taskLogs ;

}
