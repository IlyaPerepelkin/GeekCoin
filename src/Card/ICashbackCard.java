package Card;

public interface ICashbackCard {

    //накопления кэшбэка
    void accumulateCashback(float sumPay);

    // зачисления на карту накопленного кэшбэка
    void depositingCashback2Card();

}
