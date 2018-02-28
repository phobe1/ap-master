package com.you.ap.service.wallet.imp;

import com.you.ap.dao.money.ITradeDAO;
import com.you.ap.dao.money.IWalletDAO;
import com.you.ap.domain.enums.user.UserTypeEnum;
import com.you.ap.domain.schema.WalletDO;
import com.you.ap.domain.vo.ApiResponse;
import com.you.ap.service.wallet.IWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
public class WalletServiceImp implements IWalletService {


	@Autowired private IWalletDAO walletDAO;
	@Autowired private ITradeDAO tradeDAO;

	private Logger logger = LoggerFactory.getLogger(WalletServiceImp.class);

	@Override
	public ApiResponse getWallet(int userId, int type) {
		System.out.println(userId+" "+type);
		WalletDO walletDO=walletDAO.getUserWallet(userId, type);
		if(walletDO!=null){
			walletDO.setMoney(walletDO.getCoin()*0.1f);
			return ApiResponse.buildSuccess(walletDO);
		}else {
			int coin=0;
			if (type==UserTypeEnum.Student.key){
				coin=1000;
			}
			walletDAO.initwallet(userId,type,coin);
			walletDO=walletDAO.getUserWallet(userId, type);
			return ApiResponse.buildSuccess(walletDO);
		}
	}


	@Override
	public double getMoney(int studentId,int type) {
		double money = 0;
		WalletDO walletDO=walletDAO.getUserWallet(studentId, type);
		if(walletDO!=null){
			money=walletDO.getMoney();
		}else{
			initwallet(studentId,type);
		}

		return money;

	}




	@Override
	public int getCoin(int studentId,int type) {
		int coin = 0;
		WalletDO walletDO=walletDAO.getUserWallet(studentId, type);
		if(walletDO!=null){
			coin=walletDO.getCoin();
		}else{
			initwallet(studentId,type);
		}
		return coin;

	}


	//用户充值
	@Override
	public ApiResponse addMoney(int userId,int type, double money) {
		try{
			double mymoney =getMoney(userId,type);
			if(walletDAO.getUserWallet(userId,type)==null){
				return ApiResponse.buildFailure("钱包未开通");
			}
			walletDAO.updateMoney(mymoney+money,userId,type);
		}catch(Exception e){
			throw e;
			//return ApiResponse.buildFailure("支付失败");
		}
		return ApiResponse.buildSuccess("成功充值"+money+"元");

	}


	@Override
	public ApiResponse addCoin(int userId, int type,int coin) {
		try{
			int mycoin =getCoin(userId,type);
			if(walletDAO.getUserWallet(userId,type)==null){
				return ApiResponse.buildFailure("钱包未开通");
			}
			walletDAO.updateCoin(mycoin+coin,userId,type);
		}catch(Exception e){
			throw e;
			//return ApiResponse.buildFailure("支付失败");
		}
		logger.info("成功充值"+coin+"wooyo币");
		return ApiResponse.buildSuccess("成功充值"+coin+"wooyo币");
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public ApiResponse transMoney(int studentId, int teacherId, double money) {
		try{
		double studentmoney =getMoney(studentId,UserTypeEnum.Student.getKey());
		double teachermoney=getMoney(teacherId,UserTypeEnum.Teacher.getKey());
//		if(studentmoney<money){
//
//			return ApiResponse.buildFailure("余额不足，支付失败");
//		}
		if(walletDAO.getUserWallet(teacherId,UserTypeEnum.Teacher.getKey())==null){
			return ApiResponse.buildFailure("老师钱包未开通");
		}
		walletDAO.updateMoney(teachermoney+money,teacherId,UserTypeEnum.Teacher.getKey());
		walletDAO.updateMoney(studentmoney-money,studentId,UserTypeEnum.Student.getKey());
			tradeDAO.addDealOrder(UUID.randomUUID().toString().replace("-",""),studentId,teacherId,"约课",money,0);
		}catch(Exception e){
			return ApiResponse.buildFailure("支付失败");
		}
		return ApiResponse.buildSuccess("成功支付"+money+"元");

	}



	//用户消费
	@Override
	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	public void transCoin(int studentId, int teacherId, int coin) {
		try{
			int studentcoin =getCoin(studentId,UserTypeEnum.Student.getKey());
			int teachercoin=getCoin(teacherId,UserTypeEnum.Teacher.getKey());
			if(walletDAO.getUserWallet(teacherId,UserTypeEnum.Teacher.getKey())==null){
				initwallet(teacherId,UserTypeEnum.Teacher.key);
			}
			walletDAO.updateCoin(teachercoin+Math.round(Float.valueOf(coin)*0.77f),teacherId,UserTypeEnum.Teacher.getKey());
			walletDAO.updateCoin(studentcoin-coin,studentId,UserTypeEnum.Student.getKey());
			tradeDAO.addDealOrder(UUID.randomUUID().toString().replace("-",""),studentId,teacherId,"视频授课",0,coin);
		}catch(Exception e){
			logger.error("transCoin",e);
			//return ApiResponse.buildFailure("支付失败");
		}
		logger.info("{} 成功支付"+coin+"wooyo币 to {}",studentId,teacherId);
	}

	@Override
	public ApiResponse initwallet(int userId, int type) {
		int coin=0;
		if(type==UserTypeEnum.Student.key){
			coin=1000;
		}
		return ApiResponse.buildSuccess(walletDAO.initwallet(userId,type,coin));

	}

	@Override
	public ApiResponse generateDealOrder(int studentId, int teacherId, String note, double money, int coin) {
		return null;
	}


}
