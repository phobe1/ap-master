package com.you.ap.domain.vo.wallet;

public class WalletVO {

    private int coin;
    private double money;

    public WalletVO(){}

    public WalletVO(int coin ){
        this.coin=coin;
        this.money=coin*0.1;
    }


    public int getCoin() {
        return coin;
    }

    public void setCoin(int coin) {
        this.coin = coin;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
