package com.liiwe.moneybook.mysql;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.liiwe.moneybook.base.bean.entity.MoneyBookRecord;
import com.liiwe.moneybook.mapper.MoneyBookMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author wfli
 * @since 2024/9/25 18:39
 */
@SpringBootTest
public class MysqlTest {

    @Autowired
    private MoneyBookMapper moneyBookMapper;

    @Test
    public void mysqlTest(){
        MoneyBookRecord moneyBookRecord = new MoneyBookRecord();
        moneyBookRecord.setId(IdUtil.getSnowflakeNextId());
        moneyBookRecord.setDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
        moneyBookRecord.setAmount("9.98");
        moneyBookRecord.setUsername("liwenfei");
        moneyBookRecord.setCreateTime(DateUtil.date());
        moneyBookMapper.insert(moneyBookRecord);
    }
}
