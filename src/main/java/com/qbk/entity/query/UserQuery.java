package com.qbk.entity.query;

import lombok.Data;

import java.io.Serializable;

/**
 *  Query：数据查询对象，各层接收上层的查询请求。注意超过 2 个参数的查询封装，禁止使用 Map 类来传输。
 */
@Data
public class UserQuery implements Serializable {
    private static final long serialVersionUID = -8273812115235092297L;
    /**
     *   * 是否锁定
     */
    private Boolean isLock;
    private Integer pageNum;
    private Integer pageSize;
}
