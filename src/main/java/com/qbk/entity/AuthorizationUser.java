package com.qbk.entity;

import lombok.*;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Set;

/**
 *  用户实体 带角色名和权限名
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationUser extends User implements Serializable {
    private static final long serialVersionUID = -8602314906761746478L;

    /**
     * 角色名
     */
    private Set<String> roleNames ;
    /**
     * 权限名
     */
    private Set<String> menuNames ;

    /**
     * 拷贝父类属性
     */
    public static AuthorizationUser copyProperties(User user){
        AuthorizationUser authorizationUser = new AuthorizationUser();
        BeanUtils.copyProperties(user,authorizationUser );
        return authorizationUser;
    }
}
