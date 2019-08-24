package com.qbk.log.mapper;

import com.qbk.log.entity.SysLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * 日志mapper
 */
@Mapper
public interface LogMapper {

    @Insert("insert into tb_log (`type`,title,create_user,remote_addr," +
            "request_uri,params,cost_time,return_msg,user_agent,`exception`) " +
            "VALUES (#{type}, #{title},#{createUser},#{remoteAddr}," +
            "#{requestUri}, #{params},#{time},#{returnMsg}, #{userAgent},#{exception})")
    int add(SysLog sysLog);

}
