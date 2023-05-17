package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.Transaction.DepositingTransaction;

public final class TinkoffBlackMir extends CardMir implements ICashbackCard {

    public static int count;

    public TinkoffBlackMir(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
        count++;
    }

    @Override
    public void accumulateCashback(float sumPay) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        float cashback = sumPay * cardHolder.getPercentCashbackOfSumPay();
        cardHolder.setCashback(cardHolder.getCashback() + cashback);
    }

    @Override
    public void depositingCashback2Card() {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        // инициализировать транзакцию пополнения
        DepositingTransaction depositingTransaction = new DepositingTransaction(this, "Зачисление кэшбэка", cardHolder.getCashback(), getPayCardAccount().getCurrencySymbol());

        boolean topUpStatus = getPayCardAccount().topUp(cardHolder.getCashback());
        if (topUpStatus) {
            depositingTransaction.setStatusOperation("Зачисление кэшбэка прошло успешно");
        } else depositingTransaction.setStatusOperation("Зачисление кэшбэка не прошло");

        // внести в транзакцию баланс карты после пополнения
        depositingTransaction.setBalance(getPayCardAccount().getBalance());

        // добавить и привязать транзакцию пополнения к счету карты зачисления
        getPayCardAccount().getDepositingTransactions().add(depositingTransaction);
    }

    @Override
    public void payByCard(float sumPay, String buyProductOrService, String pinCode) {
        // вызовем родительскую версию кода
        super.payByCard(sumPay, buyProductOrService, pinCode);

        // и дополним метод уникальным поведением: начислим кэшбэк
        accumulateCashback(sumPay);

    }

}
