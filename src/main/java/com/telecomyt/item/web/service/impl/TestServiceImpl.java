package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.web.mapper.TestMapper;
import com.telecomyt.item.web.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private TestMapper testMapper ;

    @Override
    public String test() {
        return testMapper.test("users");
    }
}
