package com.you.ap.service.wallet;

import com.you.ap.domain.schema.order.TradeDO;

/**
 * Created by liangjielin on 2017/12/28.
 */
public interface ITradeService {

    TradeDO selectByOrderNumber(String ordernumber);

    int updateByPrimaryKeySelective(TradeDO tradeDO);

    int addTradeOrder(TradeDO tradeDO);

}
