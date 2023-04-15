package Bank;


import Account.Account;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import ClientProfile.TinkoffPhysicalPersonProfile;
import Account.TinkoffPayCardAccount;

import java.util.ArrayList;

public class Tinkoff extends Bank implements IBankServicePhysicalPerson {

    String currency;

    String currencyExchangeRate;

    PhysicalPersonProfile clientProfile;

    public PhysicalPersonProfile registerPhysicalPersonProfile(ArrayList<PhysicalPersonProfile> physicalPerson) {
        // создать профиль клиента
        TinkoffPhysicalPersonProfile tinkoffPhysicalPersonProfile = new TinkoffPhysicalPersonProfile();
        tinkoffPhysicalPersonProfile.setBank(this);
        tinkoffPhysicalPersonProfile.setPhysicalPerson(this.clientProfile.getPhysicalPerson());

        tinkoffPhysicalPersonProfile.setPercentBonusOfSumPay(0.5f);

        // установить лимиты
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInRUB(2000000.00f);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInUSD(60000.00f);
        tinkoffPhysicalPersonProfile.setLimitPaymentsTransfersDayInEUR(3500.00f);
        tinkoffPhysicalPersonProfile.setLimitTransfersToClientTinkoffWithoutCommissionMonthInRUB(100000.00f);

        // установить проценты комиссий
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForPayHousingCommunalServices(1.8f);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInRUB(1.1f);
        tinkoffPhysicalPersonProfile.setPercentOfCommissionForTransferInUsdOrOtherCurrency(1.20f);

        // установить лимиты на суммы комиссий
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInRUB(5000.00f);
        tinkoffPhysicalPersonProfile.setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(300.00f);

        // и привязать профиль клиента к банку
        addClientProfile(tinkoffPhysicalPersonProfile);

        return tinkoffPhysicalPersonProfile;
    }

    public Card openCard(ArrayList<PhysicalPersonProfile> physicalPersonProfile, Card card, String currencyCode, String pinCode) {
        // установить свойства карты
        card.setBank(this);
        card.setNumberCard(generateNumberCard());
        card.setCardHolder(card.getCardHolder());
        card.setPinCode(pinCode);

        // открыть платежный счет
        TinkoffPayCardAccount payCardAccount = (TinkoffPayCardAccount) openAccount(physicalPersonProfile, new TinkoffPayCardAccount(), currencyCode);

        // привязать карту к платежному счету
        payCardAccount.getCards().add(card);

        // привязать платежный счет к карте
        card.setPayCardAccount(payCardAccount);
        card.setStatusCard("Активна");

        // привязать карту к профилю клиента
        physicalPersonProfile.add(card.getCardHolder());

        return card;
    }

    public Account openAccount(ArrayList<PhysicalPersonProfile> physicalPersonProfile, Account account, String currencyCode) {
        // установить свойства платежного счета
        account.setBank(this);
        account.setNumberAccount(generateNumberAccount());
        account.setAccountHolder(account.getAccountHolder());
        account.setCurrencyCode(currencyCode);
        account.setCurrencySymbol(currencyCode);

        // привязать платежный счет к профилю клиента
        physicalPersonProfile.add(account.getAccountHolder());

        return account;
    }

    public float getCommissionOfTransferToClientBank() {
        return 0;
    }

    public float getExchangeRateBank() {
        // TODO: Запрос к API банка
        float exchangeRateBank = 0;
        // курс доллара к рублю
        if (currency != null && currency.equals("USD") && currencyExchangeRate.equals("RUB")) exchangeRateBank = 81.67f;
        // курс евро к рублю
        if (currency != null && currency.equals("EUR") && currencyExchangeRate.equals("RUB")) exchangeRateBank = 90.12f;
        return exchangeRateBank;
    }

}
