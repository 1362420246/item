package com.telecomyt.item.web.mapper;

import com.telecomyt.item.entity.ScheduleLog;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleLogMapper {
    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int insert(ScheduleLog record);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int insertSelective(ScheduleLog record);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    ScheduleLog selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int updateByPrimaryKeySelective(ScheduleLog record);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int updateByPrimaryKey(ScheduleLog record);

    /**
     * 根据组id查询
     * @param groupId 组id
     */
    List<ScheduleLog> queryByGroupId(@Param("groupId") Integer groupId);
}