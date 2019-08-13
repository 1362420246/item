package com.telecomyt.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime taskEndTime;
    private String sheetDescribe;
    private String sheetTitle;
    private String taskCreattime;
    private List<String> taskCardIds;
    /**
     * 是否被逾期 0：否  1：是逾期
     */
    private Integer isOverdue ;

}
