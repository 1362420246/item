package com.telecomyt.item.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author ZhangSF
 * @Date 2019/8/3 14:28
 * @Version 1.0
 */

@Data
public class TaskInfoDto implements Serializable {

    private static final long serialVersionUID = -8717474322435171318L;
    private String taskCardId;
    private Integer groupId;
    private Integer taskType;
    private Integer taskState;
    private LocalDateTime taskEndtime;
}
