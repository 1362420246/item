package com.telecomyt.item.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    /**
     * 日期类型  1天 2周 3月
     */
    @NotNull(message = "日期类型不能为空")
    private Integer dateType;

}
