package com.qbk.web.mapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 测试
 */
@Mapper
public interface TestMapper {

    @Select("SELECT table_name FROM information_schema.TABLES WHERE table_name = #{name}")
    String test(@Param("name")String name);

    /**
     * 获取线程id
     */
    @Select("select @@pseudo_thread_id")
    Integer getPseudoThreadId();

}
