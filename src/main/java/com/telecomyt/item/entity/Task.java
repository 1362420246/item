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
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task implements Serializable {
    private static final long serialVersionUID = -5240558862435141520L;
    private String taskCardId;
    private int groupeId;
    private int taskType;
    private int taskState;
    private int taskMain;
    private Date taskEndTime;
    private String taskFile;


}
