package Card;

import Account.PayCardAccount;
import ClientProfile.PhysicalPersonProfile;
import ClientProfile.TinkoffPhysicalPersonProfile;
import Transaction.DepositingTransaction;

import java.time.LocalDateTime;

public final class TinkoffBlackMir extends CardMir implements ICashbackCard {

    private static int count = 0;


    public static int getCount() {
        return count;
    }

    public static void setCount(int count) {
        TinkoffBlackMir.count = count;
    }

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
        DepositingTransaction depositingTransaction = new DepositingTransaction();
        depositingTransaction.setLocalDateTime(LocalDateTime.now());
        depositingTransaction.setToCard(this);
        depositingTransaction.setSum(cardHolder.getCashback());
        depositingTransaction.setCurrencySymbol(getPayCardAccount().getCurrencySymbol());
        depositingTransaction.setTypeOperation("Зачисление кэшбэка");

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
