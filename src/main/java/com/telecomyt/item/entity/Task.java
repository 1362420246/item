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

public class Task implements Serializable {
    private static final long serialVersionUID = -5240558862435141520L;
    private String taskCardId;
    private Integer groupId;
    private Integer taskType;
    private Integer taskState;
    private LocalDateTime taskEndTime;
    private String taskCopierId;



}
