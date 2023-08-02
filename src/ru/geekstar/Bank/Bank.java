package ru.geekstar.Bank;

import ru.geekstar.Account.Account;
import ru.geekstar.Card.Card;
import ru.geekstar.ClientProfile.ClientProfile;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.Country;
import ru.geekstar.Currency;

import java.util.ArrayList;

public abstract class Bank {
    private String bankName;

    private ArrayList<ClientProfile> clientProfiles = new ArrayList<>();


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public ArrayList<ClientProfile> getClientProfiles() {
        return clientProfiles;
    }

    public void setClientProfiles(ArrayList<ClientProfile> clientProfiles) {
        this.clientProfiles = clientProfiles;
    }


    public Bank(String bankName) {
        this.bankName = bankName;
    }

    // Сгенерировать номер карты 3546 0957 9843 7845
    public static String generateNumberCard() {
        byte lengthNumberCard = 20;
        StringBuffer numberCardBuffer = new StringBuffer();
        for (byte i = 1; i < lengthNumberCard; i++) {     // i = 1, чтобы не было пробела в начале, потому что при делении 0 на 5 будет 0
            if (i % 5 !=0) numberCardBuffer.append((byte) (Math.random() * 10));
            else numberCardBuffer.append(' ');
        }
        return numberCardBuffer.toString();
    }

    // Сгенерировать номер в формате 15738 538 9 4736 9745275
    public static String generateNumberAccount() {
        byte lengthNumberAccount = 25;
        StringBuffer numberAccountBuffer = new StringBuffer();
        for (byte i = 1; i < lengthNumberAccount; i++) {
            if (i == 6 || i == 10 || i == 12 || i == 17) numberAccountBuffer.append(" ");
            else numberAccountBuffer.append((byte) (Math.random() * 10));
        }
        return numberAccountBuffer.toString();
    }

    public static String generatePinCode() {
        byte lengthPinCode = 4;
        StringBuffer pinCodeBuffer = new StringBuffer();
        for (byte i = 1; i <= lengthPinCode; i++) pinCodeBuffer.append((byte) (Math.random() * 10));
        return pinCodeBuffer.toString();
    }

    // Вынесем из метода authorization генерацию кода авторизации и проверку статуса карты в отдельный метод authorizationStatusCard()
    public String authorizationStatusCard(Card card) {
        // Сгенерировать код авторизации
        String authorizationCode = generateAuthorizationCode();
        String authorizationMessage = card.getStatusCard().equalsIgnoreCase("Активна") ? "Success: Карта активна" : "Failed: Карта заблокирована";
        return authorizationCode + "@" + authorizationMessage;
    }

    public String generateAuthorizationCode() {
        byte lengthAuthorizationCode = 6;
        StringBuffer authorizationCodeBuffer = new StringBuffer();
        for (byte i = 0; i < lengthAuthorizationCode; i++) {
            authorizationCodeBuffer.append((byte) (Math.random() * 10));
        }
        return authorizationCodeBuffer.toString();
    }

    //  Провести авторизацию и выдать разрешение на проведение операции
    public String authorization(Card card, String typeOperation, float sum, float commission, String pinCode) {
        // провести авторизацию в части проверки статуса карты
        String authorizationStatusCard = authorizationStatusCard(card);
        // извлекаем код авторизации
        String authorizationCode = authorizationStatusCard.split("@")[0];
        // извлекаем сообщение авторизации
        String authorizationMessage = authorizationStatusCard.split("@")[1];
        // извлекаем статус из сообщения авторизации
        String authorizationStatus = authorizationMessage.substring(0, authorizationMessage.indexOf(":"));

        if (authorizationStatus.equalsIgnoreCase("Success")) {
            // тип операции может быть либо покупка, либо перевод, так как авторизацию при пополнении мы уже вынесли в метод authorizationStatusCard()

            // если тип операции покупка и набранный пин-код не соответствует пин-коду карты, то авторизация завершается с ошибкой и операция не выполняется
            if (typeOperation.contains("Покупка") && !pinCode.equals(card.getPinCode())) return authorizationCode + "@" + "Failed: Неверный пин-код";

            // проверяем баланс, достаточно ли денег с учётом комиссии и блокируем сумму покупки или перевода с комиссией
            boolean checkBalance = card.getPayCardAccount().checkBalance(sum + commission);
            if (checkBalance) {
                // проверяем не превышен ли лимит по оплатам и переводам в сутки
                boolean exceededLimitPaymentsTransfersDay = card.getCardHolder().exceededLimitPaymentsTransfersDay(sum, card.getPayCardAccount().getCurrencyCode());
                if (!exceededLimitPaymentsTransfersDay) {
                    // блокируем сумму операции и комиссию на балансе счёта карты
                    boolean reserveAmountStatus = card.getPayCardAccount().blockSum(sum + commission);
                    authorizationMessage = reserveAmountStatus ? "Success: Авторизация прошла успешно, сумма операции заблокирована" : "Failed: Сбой авторизации";
                } else authorizationMessage = "Failed: Превышен лимит по оплатам и переводам в сутки";
            } else authorizationMessage = "Failed: Недостаточно средств, пополните карту";
        }

        // возвращаем код и сообщение о статусе авторизации
        return authorizationCode + "@" + authorizationMessage;
    }


    // Рассчитать комиссию при оплате
    public float getCommission(PhysicalPersonProfile cardHolder, float sumPay, String buyProductOrService) {
        float commission = buyProductOrService.equalsIgnoreCase("ЖКХ") ? (sumPay/100) * cardHolder.getPercentOfCommissionForPayHousingCommunalServices() : 0;
        return commission;
    }

    // Рассчитать комиссию за перевод на свою или чужую карту моего или другого банка
    public float getCommission(PhysicalPersonProfile clientProfile, String fromCurrencyCode, float sum, Card toCard) {
        // запросить моя ли карта, на которую выполняется перевод
        boolean isMyCard = clientProfile.isClientCard(toCard);
        // запросить моего ли банка карта, на которую выполняем перевод
        boolean isCardMyBank = isCardBank(toCard);

        float commission = 0;
        // если карта зачисления не моя, но моего банка, то вычисляем комиссию за перевод клиенту моего банка
        if (!isMyCard && isCardMyBank) commission = getCommissionOfTransferToClientBank(clientProfile, sum, fromCurrencyCode);
        // если карта зачисления не моя и не моего банка, то вычисляем комиссию за перевод клиенту другого банка
        if (!isMyCard && !isCardMyBank) commission = getCommissionOfTransferToClientAnotherBank(clientProfile, sum, fromCurrencyCode);

        // Проверить превышен ли лимит на сумму комиссии. Если да, то ограничим сумму комиссии заданным лимитом
        commission = exceededLimitCommission(clientProfile, fromCurrencyCode, commission);

        return commission;
    }

    // Рассчитать комиссию за перевод на свою или чужую карту моего или другого банка
    public float getCommission(PhysicalPersonProfile clientProfile, String fromCurrencyCode, float sum, Account toAccount) {
        // запросить мой ли счет, на который выполняется перевод
        boolean isMyAccount = clientProfile.isClientAccount(toAccount);
        // запросить моего ли банка счет, на который выполняем перевод
        boolean isAccountMyBank = isAccountBank(toAccount);

        float commission = 0;
        // если счет зачисления не мой, но моего банка, то вычисляем комиссию за перевод клиенту моего банка
        if (!isMyAccount && isAccountMyBank) commission = getCommissionOfTransferToClientBank(clientProfile, sum, fromCurrencyCode);
        // если счет зачисления не мой и не моего банка, то вычисляем комиссию за перевод клиенту другого банка
        if (!isMyAccount && !isAccountMyBank) commission = getCommissionOfTransferToClientAnotherBank(clientProfile, sum, fromCurrencyCode);

        // Проверить превышен ли лимит на сумму комиссии. Если да, то ограничим сумму комиссии заданным лимитом
        commission = exceededLimitCommission(clientProfile, fromCurrencyCode, commission);

        return commission;
    }

    // Проверить превышен ли лимит на сумму комиссии?
    private float exceededLimitCommission(PhysicalPersonProfile clientProfile, String fromCurrencyCode, float commission) {
        // если комиссия превышает лимит за перевод в рублях, то ограничим комиссию лимитом в рублях, то есть максимально возможной суммой комиссии установленной банком
        if (fromCurrencyCode.equals(Currency.RUB.toString()) && commission > clientProfile.getLimitCommissionTransferInRUB()) commission = clientProfile.getLimitCommissionTransferInRUB();
        // иначе если комиссия превышает лимит за перевод в $, то ограничим комиссию лимитом в $
        if (fromCurrencyCode.equals(Currency.USD.toString()) && commission > clientProfile.getLimitCommissionTransferInUsdOrEquivalentInOtherCurrency())
            commission = clientProfile.getLimitCommissionTransferInUsdOrEquivalentInOtherCurrency();
        // иначе если другая валюта, то по аналогии
        if (!fromCurrencyCode.equals(Currency.RUB.toString()) && !fromCurrencyCode.equals(Currency.USD.toString())) {
            // рассчитать лимит комиссии в другой валюте путём конвертации лимита в $ в эквивалентную сумму в другой валюте
            float limitCommissionTransferInCurrency = convertToCurrencyExchangeRateBank(
                    clientProfile.getLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(),
                    Currency.USD.toString(),
                    fromCurrencyCode
            );
            // если комиссия превышает лимит за перевод в другой валюте, то ограничим комиссию лимитом в этой валюте
            if (commission > limitCommissionTransferInCurrency) commission = limitCommissionTransferInCurrency;
        }

        return commission;
    }

    // Рассчитать комиссию за перевод клиенту моего банка.
    public abstract float getCommissionOfTransferToClientBank(PhysicalPersonProfile clientProfile, float sum, String fromCurrencyCode);

    // Рассчитать комиссию за перевод клиенту другого банка
    private float getCommissionOfTransferToClientAnotherBank(PhysicalPersonProfile clientProfile, float sum, String fromCurrencyCode) {
        // можно не инициализировать, так как в любом случае будет результат благодаря ветке else
        float commission;
        // рассчитаем комиссию за перевод в рублях
        if (fromCurrencyCode.equals(Currency.RUB.toString())) commission = (sum / 100) * clientProfile.getPercentOfCommissionForTransferInRUB();
        // рассчитаем комиссию за перевод в $ или другой валюте
        else commission = (sum / 100) * clientProfile.getPercentOfCommissionForTransferInUsdOrOtherCurrency();

        return commission;

    }

    // Проверить карта моего ли банка
    public boolean isCardBank(Card card) {
        if (card.getBank().getBankName().equals(getBankName())) return true;
        return false;
    }

    // Проверить счет моего ли банка
    public boolean isAccountBank(Account account) {
        if (account.getBank().getBankName().equals(getBankName())) return true;
        return false;
    }

    // Запросить код валюты по названию страны
    public static String getCurrencyCode(String country) {
        String currencyPayCode = null;
        if (country.equalsIgnoreCase(Country.KAZAKHSTAN.toString())) currencyPayCode = Currency.KZT.toString();
        if (country.equalsIgnoreCase(Country.TURKEY.toString())) currencyPayCode = Currency.TRY.toString();
        if (country.equalsIgnoreCase(Country.FRANCE.toString())) currencyPayCode = Currency.EUR.toString();
        return currencyPayCode;
    }

    // Запросить символ валюты по коду валюты
    public static String getCurrencySymbol(String currencyCode) {
        if (currencyCode.equalsIgnoreCase(Currency.RUB.toString())) return Currency.RUB.getSymbol();
        if (currencyCode.equalsIgnoreCase(Currency.USD.toString())) return Currency.USD.getSymbol();
        if (currencyCode.equalsIgnoreCase(Currency.KZT.toString())) return Currency.KZT.getSymbol();
        if (currencyCode.equalsIgnoreCase(Currency.TRY.toString())) return Currency.TRY.getSymbol();
        if (currencyCode.equalsIgnoreCase(Currency.EUR.toString())) return Currency.EUR.getSymbol();
        return "?";
    }

    // Конвертировать в валюту по курсу банка
    public float convertToCurrencyExchangeRateBank(float sum, String fromCurrencyCode, String toCurrencyCode) {
        if (!fromCurrencyCode.equalsIgnoreCase(toCurrencyCode)) {
            ArrayList<Float> exchangeRateToCardCurrency = getExchangeRateBank(fromCurrencyCode, toCurrencyCode);
            sum *= exchangeRateToCardCurrency.get(0);
        }
        return sum;
    }

    //  Предоставить обменный курс валют банка.
    public abstract ArrayList<Float> getExchangeRateBank(String currency, String currencyExchangeRate);

    public float round(float sum) {
        return Math.round(sum * 100.00f) / 100.00f;
    }


}
