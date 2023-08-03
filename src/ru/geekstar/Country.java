package ru.geekstar;

public enum Country {

    TURKEY ("Турция"),
    KAZAKHSTAN ("Казахстан"),
    FRANCE ("Франция");

    private final String country;

    Country(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }


}
