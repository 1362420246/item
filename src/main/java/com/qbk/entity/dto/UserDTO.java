package com.qbk.entity.dto;

import com.qbk.entity.Role;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO（Data Transfer Object）：数据传输对象，Service 或 Manager 向外传输的对象。
 */
@Data
public class UserDTO implements Serializable{

    private static final long serialVersionUID = -4300742786560887452L;
    /**
     *   * 用户ID
     */
    private Integer id;

    /**
     *   * 登录名
     */
    private String loginName;

    /**
     *   * 昵称
     */
    private String nickName;

    /**
     *   * 是否锁定
     */
    private Boolean isLock;

    /**
     *   *
     */
    private LocalDateTime createDate;

    /**
     *   *
     */
    private LocalDateTime updateDate;

}
