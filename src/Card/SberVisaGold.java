package Card;

import Account.SberSavingsAccount;

public class SberVisaGold extends CardVisa {

    @Override
    public void payByCard(float sumPay, String buyProductOrService, String pinCode) {
        // вызовем родительскую версию кода
        super.payByCard(sumPay, buyProductOrService, pinCode);

        // и дополним метод уникальным поведением: начислим сбербонусы, которые присуще только картам Сбера

    }

    @Override
    public void transferCard2Card(Card toCard, float sumTransfer) {
        // вызовем родительскую версию кода
        super.transferCard2Card(toCard, sumTransfer);

        // и дополним метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
        getCardHolder().updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toCard, sumTransfer);
    }

    @Override
    public void transferCard2Account(SberSavingsAccount toAccount, float sumTransfer) {
        // вызовем родительскую версию кода
        super.transferCard2Account(toAccount, sumTransfer);

        // и дополним метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
        getCardHolder().updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toAccount, sumTransfer);
    }
}
