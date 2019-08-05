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
    private Integer groupId ;
    private Integer taskType ;
    private Integer taskState ;
    private Integer taskMain ;
    private String taskFile ;
    private LocalDateTime taskEndtime ;
    private String sheetTitle ;
    private String sheetDescribe ;

}
