package com.qbk.web.service.impl;

import com.qbk.entity.Role;
import com.qbk.entity.User;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.web.mapper.UserMapper;
import com.qbk.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 添加用户
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public BaseResult<String> add(User user) {
        User userByName = userMapper.getUserByName(user.getLoginName());
        if(userByName != null){
            log.debug("登录名已存在:{}",user.getLoginName());
            return BaseResultGenerator.error("登录名已存在");
        }
        int add = userMapper.addUser(user);
        if(add == 0){
            log.debug("添加用户失败:{}",user.getLoginName());
            return BaseResultGenerator.error("添加用户失败");
        }
        List<Role> roleList = user.getRoleList();
        List<Integer> roleIds = roleList.stream().map(Role::getId).collect(Collectors.toList());
        userMapper.addRole(user.getId(),roleIds);
        log.debug("添加用户成功:{}",user.getLoginName());
        return BaseResultGenerator.success("添加用户成功");
    }
}
