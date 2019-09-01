package com.qbk.web.service;

import com.qbk.entity.User;
import com.qbk.result.BaseResult;

public interface UserService {
    BaseResult<String> add(User user);
}
