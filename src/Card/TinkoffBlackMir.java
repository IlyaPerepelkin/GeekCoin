package Card;

import Transaction.DepositingTransaction;
import ClientProfile.TinkoffPhysicalPersonProfile;

import java.time.LocalDateTime;

public class TinkoffBlackMir extends CardMir implements ICashbackCard {

    @Override
    public void accumulateCashback(float sumPay) {
        float cashback = (float) (sumPay * getPercentCashbackOfSumPay());
        setCashback(cashback);
    }

    @Override
    public void depositingCashback2Card(Card toCard, float sumDepositing) {
        // инициализировать транзакцию пополнения
        DepositingTransaction depositingTransaction = new DepositingTransaction();
        depositingTransaction.setLocalDateTime(LocalDateTime.now());
        depositingTransaction.setToCard(this);
        depositingTransaction.setSum(sumDepositing);
        depositingTransaction.setCurrencySymbol(getPayCardAccount().getCurrencySymbol());
        depositingTransaction.setTypeOperation("Зачисление кэшбэка");

        boolean topUpStatus = getPayCardAccount().topUp(sumDepositing);
        if (topUpStatus) {

            // внести в транзакцию пополнения статус пополнения
            depositingTransaction.setStatusOperation("Пополнение прошло успешно");
            // внести в транзакцию пополнения баланс карты после пополнения
            depositingTransaction.setBalance(getPayCardAccount().getBalance() + getCashback());
            // добавить и привязать транзакцию пополнения к счету карты зачисления
            getPayCardAccount().getDepositingTransactions().add(depositingTransaction);

            // внести в транзакцию перевода статус перевода
            depositingTransaction.setStatusOperation("Перевод прошел успешно");


            // TODO: и перевести комиссию на счет банка

        } else depositingTransaction.setStatusOperation("Перевод не прошел");

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
