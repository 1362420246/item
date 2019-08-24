package com.qbk.log.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysLog implements Serializable {

    private static final long serialVersionUID = -5219408738169689511L;

    /**
     * 日志id
     */
    private Long id;

    /**
     * 日志类型
     */
    @Builder.Default
    private String type = "1";

    /**
     * 日志标题
     */
    private String title;

    /**
     * 创建者
     */
    private String createUser;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 操作者IP
     */
    private String remoteAddr;

    /**
     *
     */
    private String userAgent;

    /**
     * 请求uri
     */
    private String requestUri;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时间
     */
    @Builder.Default
    private Long time = 0L;

    /**
     * 返回结果
     */
    private String returnMsg;

    /**
     * 异常信息
     */
    private String exception;

}

