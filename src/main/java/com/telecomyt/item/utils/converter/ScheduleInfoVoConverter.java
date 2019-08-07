package com.telecomyt.item.utils.converter;

import com.telecomyt.item.dto.ScheduleInfoVo;
import com.telecomyt.item.entity.ScheduleGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * 日程实体转化
 */
@Mapper
public interface ScheduleInfoVoConverter {

    ScheduleInfoVoConverter INSTANCE = Mappers.getMapper(ScheduleInfoVoConverter.class);

    @Mappings({
            @Mapping(source = "id", target = "groupId")
    })
    ScheduleInfoVo scheduleGrouToVo (ScheduleGroup scheduleGroup);

}
