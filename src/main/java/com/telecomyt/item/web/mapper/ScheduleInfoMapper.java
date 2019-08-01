package com.telecomyt.item.web.mapper;

import com.telecomyt.item.entity.ScheduleInfo;

public interface ScheduleInfoMapper {
    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int deleteByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int insert(ScheduleInfo record);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int insertSelective(ScheduleInfo record);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    ScheduleInfo selectByPrimaryKey(Integer id);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int updateByPrimaryKeySelective(ScheduleInfo record);

    /**
     *
     * @mbg.generated Thu Aug 01 15:12:17 CST 2019
     */
    int updateByPrimaryKey(ScheduleInfo record);
}