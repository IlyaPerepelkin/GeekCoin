package ru.geekstar.Card;

import ru.geekstar.Account.PayCardAccount;
import ru.geekstar.Account.TinkoffPayCardAccount;
import ru.geekstar.Bank.Tinkoff;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.ClientProfile.TinkoffPhysicalPersonProfile;
import ru.geekstar.Transaction.PayMilesTransaction;

import java.util.ArrayList;

public final class TinkoffAirlinesMir extends CardMir implements IMulticurrencyCard, IAirlinesCard {

    ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();

    public static int count;


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
        addAccount("EUR");
        count++;
    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        TinkoffPayCardAccount tinkoffPayCardAccount = (TinkoffPayCardAccount) ((Tinkoff) this.getBank()).openAccount(this.getCardHolder(), TinkoffPayCardAccount.class, currencyCodeAccount);
        tinkoffPayCardAccount.getCards().add(this);
        getMulticurrencyAccounts().add(tinkoffPayCardAccount);
    }

    @Override
    public void accumulateMiles(float sumPay) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        int miles = 0;
        if (getPayCardAccount().getCurrencyCode().equals("RUB")) miles = (int) (sumPay / cardHolder.getCostOfMileRUB());
        if (getPayCardAccount().getCurrencyCode().equals("USD")) miles = (int) (sumPay / cardHolder.getCostOfMileUSD());
        if (getPayCardAccount().getCurrencyCode().equals("EUR")) miles = (int) (sumPay / cardHolder.getCostOfMileEUR());

        cardHolder.setMiles(cardHolder.getMiles() + miles);
    }

    @Override
    public void payByCard(float sumPay, String buyProductOrService, String pinCode) {
        super.payByCard(sumPay, buyProductOrService, pinCode);
        accumulateMiles(sumPay);
    }

    @Override
    public void payByCardMiles(float sumPay, int milesPay, String buyProductOrService, String pinCode) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        int miles = cardHolder.getMiles();

        PayMilesTransaction payMilesTransaction = new PayMilesTransaction(this, "Оплата милями ", sumPay, getPayCardAccount().getCurrencySymbol());
        payMilesTransaction.setBuyProductOrService(buyProductOrService);

        if (milesPay > sumPay) payMilesTransaction.setStatusOperation("Сумма оплаты милями больше, чем стоимость билета");

        if (miles >= milesPay) {
            sumPay -= milesPay;
            payMilesTransaction.setStatusOperation("Билет оплачен милями");
        } else payMilesTransaction.setStatusOperation("Недостаточно миль для оплаты билета бонусов");

        payMilesTransaction.setBalance(getPayCardAccount().getBalance());
        getPayCardAccount().getPayTransactions().add(payMilesTransaction);

        payByCard(sumPay, buyProductOrService, pinCode);
        cardHolder.setMiles(miles - milesPay);

    }

}