package com.telecomyt.item.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @ProjectName: database
 * @Package: com.telecomyt.core.model
 * @ClassName: CacheUser
 * @Description: 缓存redis 对象
 * @Author: zhangkai
 * @CreateDate: 2019/7/30 15:28
 * @UpdateUser:
 * @UpdateDate: 2019/7/30 15:28
 * @UpdateRemark:
 */
@Data
public class CacheUser implements Serializable {

    /**
     *
     */
    private String uid;

    /**
     * 用户名
     */
    private String name;

    /**
     * 手机号
     */
    private String telephone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 部门全称
     */
    private String deptName;

    /**
     * 部门编码
     */
    private String deptNo;

    /**
     * imei号码
     */
    private String imei;

}
