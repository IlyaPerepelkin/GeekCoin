package Card;

import Account.Account;
import Account.TinkoffPayCardAccount;
import Bank.Tinkoff;
import ClientProfile.TinkoffPhysicalPersonProfile;
import Transaction.PayMilesTransaction;

import java.time.LocalDateTime;

public class TinkoffAirlinesMir extends CardMir implements IMulticurrencyCard, IAirlinesCard {


    @Override
    public void setPayCardAccount(Account payCardAccount) {

    }

    @Override
    public Account getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    @Override
    public void setMulticurrencyAccounts(Account multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;

    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        TinkoffPayCardAccount tinkoffPayCardAccount = (TinkoffPayCardAccount) ((Tinkoff) this.getBank()).openAccount(this.getCardHolder(), new TinkoffPayCardAccount(), currencyCodeAccount);
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
    public void payByCardMiles(float sumPay, int milesPay, String byProductOrService, String pinCode) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        int miles = cardHolder.getMiles();

        PayMilesTransaction payMilesTransaction = new PayMilesTransaction();
        payMilesTransaction.setLocalDateTime(LocalDateTime.now());
        payMilesTransaction.setFromCard(this);
        payMilesTransaction.setSum(sumPay);
        payMilesTransaction.setTypeOperation("Оплата милями ");
        payMilesTransaction.setBuyProductOrService(byProductOrService);

        if (milesPay > sumPay) payMilesTransaction.setStatusOperation("Сумма оплаты милями больше, чем стоимость билета");

        if (miles >= milesPay) {
            sumPay = (sumPay - milesPay);
            payMilesTransaction.setStatusOperation("Билет оплачен милями");
        } else payMilesTransaction.setStatusOperation("Недостаточно миль для оплаты билета бонусов");

        payMilesTransaction.setBalance(getPayCardAccount().getBalance());
        getPayCardAccount().getPayTransactions().add(payMilesTransaction);

        payByCard(sumPay, byProductOrService, pinCode);
        cardHolder.setMiles(miles - milesPay);

    }

}
