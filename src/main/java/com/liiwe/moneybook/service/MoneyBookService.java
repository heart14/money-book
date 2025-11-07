package com.liiwe.moneybook.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liiwe.moneybook.base.bean.domain.mb.*;
import com.liiwe.moneybook.base.bean.entity.MoneyBook;
import com.liiwe.moneybook.base.bean.model.MonthlyMoneyRecord;

import java.util.List;
import java.util.Map;

/**
 * @author wfli
 * @since 2025/6/11 17:10
 */
public interface MoneyBookService {

    void importMoneyBook(List<MoneyBook> list);

    Page<MoneyBook> getPageList(PageListReq pageListReq);

    TotalAmountResp getTotalAmount(TotalAmountReq totalAmountReq);

    List<MoneyBook> getBillList(BillListReq billListReq);

    List<Map<String, Object>> getStatisticData(StatisticDataReq statisticDataReq);

    List<Map<String, Object>> getCategoryStatistic(CategoryStatisticReq categoryStatisticReq);

    MonthlyDataResp getMonthlyData(StatisticDataReq statisticDataReq);

    void saveMoneyBook(MoneyBookReq moneyBookReq,boolean fromMobile);

    void editMoneyBook(MoneyBook moneyBook);
}
