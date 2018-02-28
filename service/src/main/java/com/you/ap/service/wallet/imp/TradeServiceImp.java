package com.you.ap.service.wallet.imp;

import com.you.ap.dao.money.ITradeDAO;
import com.you.ap.domain.schema.order.TradeDO;
import com.you.ap.service.wallet.ITradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImp implements ITradeService{

    @Autowired
    private ITradeDAO tradeDAO;

    @Override
    public TradeDO selectByOrderNumber(String ordernumber) {
        return tradeDAO.getTradeOrderByOrderNumber(ordernumber);
    }

    @Override
    public int updateByPrimaryKeySelective(TradeDO tradeDO) {
        return tradeDAO.updateByPrimaryKeySelective(tradeDO);
    }

    @Override
    public int addTradeOrder(TradeDO tradeDO) {
        return tradeDAO.addTradeOrder(tradeDO);
    }
}
