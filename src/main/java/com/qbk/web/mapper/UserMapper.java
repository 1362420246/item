package com.qbk.web.mapper;

import com.qbk.entity.Role;
import com.qbk.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface UserMapper {

    /**
     * 获得该用户角色
     */
    List<Role> getRoleByUserName(String username);

    /**
     * 获得该用户的权限
     */
    List<String> getMenuNameByRoleIds(@Param("roleIds") Set<Integer> roleIds);

    /**
     * 获取用户信息
     */
    User getUserByName(String username);

    /**
     * 添加用户
     */
    int addUser(User user);

    /**
     * 给指定用户添加角色
     */
    int addRole(@Param("userId") Integer userId,@Param("roleIds") List<Integer> roleIds);
}
