package com.qbk.entity.converter;

import com.qbk.entity.User;
import com.qbk.entity.dto.UserDTO;
import com.qbk.entity.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 实体对象转换器
 */
@Mapper
public interface UserDtoToUserVorMapper {

    UserDtoToUserVorMapper INSTANCE = Mappers.getMapper(UserDtoToUserVorMapper.class);

    /**
     * 单个对象 和 对多个对象转换
     */
    @Mappings({
            @Mapping(source = "loginName", target = "username")
    })
    UserVo converter(UserDTO user);
    List<UserVo> converter(List<UserDTO> list);
}
