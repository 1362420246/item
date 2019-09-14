package com.qbk.entity.converter;

import com.qbk.entity.User;
import com.qbk.entity.dto.UserDTO;
import com.qbk.entity.param.TestRequest;
import com.qbk.entity.param.TestResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 实体对象转换器
 */
@Mapper
public interface UserDtoConverterMapper {

    UserDtoConverterMapper INSTANCE = Mappers.getMapper(UserDtoConverterMapper.class);

    /**
     * 单个对象 和 对多个对象转换
     */
    @Mappings({
    })
    UserDTO converter(User user);
    List<UserDTO> converter(List<User> list);
}
