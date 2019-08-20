package com.telecomyt.item.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户显示层对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO implements Serializable {
    private static final long serialVersionUID = 2214768243360788573L;
    /**
     * 身份号
     */
    private String cardId ;
    /**
     * 名称
     */
    private String name ;
    /**
     * 头像
     */
    private String headPortrait ;
    /**
     * 任务状态
     */
    private Integer taskState;
}
