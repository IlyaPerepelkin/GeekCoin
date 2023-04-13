package Card;

import Account.SavingsAccount;
import ClientProfile.SberPhysicalPersonProfile;
import Transaction.PayBonusTransaction;

import java.time.LocalDateTime;

public class SberVisaGold extends CardVisa implements IBonusCard {

    @Override
    public void accumulateBonuses(float sumPay) {
        SberPhysicalPersonProfile cardHolder = (SberPhysicalPersonProfile) getCardHolder();
        int bonuses = Math.round((sumPay / 100) * cardHolder.getPercentBonusOfSumPay());
        cardHolder.setBonuses(cardHolder.getBonuses() + bonuses);
    }

    @Override
    public void payByCardBonuses(float sumPay, int bonusesPay, String buyProductOrService, String pinCode) {

        SberPhysicalPersonProfile cardHolder = (SberPhysicalPersonProfile) getCardHolder();
        int bonus = cardHolder.getBonuses();
        int sumPay99 = (int) (sumPay * 0.99); // максимально возможная сумма оплаты бонусами
        int bonusesToPay = Math.min(bonusesPay, sumPay99); // определяем сколько бонусов можно использовать
        if (bonusesToPay < 0) {
            bonusesToPay = 0; // бонусы не могут быть отрицательными
        }
        if (bonusesToPay > bonus) {
            bonusesToPay = bonus; // нельзя использовать больше бонусов, чем есть на карте
        }
        float sumToPayWithCard = sumPay - (float) bonusesToPay; // определяем остаток для оплаты картой
        cardHolder.setBonuses(bonus - bonusesToPay); // уменьшаем количество бонусов на карте
        payByCard(sumToPayWithCard, buyProductOrService, pinCode); // оплачиваем остаток картой

        PayBonusTransaction payBonusTransaction = new PayBonusTransaction();
        payBonusTransaction.setFromCard(this);
        payBonusTransaction.setLocalDateTime(LocalDateTime.now());
        payBonusTransaction.setTypeOperation("Оплата бонусами" + " " + bonusesToPay + " " + "бонусов");
        payBonusTransaction.setBuyProductOrService(buyProductOrService);
        payBonusTransaction.setStatusOperation("Оплата бонусами прошла успешно");
        payBonusTransaction.setPayBonuses(bonusesToPay);
        payBonusTransaction.setBalanceBonuses(cardHolder.getBonuses());

        payBonusTransaction.setBalance(getPayCardAccount().getBalance());
        getPayCardAccount().addPayTransaction(payBonusTransaction);
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
    public void transferCard2Account(SavingsAccount toAccount, float sumTransfer) {
        // вызовем родительскую версию кода
        super.transferCard2Account(toAccount, sumTransfer);

        // и дополним метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
        ((SberPhysicalPersonProfile) getCardHolder()).updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toAccount, sumTransfer);
    }
}
