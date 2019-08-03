package com.telecomyt.item.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ZhangSF
 * @Date 2019/8/3 14:28
 * @Version 1.0
 */

@Data
public class TaskInfoDto implements Serializable {

    private static final long serialVersionUID = -8717474322435171318L;
    private String task_CardId;
    private int group_Id;
    private int task_Type;
    private int task_State;
    private int task_Main;
    private int task_File;
    private int task_Endtime;
}
