/**
 * @Author ZhangSF
 * @Date 2019/8/2
 * @Version 1.0
 */



package com.telecomyt.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task implements Serializable {
    private static final long serialVersionUID = -5240558862435141520L;
    private String taskCardId;
    private Integer groupId;
    private Integer taskType;
    private Integer taskState;
    private Integer taskMain;
    private LocalDateTime taskEndTime;
    private String taskFile;
    private String taskCopierId;


}
