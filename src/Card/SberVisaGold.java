package Card;

import Account.SberSavingsAccount;

public class SberVisaGold extends CardVisa {

    @Override
    public void payByCard(float sumPay, String buyProductOrService) {
        // вызовем родительскую верию кода
        super.payByCard(sumPay, buyProductOrService);

        // и дополнимим метод уникальным поведением: начислим сбербонусы, которые присуще только картам Сбера

    }

    @Override
    public void transferCard2Card(SberVisaGold toCard, float sumTransfer) {
        // вызовем родительскую верию кода
        super.transferCard2Card(toCard, sumTransfer);

        // и дополнимим метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
        getCardHolder().updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toCard, sumTransfer);
    }

    @Override
    public void transferCard2Account(SberSavingsAccount toAccount, float sumTransfer) {
        // вызовем родительскую верию кода
        super.transferCard2Account(toAccount, sumTransfer);

        // и дополнимим метод уникальным поведением:
        // прибавим сумму перевода к общей сумме всех переводов клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
        getCardHolder().updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(toAccount, sumTransfer);
    }
}
