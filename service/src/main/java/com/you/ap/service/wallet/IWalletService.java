package com.you.ap.service.wallet;


import com.you.ap.domain.vo.ApiResponse;
import org.apache.ibatis.annotations.Param;

public interface IWalletService {

	ApiResponse getWallet(int userId,int type);

	int getCoin(int id,int type);

	double getMoney(int id,int type);

	//钱包充值
	ApiResponse addMoney(int userId, int type,double money);
	ApiResponse addCoin(int userId, int type,int coin);

	//学生给老师转账
	void transCoin(int studentId, int teacherId, int coin);
	ApiResponse transMoney(int studentId, int teacherId, double money);

	//初始化钱包账号
	ApiResponse initwallet(int userId,int type);

	ApiResponse generateDealOrder(int studentId, int teacherId,String note,double money,int coin);



}
