package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.TinkoffPayCardAccount;
import ru.geekstar.Bank.Tinkoff;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.Currency;
import ru.geekstar.Transaction.PayMileTransaction;

import java.util.ArrayList;

public final class TinkoffAirlinesMir extends CardMir implements IMulticurrencyCard, IAirlinesCard {

    public static int countCards;

    ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();


    @Override
    public ArrayList<PayCardAccount> getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    @Override
    public void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;
    }

    public TinkoffAirlinesMir(PhysicalPersonProfile cardHolder, PayCardAccount payCardAccount, String pinCode) {
        super(cardHolder, payCardAccount, pinCode);
        addAccount(Currency.EUR.toString());
        countCards++;
    }

    @Override
    public int getMiles() {
        return ((TinkoffPhysicalPersonProfile) getCardHolder()).getMiles();
    }

    @Override
    public void payByCard(float sumPay, String buyProductOrService, String pinCode) {
        super.payByCard(sumPay, buyProductOrService, pinCode);
        accumulateMiles(sumPay);
    }

    @Override
    public void accumulateMiles(float sumPay) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        int miles = 0;
        if (getPayCardAccount().getCurrencyCode().equals(Currency.RUB.toString())) miles = (int) (sumPay / cardHolder.getCostOf1MileRUB());
        if (getPayCardAccount().getCurrencyCode().equals(Currency.USD.toString())) miles = (int) (sumPay / cardHolder.getCostOf1MileUSD());
        if (getPayCardAccount().getCurrencyCode().equals(Currency.EUR.toString())) miles = (int) (sumPay / cardHolder.getCostOf1MileEUR());

        cardHolder.setMiles(cardHolder.getMiles() + miles);
    }

    @Override
    public void payByCardMiles(float sumPay, int milesPay, String buyProductOrService, String pinCode) {
        PayMileTransaction payMileTransaction = new PayMileTransaction(this, "Оплата милями", sumPay, milesPay, buyProductOrService);

        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        int balanceMiles = cardHolder.getMiles();
        if (balanceMiles >= milesPay) {
            if (milesPay > sumPay) milesPay = (int) sumPay;
            balanceMiles -= milesPay;
            cardHolder.setMiles(balanceMiles);
            sumPay -= milesPay;
            payMileTransaction.setStatusOperation("Оплата милями прошла успешно");
        } else payMileTransaction.setStatusOperation("Недостаточно миль");

        payMileTransaction.setPayMiles(milesPay);
        payMileTransaction.setBalanceMiles(balanceMiles);
        this.getPayCardAccount().getPayTransactions().add(payMileTransaction);

        payByCard(sumPay, buyProductOrService, pinCode);
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        PayCardAccount payCardAccount = (PayCardAccount) ((Tinkoff) this.getBank()).openAccount(this.getCardHolder(), TinkoffPayCardAccount.class, currencyCodeAccount);
        payCardAccount.getCards().add(this);
        getMulticurrencyAccounts().add(payCardAccount);
    }

}
