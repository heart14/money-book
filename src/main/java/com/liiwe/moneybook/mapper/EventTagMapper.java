package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.entity.EventTag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/12/3 14:34
 */
@Mapper
public interface EventTagMapper extends BaseMapper<EventTag> {

    void batchInsertEventTag(@Param("list") List<EventTag> eventList);
}
