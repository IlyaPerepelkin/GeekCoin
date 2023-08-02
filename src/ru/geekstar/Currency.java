package ru.geekstar;

public enum Currency {

    RUB ("₽"),
    USD ("$"),
    EUR ("€"),
    KZT ("₸"),
    TRY ("₺");

    private String symbol;

    Currency(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
