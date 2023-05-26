package ru.geekstar.Card;

import ru.geekstar.Account.Account;
import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.SberPhysicalPersonProfile;
import ru.geekstar.Transaction.PayBonusTransaction;

public final class SberVisaGold extends CardVisa implements IBonusCard {

    public static int count;

    public SberVisaGold(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
        count++;
    }

    @Override
    public void accumulateBonuses(float sumPay) {
        SberPhysicalPersonProfile cardHolder = (SberPhysicalPersonProfile) getCardHolder();
        int bonuses = Math.round((sumPay / 100) * cardHolder.getPercentBonusOfSumPay());
        cardHolder.setBonuses(cardHolder.getBonuses() + bonuses);
    }

    @Override
    public void payByCardBonuses(float sumPay, int bonusesPay, String buyProductOrService, String pinCode) {
        PayBonusTransaction payBonusTransaction = new PayBonusTransaction(this, "Оплата бонусами", sumPay, getPayCardAccount().getCurrencySymbol());
        payBonusTransaction.setBuyProductOrService(buyProductOrService);

        SberPhysicalPersonProfile cardHolder = (SberPhysicalPersonProfile) getCardHolder();
        int bonuses = cardHolder.getBonuses();
        int sumPay99 = (int) (sumPay * 0.99); // максимально возможная сумма оплаты бонусами
        int payBonuses = Math.min(bonusesPay, sumPay99); // определяем сколько бонусов можно использовать
        if (payBonuses < 0) {
            payBonuses = 0; // бонусы не могут быть отрицательными
        }
        if (bonuses >= payBonuses) {
            sumPay -= (float) payBonuses; // определяем остаток для оплаты картой
            cardHolder.setBonuses(bonuses - payBonuses); // уменьшаем количество бонусов на карте
            payBonusTransaction.setStatusOperation("Оплата бонусами прошла успешно");
        } else payBonusTransaction.setStatusOperation("Недостаточно бонусов");

        payBonusTransaction.setPayBonuses(payBonuses);
        payBonusTransaction.setBalanceBonuses(cardHolder.getBonuses());


        payByCard(sumPay, buyProductOrService, pinCode); // оплачиваем остаток картой
    }

    @Override
    public void payByCard(float sumPay, String buyProductOrService, String pinCode) {
        // вызовем родительскую версию кода
        super.payByCard(sumPay, buyProductOrService, pinCode);

        // и дополним метод уникальным поведением: начислим сбербонусы, которые присуще только картам Сбера
        accumulateBonuses(sumPay);

    }

    @Override
    public void transferCard2Card(Card toCard, float sumTransfer) {
        // вызовем родительскую версию кода
        super.transferCard2Card(toCard, sumTransfer);

        // и дополним метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
        ((SberPhysicalPersonProfile) getCardHolder()).updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toCard, sumTransfer);
    }

    @Override
    public void transferCard2Account(Account toAccount, float sumTransfer) {
        // вызовем родительскую версию кода
        super.transferCard2Account(toAccount, sumTransfer);

        // и дополним метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
        ((SberPhysicalPersonProfile) getCardHolder()).updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toAccount, sumTransfer);
    }
}