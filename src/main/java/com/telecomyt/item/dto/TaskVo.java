package com.telecomyt.item.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 任务显示层
 */
@Data
public class TaskVo implements Serializable {
    private static final long serialVersionUID = 241258762134364837L;
    /**
     * 任务组id
     */
    private Integer groupId ;
    /**
     * 任务类型 1-接受任务  2-抄送任务
     */
    private Integer taskType ;
    /**
     * 0-任务未开始1-任务已开始  2-任务已拒绝
     */
    private Integer taskState ;
    /**
     * 任务主从关系 0-是发布人 1-非发布人
     */
    private Integer taskMain ;
    /**
     * 任务说明文档
     */
    private String taskFile ;
    /**
     * 任务结束时间
     */
    private LocalDateTime taskEndtime ;
    /**
     * 任务标题
     */
    private String sheetTitle ;
    /**
     * 任务描述
     */
    private String sheetDescribe ;

}
