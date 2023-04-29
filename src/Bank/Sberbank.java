package Bank;
import ClientProfile.ClientProfile;
import ClientProfile.PhysicalPersonProfile;
import ClientProfile.SberPhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

import java.util.ArrayList;

public class Sberbank extends Bank implements IBankServicePhysicalPerson {

    public static final String SBER;

    static {
        SBER = "Сбер";
        System.out.println(SBER + " для физических лиц");
    }

    public Sberbank() {
        this(SBER);
    }

    public Sberbank(String bankName) {
        super(bankName);
    }

    // Зарегистрировать профиль физ лица
    @Override
    public PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson) {
        // создать профиль клиента
        SberPhysicalPersonProfile sberPhysicalPersonProfile = new SberPhysicalPersonProfile(this, physicalPerson);
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

        return sberPhysicalPersonProfile;
    }

    @Override
    // Предоставить обменный курс валют Сбера
    public ArrayList<Float> getExchangeRateBank(String currency, String currencyExchangeRate) {
        // TODO: Запрос к API банка
        ArrayList<Float> exchangeRateBank = new ArrayList<>();
        // курс доллара к рублю
        if (currency.equals("USD") && currencyExchangeRate.equals("RUB")) {
            exchangeRateBank.add(62.75f); // курс покупки
            exchangeRateBank.add(60.48f); // курс продажи
        }
        // курс евро к рублю
        if (currency.equals("EUR") && currencyExchangeRate.equals("RUB")) {
            exchangeRateBank.add(63.55f); // курс покупки
            exchangeRateBank.add(61.35f); // курс продажи
        }
        return  exchangeRateBank;
    }


    // Рассчитать комиссию за перевод клиенту моего банка Сбер
    public float getCommissionOfTransferToClientBank(ClientProfile clientProfile, float sum, String fromCurrencyCode) {
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
