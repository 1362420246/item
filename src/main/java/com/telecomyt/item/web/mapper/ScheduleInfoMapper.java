package com.telecomyt.item.web.mapper;

import com.telecomyt.item.dto.ScheduleInfoDTO;
import com.telecomyt.item.dto.ScheduleListQuery;
import com.telecomyt.item.entity.ScheduleInfo;
import com.telecomyt.item.entity.ScheduleInfoDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    /**
     * 批量插入
     */
    int insertList(ScheduleInfoDO scheduleInfoDO);

    /**
     * 查询不重复日程
     */
    List<ScheduleInfoDTO> queryScheduleListByNoRepeat(ScheduleListQuery scheduleListQuery);

    /**
     * 查询重复日程
     */
    List<ScheduleInfoDTO> queryScheduleListByRepeat(ScheduleListQuery scheduleListQuery);

    /**
     * 查询关联人
     * @param groupId 组id
     */
    List<String> queryAffiliatedCardids(@Param("groupId") Integer groupId);

    /**
     * 删除日程
     * @param groupId 组id
     * @param cardid 身份证号
     */
    int deleteByGroupIdAndCardid(@Param("groupId")Integer groupId,@Param("cardid") String cardid);
}