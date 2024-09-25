package com.liiwe.moneybook.mysql;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.liiwe.moneybook.base.bean.entity.DailyRecord;
import com.liiwe.moneybook.mapper.DailyRecordMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author wfli
 * @since 2024/9/25 18:39
 */
@SpringBootTest
public class MysqlTest {

    @Autowired
    private DailyRecordMapper dailyRecordMapper;

    @Test
    public void mysqlTest(){
        DailyRecord dailyRecord = new DailyRecord();
        dailyRecord.setId(IdUtil.getSnowflakeNextId());
        dailyRecord.setDate(DateUtil.date());
        dailyRecord.setAmount("9.98");
        dailyRecord.setUsername("liwenfei");
        dailyRecord.setCreateTime(DateUtil.date());
        dailyRecordMapper.insert(dailyRecord);
    }
}
