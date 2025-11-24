package com.liiwe.moneybook.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liiwe.moneybook.base.bean.entity.Diary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lwf14
 * @since 2025/11/24 10:06
 */
@Mapper
public interface DiaryMapper extends BaseMapper<Diary> {

    void batchInsertDiary(@Param("list") List<Diary> diaryList);
}
