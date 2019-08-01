package com.telecomyt.item.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
public class ScheduleDto implements Serializable {

    private static final long serialVersionUID = -2219223120814315631L;

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
    private LocalDateTime startTime;

    /**
     *   * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
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

    /**
     *   * 创建人身份证
     */
    @NotNull(message = "创建人身份证不能为空")
    private String creatorCardid;

    /**
     *   * 关联人身份证
     */
    private List<String> affiliatedCardids;

}
