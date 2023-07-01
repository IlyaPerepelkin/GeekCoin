package ru.geekstar.ThreadJobs;

import ru.geekstar.Card.Card;

public class PayByCardRunnable extends Operations implements Runnable {

    private float sumPay;

    private String buyProductOrService;

    private String country;

    private String pinCode;

    private Card card;


    public PayByCardRunnable(Card card, float sumPay, String buyProductOrService, String pinCode) {
        this.card = card;
        this.sumPay = sumPay;
        this.buyProductOrService = buyProductOrService;
        this.pinCode = pinCode;
    }

    public PayByCardRunnable(Card card, float sumPay, String buyProductOrService, String country, String pinCode) {
        this(card, sumPay, buyProductOrService, pinCode);
        this.country = country;
    }

    public PayByCardRunnable() {
    }

    @Override
    public void run() {
        if (country == null) card.payByCard(sumPay, buyProductOrService, pinCode);
        else card.payByCard(sumPay, buyProductOrService, country, pinCode);
    }

}
