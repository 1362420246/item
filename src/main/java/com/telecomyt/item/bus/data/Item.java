package com.telecomyt.item.bus.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item implements Serializable {
    private static final long serialVersionUID = 5282788716958640711L;

    /**
     * 任务创建者信息
     */
    private Creator creator;

    /**
     * 任务主题
     */
    private String title;

    /**
     * 任务内容
     */
    private String description ;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 是否分享到即时消息，默认分享
     */
    @Builder.Default
    private Boolean share = true ;


}
