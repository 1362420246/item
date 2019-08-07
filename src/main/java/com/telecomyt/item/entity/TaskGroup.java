
package com.telecomyt.item.entity;

import com.telecomyt.item.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskGroup implements Serializable {
    private static final long serialVersionUID = 8240255160784435225L;
    private Integer groupId;
    private String creatorCardId;
    private String sheetTitle;
    private String sheetDescribe;
    private LocalDateTime taskEndTime;
    private String taskFileUrl;
    private String taskFilePath;
    private Integer taskType;
    private LocalDateTime taskCreatTime;



    public  TaskGroup(TaskDto taskDto){
        this.creatorCardId = taskDto.getCreatorCardId();
        this.sheetDescribe = taskDto.getSheetDescribe();
        this.sheetTitle = taskDto.getSheetTitle();
        this.taskEndTime = taskDto.getTaskEndTime();
        this .taskType = taskDto.getTaskType();
        this.taskCreatTime = LocalDateTime.now();
    }
}
