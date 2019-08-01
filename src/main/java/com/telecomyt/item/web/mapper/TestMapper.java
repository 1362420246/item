package com.telecomyt.item.web.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TestMapper {

    @Select("SELECT table_name FROM information_schema.TABLES WHERE table_name = #{name}")
    String test(@Param("name")String name);

}
