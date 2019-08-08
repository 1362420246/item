package com.telecomyt.item.web.service.impl;

import com.telecomyt.item.web.mapper.TaskMapper;
import com.telecomyt.item.web.service.TaskAsynService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskAsynServiceImpl implements TaskAsynService {

    @Autowired
    private TaskMapper taskMapper ;

    @Async
    @Override
    public void updateOverdue(List<Integer> groupIds) {
        groupIds.forEach(taskMapper::updateOverdue );
    }
}
