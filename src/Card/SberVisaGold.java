package Card;

import Account.SavingsAccount;
import ClientProfile.SberPhysicalPersonProfile;

public class SberVisaGold extends CardVisa implements IBonusCard {

    @Override
    public void accumulateBonuses(float sumPay) {
        SberPhysicalPersonProfile cardHolder = (SberPhysicalPersonProfile) getCardHolder();
        int bonuses = Math.round((sumPay / 100) * cardHolder.getPercentBonusOfSumPay());
        cardHolder.setBonuses(cardHolder.getBonuses() + bonuses);
    }

    @Override
    public void payByCardBonuses(float sumPay, int bonusesPay, String buyProductOrService) {
        // реализовать самому далее в заданиях
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
