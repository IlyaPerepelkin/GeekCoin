package ru.geekstar.ThreadJobs;

import ru.geekstar.Card.Card;
import ru.geekstar.Card.IAirlinesCard;

public class PayByCardMilesRunnable extends PayByCardRunnable {

    private IAirlinesCard airlinesCard;

    private float sumPay;

    private int milesPay;

    private String buyProductOrService;

    private String pinCode;


    public PayByCardMilesRunnable(IAirlinesCard airlinesCard, float sumPay, int milesPay, String buyProductOrService, String pinCode) {
        this.airlinesCard = airlinesCard;
        this.sumPay = sumPay;
        this.milesPay = milesPay;
        this.buyProductOrService = buyProductOrService;
        this.pinCode = pinCode;
    }

    @Override
    public void run() {
        airlinesCard.payByCardMiles(sumPay, milesPay, buyProductOrService, pinCode);
    }

}
