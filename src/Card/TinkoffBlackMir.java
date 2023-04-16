package Card;

import Transaction.DepositingTransaction;

import java.time.LocalDateTime;

public class TinkoffBlackMir extends CardMir implements ICashbackCard {

    float cashback;

    public float getCashback() {
        return cashback;
    }

    public void setCashback(float cashback) {
        this.cashback = cashback;
    }

    @Override
    public void accumulateCashback(float sumPay) {
        float cashback = (float) (sumPay * 0.05); // вычисляем 5% кэшбэка от суммы покупки
        this.setCashback(this.getCashback() + cashback);
    }

    @Override
    public void depositingCashback2Card() {
        // инициализировать транзакцию пополнения
        DepositingTransaction depositingTransaction = new DepositingTransaction();
        depositingTransaction.setLocalDateTime(LocalDateTime.now());
        depositingTransaction.setToCard(this);
        depositingTransaction.setSum(cashback);
        depositingTransaction.setTypeOperation("Зачисление кэшбэка");

        // внести в транзакцию баланс карты после пополнения
        depositingTransaction.setBalance(getPayCardAccount().getBalance() + cashback);

        // добавить и привязать транзакцию пополнения к счету карты зачисления
        getPayCardAccount().addDepositingTransaction(depositingTransaction);
    }

}
