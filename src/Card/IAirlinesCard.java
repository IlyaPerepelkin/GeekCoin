package Card;

public interface IAirlinesCard {

    // накопление миль привязанных к профилю физ лица, путём начисления транзакционных миль из расчёта 1 миля за каждые потраченные 60 ₽ или 1 $/€ по этой карте
    void accumulateMiles(float sumPay, String currency);

    // оплата милями до 100% стоимости билетов из расчёта 1 миля = 1₽
    void payByCardMiles(int ticketPrice,String byProductOrService, String pinCode);

}
