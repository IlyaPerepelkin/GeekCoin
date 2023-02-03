package Bank;

import Account.SberPayCardAccount;
import Account.SberSavingsAccount;
import Card.SberVisaGold;
import ClientProfile.SberPhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

public class Sberbank extends Bank {

    // Зарегистрировать профиль физ лица
    public SberPhysicalPersonProfile registerClientProfile(PhysicalPerson physicalPerson) {
        // создать профиль клиента
        SberPhysicalPersonProfile sberPhysicalPersonProfile = new SberPhysicalPersonProfile();
        sberPhysicalPersonProfile.setBank(this);
        sberPhysicalPersonProfile.setPhysicalPerson(physicalPerson);

        // установить лимиты
        sberPhysicalPersonProfile.setLimitPaymentsTransfersDayInRUB(1000000.00f);
        sberPhysicalPersonProfile.setLimitPaymentsTransfersDayInUSD(50000.00f);
        sberPhysicalPersonProfile.setLimitPaymentsTransfersDayInEUR(3800.00f);
        sberPhysicalPersonProfile.setLimitTransfersToClientSberWithoutCommissionMonthInRUB(50000.00f);

        // установить проценты комиссий
        sberPhysicalPersonProfile.setPercentOfCommissionForPayHousingCommunalServices(2.0f);
        sberPhysicalPersonProfile.setPercentOfCommissionForTransferInRUB(1.0f);
        sberPhysicalPersonProfile.setPercentOfCommissionForTransferInUsdOrOtherCurrency(1.25f);

        // установить лимиты на суммы комиссий
        sberPhysicalPersonProfile.setLimitCommissionTransferInRUB(3000.00f);
        sberPhysicalPersonProfile.setLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(100.00f);

        // и привязать профиль клиента к банку
        addClientProfile(sberPhysicalPersonProfile);

        return sberPhysicalPersonProfile;
    }

    // Открыть карту
    public SberVisaGold openCard(SberPhysicalPersonProfile clientProfile, SberVisaGold card, String currencyCode) {
        // установить свойства карты
        card.setBank(this);
        card.setNumberCard(generateNumberCard());
        card.setCardHolder(clientProfile);

        // открыть платежный счет
        SberPayCardAccount payCardAccount = openAccount(clientProfile, new SberPayCardAccount(), currencyCode);

        // привязать карту к платежному счету
        payCardAccount.addCard(card);

        // привязать платежный счет к карте
        card.setPayCardAccount(payCardAccount);
        card.setStatusCard("Активна");

        // привязать карту к профилю клиента
        clientProfile.addCard(card);

        return card;
    }

    // Открыть платежный счет
    private SberPayCardAccount openAccount(SberPhysicalPersonProfile clientProfile, SberPayCardAccount account, String currencyCode) {
        // установить свойства платежнего счета
        account.setBank(this);
        account.setNumberAccount(generateNumberAccount());
        account.setAccountHolder(clientProfile);
        account.setCurrencyCode(currencyCode);
        account.setCurrencySymbol(currencyCode);

        // привязать платежный счет к профилю клиента
        clientProfile.addAccount(account);

        return account;
    }

    // Открыть сберегательный счет
    public SberSavingsAccount openAccount(SberPhysicalPersonProfile clientProfile, SberSavingsAccount account, String currencyCode) {
        // Установить свойства сберегательного счета
        account.setBank(this);
        account.setNumberAccount(generateNumberAccount());
        account.setAccountHolder(clientProfile);
        account.setCurrencyCode(currencyCode);
        account.setCurrencySymbol(currencyCode);

        // привязать сберегательный счет к профилю клиента
        clientProfile.addAccount(account);

        return account;
    }

    @Override
    // Предоставить обменный курс валют Сбера
    public float getExchangeRateBank(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API банка
        float exchangeRateBank = 0;
        // курс доллара к рублю
        if (currency.equals("USD") && currencyExchangeRate.equals("RUB")) exchangeRateBank = 60.48f;
        // курс евро к рублю
        if (currency.equals("EUR") && currencyExchangeRate.equals("RUB")) exchangeRateBank = 61.56f;
        return  exchangeRateBank;
    }

    @Override
    // Рассчитать комиссию за перевод  клиенту моего банка Сбер
    public float getCommissionOfTransferToClientBank(SberPhysicalPersonProfile clientProfile, float sum, String fromCurrencyCode) {
        // по умолчанию комиссия 0
        float commission = 0;
        // если сумма перевода в рублях
        if (fromCurrencyCode.equals("RUB")) {
            // и если превышен лимит по переводам клиентам Сбера в месяц, то рассчитываем комиссию за перевод
            boolean exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB = clientProfile.exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB(sum);
            if (exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB)
                commission = (sum / 100) * clientProfile.getPercentOfCommissionForTransferInRUB();
        }
        return commission;
    }
}
