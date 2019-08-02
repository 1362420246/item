package com.telecomyt.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 修改日程参数对象
 */
@Data
public class ScheduleUpdateParam implements Serializable {

    private static final long serialVersionUID = -5981108391353912678L;

    /**
     *   *  组id  groupId
     */
    @NotNull(message = "组id不能为空")
    @JsonProperty("groupId")
    private Integer id;

    /**
     *   * 日程标题
     */
    @NotNull(message = "日程标题不能为空")
    private String scheduleTitle;

    /**
     *   * 日程描述
     */
    private String scheduleDescribe;

    /**
     *   * 开始时间
     */
    @NotNull(message = "开始时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     *   * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     *   * 是否重复  0：不重复  1：重复
     */
    @NotNull(message = "是否重复不能为空")
    private Boolean isRepeat;

    /**
     *   * 重复规则 0:不重复 1每天 2每周 3每月
     */
    @NotNull(message = "重复规则不能为空")
    private Integer repeatRules;

}
