package Card;

import Account.PayCardAccount;
import Account.TinkoffPayCardAccount;
import Bank.Tinkoff;
import ClientProfile.TinkoffPhysicalPersonProfile;
import Transaction.PayTransaction;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TinkoffAirlinesMir extends CardMir implements IMulticurrencyCard, IAirlinesCard {

    private ArrayList<PayCardAccount> multicurrencyAccounts = new ArrayList<>();


    @Override
    public ArrayList<PayCardAccount> getMulticurrencyAccounts() {
        return multicurrencyAccounts;
    }

    @Override
    public void setMulticurrencyAccounts(ArrayList<PayCardAccount> multicurrencyAccounts) {
        this.multicurrencyAccounts = multicurrencyAccounts;

    }

    @Override
    public void addAccount(String currencyCodeAccount) {
        TinkoffPayCardAccount tinkoffPayCardAccount = (TinkoffPayCardAccount) (((Tinkoff) this.getBank()).openAccount(this.getCardHolder(), new TinkoffPayCardAccount(), currencyCodeAccount));
        tinkoffPayCardAccount.getCards().add(this);
        getMulticurrencyAccounts().add(tinkoffPayCardAccount);
    }

    @Override
    public void switchAccount(String currencyCodeAccount) {
        for (int i = 0; i < multicurrencyAccounts.size(); i++) {
            PayCardAccount payCardAccount = multicurrencyAccounts.get(i);
            if (payCardAccount.getCurrencyCode().equals(currencyCodeAccount)) {
                multicurrencyAccounts.remove(payCardAccount);
                multicurrencyAccounts.add(getPayCardAccount());
                setPayCardAccount(payCardAccount);
            }
        }
    }

    @Override
    public void accumulateMiles(float sumPay, String currency) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();
        float exchangeMilesRate = 0;
        if (currency.equals("RUB")) {
            exchangeMilesRate = 60;
        } else if (currency.equals("USD") || currency.equals("EUR")) {
            exchangeMilesRate = 1;
        }
        int miles = (int) (sumPay / exchangeMilesRate);
        cardHolder.setMiles(cardHolder.getMiles() + miles);
    }

    @Override
    public void payByCardMiles(int ticketPrice,String byProductOrService, String pinCode) {
        TinkoffPhysicalPersonProfile cardHolder = (TinkoffPhysicalPersonProfile) getCardHolder();

        PayTransaction payTransaction = new PayTransaction();
        payTransaction.setLocalDateTime(LocalDateTime.now());
        payTransaction.setFromCard(this);
        payTransaction.setSum(ticketPrice);
        payTransaction.setTypeOperation("Оплата милями ");
        payTransaction.setBuyProductOrService(byProductOrService);

        boolean topUpStatus = getPayCardAccount().topUp(cardHolder.getMiles());
        if (topUpStatus) {
            ((cardHolder.getMiles()) < ticketPrice).setStatusOperation("Недостаточно миль для оплаты билета");
        } else {
            cardHolder.setMiles((cardHolder.getMiles()) - ticketPrice).setStatusOperation("Билет оплачен милями");
        }


        payTransaction.setBalance(getPayCardAccount().getBalance());
        getPayCardAccount().getPayTransactions().add(payTransaction);
    }
}
