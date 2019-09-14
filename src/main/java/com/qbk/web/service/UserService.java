package com.qbk.web.service;

import com.qbk.entity.AuthorizationUser;
import com.qbk.entity.User;
import com.qbk.entity.dto.UserDTO;
import com.qbk.entity.query.UserQuery;
import com.qbk.result.BaseResult;

import java.util.List;

public interface UserService {

    BaseResult<String> add(User user);

    List<UserDTO> selectList(UserQuery userQuery);
}
