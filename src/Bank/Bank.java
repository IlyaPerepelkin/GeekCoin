package Bank;

import Account.SavingsAccount;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;

import java.util.ArrayList;

public abstract class Bank {
    private String bankName;

    private ArrayList<PhysicalPersonProfile> clientProfiles = new ArrayList<>();


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public ArrayList<PhysicalPersonProfile> getClientProfiles() {
        return clientProfiles;
    }

    public void setClientProfiles(ArrayList<PhysicalPersonProfile> clientProfiles) {
        this.clientProfiles = clientProfiles;
    }


    // Добавить профиль клиента
    public void addClientProfile(PhysicalPersonProfile clientProfile) {
            clientProfiles.add(clientProfile);
    }

    // Сгенерировать номер карты 3546 0957 9843 7845
    public String generateNumberCard() {
        byte lengthNumberCard = 20;
        StringBuffer numberCardBuffer = new StringBuffer();
        for (byte i = 1; i < lengthNumberCard; i++) {     // i = 1, чтобы не было пробела в начале, потому что при делении 0 на 5 будет 0
            if (i % 5 !=0) numberCardBuffer.append((byte) (Math.random() * 10));
            else numberCardBuffer.append(' ');
        }
        return numberCardBuffer.toString();
    }

    // Сгенерировать номер в формате 15738 538 9 4736 9745275
    public String generateNumberAccount() {
        byte lengthNumberAccount = 25;
        StringBuffer numberAccountBuffer = new StringBuffer();
        for (byte i = 1; i < lengthNumberAccount; i++) {
            if (i == 6 || i == 10 || i == 12 || i == 17) numberAccountBuffer.append(" ");
            else numberAccountBuffer.append((byte) (Math.random() * 10));
        }
        return numberAccountBuffer.toString();
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

        String[] authorizationStatus = authorizationStatusCard(card).split("@");
        String authorizationCode = authorizationStatus[0];
        String authorizationMessage = authorizationStatus[1];

        if (authorizationMessage.contains("Success")) {
            // если тип операции покупка или перевод, то проверяем баланс и блокируем сумму покупки или перевода с комиссией
            if (typeOperation.contains("Покупка") || typeOperation.contains("Перевод")) {

                if (typeOperation.contains("Покупка") && !card.getPinCode().equals(pinCode)) return authorizationCode + "@" + "Failed: Неверный пин код";

                // проверяем баланс и хватит ли нам денег с учетом комиссии
                boolean checkBalance = card.getPayCardAccount().checkBalance(sum + commission);
                if (checkBalance) {
                    // проверяем не превышен ли лимит по оплатам и переводам в сутки
                    boolean exceededLimitPaymentsTransfersDay = card.getCardHolder().exceededLimitPaymentsTransfersDay(sum, card.getPayCardAccount().getCurrencyCode());
                    if (!exceededLimitPaymentsTransfersDay) {
                        // блокируем сумму операции и комиссию на балансе счета карты
                        boolean reserveAmountStatus = card.getPayCardAccount().blockSum(sum + commission);
                        authorizationMessage = reserveAmountStatus ? "Success: Авторизация прошла успешно" : "Failed: Сбой авторизации";
                    } else authorizationMessage = "Failed: Превышен лимит по оплатам и переводам в сутки";
                } else authorizationMessage = "Failed: Недостаточно средств, пополните карту";
            }
        }

        // вернуть код и сообщение о статусе авторизации
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
        if (!isMyCard && isCardMyBank) commission = getCommissionOfTransferToClientBank(); // clientProfile, sum, fromCurrencyCode
        // если карта зачисления не моя и не моего банка, то вычисляем комиссию за перевод клиенту другого банка
        if (!isMyCard && !isCardMyBank) commission = getCommissionOfTransferToClientAnotherBank(clientProfile, sum, fromCurrencyCode);

    // Проверить превышен ли лимит на сумму комиссии. Если да, то ограничим сумму комиссии заданным лимитом
        commission = exceededLimitCommission(clientProfile, fromCurrencyCode, commission);

        return commission;
    }

    // Рассчитать комиссию за перевод на свою или чужую карту моего или другого банка
    public float getCommission(PhysicalPersonProfile clientProfile, String fromCurrencyCode, float sum, SavingsAccount toAccount) {
        // запросить мой ли счет, на который выполняется перевод
        boolean isMyAccount = clientProfile.isClientAccount(toAccount);
        // запросить моего ли банка счет, на который выполняем перевод
        boolean isAccountMyBank = isAccountBank(toAccount);

        float commission = 0;
        // если счет зачисления не мой, но моего банка, то вычисляем комиссию за перевод клиенту моего банка
        if (!isMyAccount && isAccountMyBank) commission = getCommissionOfTransferToClientBank(); // clientProfile, sum, fromCurrencyCode
        // если счет зачисления не мой и не моего банка, то вычисляем комиссию за перевод клиенту другого банка
        if (!isMyAccount && !isAccountMyBank) commission = getCommissionOfTransferToClientAnotherBank(clientProfile, sum, fromCurrencyCode);

        // Проверить превышен ли лимит на сумму комиссии. Если да, то ограничим сумму комиссии заданным лимитом
        commission = exceededLimitCommission(clientProfile, fromCurrencyCode, commission);

        return commission;
    }

    // Проверить превышен ли лимит на сумму комиссии?
    private float exceededLimitCommission(PhysicalPersonProfile clientProfile, String fromCurrencyCode, float commission) {
        // Если нет, то комиссия равна себе
        if (fromCurrencyCode.equals("RUB") && commission <= clientProfile.getLimitCommissionTransferInRUB()) return commission;
        else if (fromCurrencyCode.equals("USD") && commission <= clientProfile.getLimitCommissionTransferInUsdOrEquivalentInOtherCurrency())
            return commission;
        else { // Если да, то ограничим сумму комиссии лимитом
            // если комиссия превышает лимит за перевод в рублях, то ограничим комиссию в рублях, то есть максимально возможной суммой комиссии установленной банком
            if (fromCurrencyCode.equals("RUB") && commission > clientProfile.getLimitCommissionTransferInRUB())
                commission = clientProfile.getLimitCommissionTransferInRUB();
                // иначе если комиссия превышает лимит за перевод в $, то ограничим комиссию лимитом в $
            else if (fromCurrencyCode.equals("USD") && commission > clientProfile.getLimitCommissionTransferInUsdOrEquivalentInOtherCurrency())
                commission = clientProfile.getLimitCommissionTransferInUsdOrEquivalentInOtherCurrency();
                // иначе если другая валюта, то по аналоги
            else {
                // рассчитать лимит комиссии в другой валюте путем конвертации лимита в $ в эквивалентную сумму в другой валюте
                float limitCommissionTransferInCurrency = convertToCurrencyExchangeRateBank(
                        clientProfile.getLimitCommissionTransferInUsdOrEquivalentInOtherCurrency(),
                        "USD",
                        fromCurrencyCode);
                // если комиссия превышает лимит за перевод в другой валюте, то ограничим комиссию лимитом в этой валюте
                if (commission > limitCommissionTransferInCurrency) commission = limitCommissionTransferInCurrency;
            }
        }

        return commission;

    }

    // Рассчитать комиссию за перевод клиенту моего банка.
    public abstract float getCommissionOfTransferToClientBank();

    // Рассчитать комиссию за перевод клиенту другого банка
    private float getCommissionOfTransferToClientAnotherBank(PhysicalPersonProfile clientProfile, float sum, String fromCurrencyCode) {
        // можно не инициализировать, так как в любом случае будет результат благодаря ветке else
        float commission;
        // рассчитаем комиссию за перевод в рублях
        if (fromCurrencyCode.equals("RUB")) commission = (sum / 100) * clientProfile.getPercentOfCommissionForTransferInRUB();
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
    public boolean isAccountBank(SavingsAccount account) {
        if (account.getBank().getBankName().equals(getBankName())) return true;
        return false;
    }

    // Запросить код валюты по названию страны
    public String getCurrencyCode(String country) {
        String currencyPayCode = null;
        if (country.equalsIgnoreCase("Турция")) currencyPayCode = "TRY";
        if (country.equalsIgnoreCase("Франция")) currencyPayCode = "EUR";
        return currencyPayCode;
    }

    // Конвертировать в валюту по курсу банка
    public float convertToCurrencyExchangeRateBank(float sum, String fromCurrencyCode, String toCurrencyCode) {
        float exchangeRateToCardCurrency = getExchangeRateBank();
        float sumInCardCurrency = sum * exchangeRateToCardCurrency;

        return sumInCardCurrency;
    }

    //  Предоставить обменный курс валют банка.
    public abstract float getExchangeRateBank();

    public float round(float sum) {
        return Math.round(sum * 100.00f) / 100.00f;
    }


}
