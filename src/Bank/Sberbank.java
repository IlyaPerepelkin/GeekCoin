package Bank;

import Account.Account;
import Account.SberPayCardAccount;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import ClientProfile.SberPhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

public class Sberbank extends Bank implements IBankServicePhysicalPerson {

    // Зарегистрировать профиль физ лица
    @Override
    public PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson) {
        // создать профиль клиента
        SberPhysicalPersonProfile sberPhysicalPersonProfile = new SberPhysicalPersonProfile();
        sberPhysicalPersonProfile.setBank(this);
        sberPhysicalPersonProfile.setPhysicalPerson(physicalPerson);

        sberPhysicalPersonProfile.setPercentBonusOfSumPay(0.5f);

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
    @Override
    public Card openCard(PhysicalPersonProfile physicalPersonProfile, Card  card, String currencyCode, String pinCode) {
        // установить свойства карты
        card.setBank(this);
        card.setNumberCard(generateNumberCard());
        card.setCardHolder(physicalPersonProfile);
        card.setPinCode(pinCode);

        // открыть платежный счет
        SberPayCardAccount payCardAccount = (SberPayCardAccount) openAccount(physicalPersonProfile, new SberPayCardAccount(), currencyCode);

        // привязать карту к платежному счету
        payCardAccount.addCard(card);

        // привязать платежный счет к карте
        card.setPayCardAccount(payCardAccount);
        card.setStatusCard("Активна");

        // привязать карту к профилю клиента
        physicalPersonProfile.addCard(card);

        return card;
    }

    // Открыть счет
    @Override
    public Account openAccount(PhysicalPersonProfile physicalPersonProfile, Account account, String currencyCode) {
        // установить свойства платежного счета
        account.setBank(this);
        account.setNumberAccount(generateNumberAccount());
        account.setAccountHolder(physicalPersonProfile);
        account.setCurrencyCode(currencyCode);
        account.setCurrencySymbol(currencyCode);

        // привязать платежный счет к профилю клиента
        physicalPersonProfile.addAccount(account);

        return account;
    }

    @Override
    // Предоставить обменный курс валют Сбера
    public float getExchangeRateBank(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API банка
        float exchangeRateBank = 0;
        // курс доллара к рублю
        if (currency.equals("USD") && currencyExchangeRate.equals("RUB")) exchangeRateBank = 60.30f;
        // курс евро к рублю
        if (currency.equals("EUR") && currencyExchangeRate.equals("RUB")) exchangeRateBank = 61.50f;
        return  exchangeRateBank;
    }

    @Override
    // Рассчитать комиссию за перевод клиенту моего банка Сбер
    public float getCommissionOfTransferToClientBank(PhysicalPersonProfile clientProfile, float sum, String fromCurrencyCode) {
        // по умолчанию комиссия 0
        float commission = 0;
        // если сумма перевода в рублях
        if (fromCurrencyCode.equals("RUB")) {
            // и если превышен лимит по переводам клиентам Сбера в месяц, то рассчитываем комиссию за перевод
            boolean exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB = ((SberPhysicalPersonProfile) clientProfile).exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB(sum);
            if (exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB)
                commission = (sum / 100) * clientProfile.getPercentOfCommissionForTransferInRUB();
        }
        return commission;
    }
}
