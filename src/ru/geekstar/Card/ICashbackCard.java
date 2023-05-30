package ru.geekstar.Card;

public interface ICashbackCard {

    float getCashback();

    //накопления кэшбэка
    void accumulateCashback(float sumPay);

    // зачисления на карту накопленного кэшбэка
    void depositingCashback2Card();

}
