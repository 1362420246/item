package com.telecomyt.item.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日程列表查询对象
 */
@Data
public class ScheduleListQuery implements Serializable {
    private static final long serialVersionUID = 1981292753101129303L;

    /**
     * 身份证号
     */
    @NotNull(message = "身份证号不能为空")
    private String cardid;

    /**
     * 开始时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    /**
     * 日期类型  1天 2周 3月
     */
    @NotNull(message = "日期类型不能为空")
    private Integer dateType;

    /**
     *   * 重复规则 0:不重复 1每天 2每周 3每月
     */
    private Integer repeatRules ;
    /**
     *   * 开始时间的星期
     */
    private Integer startWeek ;

    /**
     *   * 结束时间的星期
     */
    private Integer endWeek ;

    /**
     *   * 开始时间的日
     */
    private Integer startDayMonth;

    /**
     *   * 结束时间的日
     */
    private Integer endDayMonth;

}
