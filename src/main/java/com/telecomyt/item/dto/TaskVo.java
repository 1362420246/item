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

    /**
     * 创建时间
     */
    private String taskCreattime ;

    /**
     * 组id
     */
    private String groupId ;

}
