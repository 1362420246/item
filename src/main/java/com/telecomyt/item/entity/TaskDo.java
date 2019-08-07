package com.telecomyt.item.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Author ZhangSF
 * @Date 2019/8/3 14:50
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskDo implements Serializable {

    private static final long serialVersionUID = 6214878095480373924L;

    private List<String> taskCardIds;
    private Integer groupId;
    private Integer taskType;
    private Integer taskState;
    private LocalDateTime taskEndTime;
    private  List<String> taskCopierIds;


    
}
