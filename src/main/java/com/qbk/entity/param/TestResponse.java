package com.qbk.entity.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 测试参数相应
 */
@Data
public class TestResponse implements Serializable {

    private static final long serialVersionUID = -4836204119504454820L;

    /** 登录名 */
    private String username;

    /** 登录密码 */
    @JsonIgnore
    private String password;

    /**
     * 电话
     */
    private String phone;

    /** 邮箱 */
    private String mail;

    /**
     * TODO spring.jackson.date-format 好像对 LocalDateTime 类不起作用
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime time = LocalDateTime.now();

    private Date date = new Date();

}
