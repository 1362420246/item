package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.web.mapper.TestMapper;
import com.telecomyt.item.web.service.TestService;
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
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testConnection() {
//        for (int i = 0; i < 100 ; i++) {
            Integer threadId = testMapper.getPseudoThreadId();
            Integer threadId2 = testMapper.getPseudoThreadId();
            log.info("当前线程id：{} ---{}",threadId ,threadId2);
//        }
    }
}
