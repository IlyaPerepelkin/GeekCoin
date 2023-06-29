package ru.geekstar.ThreadJobs;

import ru.geekstar.Card.IBonusCard;

public class PayByCardBonusRunnable extends PayByCardRunnable {

    private IBonusCard bonusCard;

    private float sumPay;

    private int bonusesPay;

    private String buyProductOrService;

    private String pinCode;


    public PayByCardBonusRunnable(IBonusCard bonusCard, float sumPay, int bonusesPay, String buyProductOrService, String pinCode) {
        this.bonusCard = bonusCard;
        this.sumPay = sumPay;
        this.bonusesPay = bonusesPay;
        this.buyProductOrService = buyProductOrService;
        this.pinCode = pinCode;
    }

    @Override
    public void run() {
        bonusCard.payByCardBonuses(sumPay, bonusesPay, buyProductOrService, pinCode);
    }

}
