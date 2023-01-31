package Bank;

import Card.Card;
import ClientProfile.SberPhysicalPersonProfile;
import Card.SberVisaGold;

public class Bank {
    private String bankName;

    private SberPhysicalPersonProfile[] clientProfiles = new SberPhysicalPersonProfile[5];

    private int countClientProfiles;


    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public SberPhysicalPersonProfile[] getClientProfiles() {
        return clientProfiles;
    }

    public void setClientProfiles(SberPhysicalPersonProfile[] clientProfiles) {
        this.clientProfiles = clientProfiles;
    }

    public int getCountClientProfiles() {
        return countClientProfiles;
    }

    public void setCountClientProfiles(int countClientProfiles) {
        this.countClientProfiles = countClientProfiles;
    }


    // Добавить профиль клиента
    public void addClientProfile(SberPhysicalPersonProfile clientProfile) {
        clientProfiles[countClientProfiles++] = clientProfile;
    }

    // Сгенирировать номер карты 3546 0957 9843 7845
    public String generateNumberCard() {
        byte lengthNumberCard = 20;
        StringBuffer numberCardBuffer = new StringBuffer();
        for (byte i = 1; i < lengthNumberCard; i++) {     // i = 1, чтобы не было пробела в начале, потому что при делении 0 на 5 будет 0
            if (i % 5 !=0) numberCardBuffer.append((byte) Math.random() * 10);
            else numberCardBuffer.append(' ');
        }
        return numberCardBuffer.toString();
    }

    // Сгенирировать номер 3546709579984317845
    public String generateNumberAccount() {
        byte lengthNumberAccount = 20;
        StringBuffer numberAccountBuffer = new StringBuffer();
        for (byte i = 0; i < lengthNumberAccount; i++) {
            numberAccountBuffer.append((byte) Math.random() * 10);
        }
        return numberAccountBuffer.toString();
    }

    //  Провести авторизацию и выдать разрешение на проведенеи операции
    public String authorization(SberVisaGold card, String typeOperation, float sum, float commission) {
        // Сгенерировать код авторизации
        String authorizationCode = generateAuthorizationCode();

        String authorizationMessage;
        // проверить статус карты
        boolean statusCard = card.getStatusCard().equalsIgnoreCase("Активна") ? true : false;
        if (statusCard) {
            authorizationMessage = "Success: Карта активна";

            // если тип операции покупка или перевод, то проверяем баланс и блокируем сумму покупки или перевода с комиссией
            if (typeOperation.contains("Покупка") || typeOperation.contains("Перевод")) {
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
        } else authorizationMessage = "Failed: Карта заблокирована";

        // вернуть код и сообщение о статсу авторизации
        return authorizationCode + "@" + authorizationMessage;
    }

    private String generateAuthorizationCode() {
        byte lengthAuthorizationCode = 6;
        StringBuffer authorizationCodeBuffer = new StringBuffer();
        for (byte i = 0; i < lengthAuthorizationCode; i++) {
            authorizationCodeBuffer.append((byte) Math.random() * 10);
        }
        return authorizationCodeBuffer.toString();
    }

    // Рассчитать комиссию при оплате
    public float getCommission(SberPhysicalPersonProfile cardHolder, float sumPay, String buyProductOrService) {
        float commission = buyProductOrService.equalsIgnoreCase("ЖКХ") ? (sumPay/100) * cardHolder.getPercentOfCommissionForPayHousingCommunalServices() : 0;
        return commission;
    }
}
