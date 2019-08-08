package com.telecomyt.item.web.service;

import java.util.List;

public interface TaskAsynService {

    /**
     * 修改逾期
     * @param groupIds 组id
     */
    void updateOverdue(List<Integer> groupIds);
}
