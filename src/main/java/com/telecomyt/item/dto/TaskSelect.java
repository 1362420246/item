package com.telecomyt.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author ZhangSF
 * @Date 2019/8/6 15:58
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class TaskSelect implements Serializable {
    private static final long serialVersionUID = 704551513185815840L;
    private Integer groupId;
    private Integer taskType;
    private Integer taskEndTime;
    private Integer sheetDescribe;
    private Integer sheetTitle;






}
