package com.qbk.web.service.impl;

import com.qbk.web.mapper.TestMapper;
import com.qbk.web.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试
 */
@Service
@Slf4j
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper ;

    /**
     * 测试连接
     *
     * 关于select是否要添加事务：
     * 1.有事务时候mybatis会用threadload 缓存 SqlSession 和 Connection ，保证同一个事务同一个连接、同一个session。
     * 2.无事务时候，会执行完一个mapper 直接关闭 Connection 和 SqlSession 。
        （如果配置数据库连接池，Connection 会归还到连接池里，下一个mapper方法执行还可以能是这个Connection ，
            一般情况测试不出Connection 不同，需要在高并发的情况下才能测试出不同的Connection ）
     * 3.mysql的一级缓存是在同一个SqlSession的基础上，所以无事务一级缓存也没有意义。
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testConnection() {
        log.debug("测试是否是同一个连接，测试是否走一级缓存，测试事务的影响：请在高并发下测试---------");
        Integer threadId = testMapper.getPseudoThreadId();
        Integer threadId1 = testMapper.getPseudoThreadIdByWhere();
        Integer threadId2 = testMapper.getPseudoThreadId();
        log.debug("当前线程id：{}-{}-{}",threadId ,threadId1,threadId2);
    }
}
