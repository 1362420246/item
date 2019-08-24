package com.qbk.entity.converter;

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
public interface TestConverterMapper {

    TestConverterMapper INSTANCE = Mappers.getMapper(TestConverterMapper.class);

    /**
     * 单个对象 和 对多个对象转换
     */
    @Mappings({
            @Mapping(source = "tel", target = "phone")
    })
    TestResponse converter(TestRequest testRequest);
    List<TestResponse> converter(List<TestRequest> list);
}
