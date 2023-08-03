package ru.geekstar.Card.IPaySystem;

import ru.geekstar.Currency;

public interface IMastercard extends IPaySystem {

    String CURRENCY_CODE_PAY_SYSTEM_USD = Currency.USD.toString();

    String CURRENCY_CODE_PAY_SYSTEM_EUROZONE = Currency.EUR.toString();

}
