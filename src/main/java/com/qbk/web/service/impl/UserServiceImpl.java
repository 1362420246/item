package com.qbk.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qbk.entity.AuthorizationUser;
import com.qbk.entity.Role;
import com.qbk.entity.User;
import com.qbk.entity.converter.UserDtoConverterMapper;
import com.qbk.entity.dto.UserDTO;
import com.qbk.entity.query.UserQuery;
import com.qbk.result.BaseResult;
import com.qbk.result.BaseResultGenerator;
import com.qbk.web.mapper.UserMapper;
import com.qbk.web.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
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

    /**
     * 查询用户列表
     *
     * 关于select是否要添加事务：
     * 1.有事务时候mybatis会用threadload 缓存 SqlSession 和 Connection ，保证同一个事务同一个连接、同一个session。
     * 2.无事务时候，会执行完一个mapper 直接关闭 Connection 和 SqlSession 。
        （如果配置数据库连接池，Connection 会归还到连接池里，下一个mapper方法执行还可以能是这个Connection ，
         一般情况测试不出Connection 不同，需要在高并发的情况下才能测试出不同的Connection ）
     * 3.mysql的一级缓存是在同一个SqlSession的基础上，所以无事务一级缓存也没有意义。
     * 4.mysql默认事务RR下select是快照读，无加锁，但如果是序列化级别select是当前读，加共享锁
     * 5.如果只有一条mapper查询，不用添加事务，因为innodb默认开启autocommit，每一条sql都会在数据库层添加事务，
         而且也不需要关心是否同一个连接和一级缓存。
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public List<UserDTO> selectList(UserQuery query) {
        //排序规则
        String orderBy = "id desc";
        //分页插件
        PageHelper.startPage(query.getPageNum(), query.getPageSize(), orderBy);
        //查询
        List<User> users = userMapper.selectList(query);
        List<UserDTO> userDTOS = UserDtoConverterMapper.INSTANCE.converter(users);

        //获取分页参数 :总数 总页数 页码 页大小
        PageInfo<User> userPageInfo = new PageInfo<>(users);
        final long total = userPageInfo.getTotal();
        final int pages = userPageInfo.getPages();
        final int pageNum = userPageInfo.getPageNum();
        final int pageSize = userPageInfo.getPageSize();
        System.out.println(total);
        System.out.println(pages);
        System.out.println(pageNum);
        System.out.println(pageSize);

        return userDTOS;
    }
}
