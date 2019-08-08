
package com.telecomyt.item.entity;

import com.telecomyt.item.dto.TaskDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime taskEndTime;
    /**
     * '任务说明文档请求路径
     */
    private String groupFileurl;
    /**
     * 任务说明文档存储路径
     */
    private String groupFilepath;

    /**
     * 任务说明文档存储路径
     */
    private String groupFilename;

    private Integer taskType;
    private LocalDateTime taskCreatTime;
    /**
     * '正常0 结束1 默认0'
     */
    private Integer groupStatus ;

    /**
     * 是否被删除 0：否  1：是删除
     */
    private Integer isDelete ;

    /**
     * 是否被逾期 0：否  1：是逾期
     */
    private Integer isOverdue ;

    public  TaskGroup(TaskDto taskDto){
        this.creatorCardId = taskDto.getCreatorCardId();
        this.sheetDescribe = taskDto.getSheetDescribe();
        this.sheetTitle = taskDto.getSheetTitle();
        this.taskEndTime = taskDto.getTaskEndTime();
        this.taskCreatTime = LocalDateTime.now();
    }
}
