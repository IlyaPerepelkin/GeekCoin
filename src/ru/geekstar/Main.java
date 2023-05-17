package ru.geekstar;

import ru.geekstar.Account.SberPayCardAccount;
import ru.geekstar.Account.SberSavingsAccount;
import ru.geekstar.Account.TinkoffPayCardAccount;
import ru.geekstar.Bank.Sberbank;
import ru.geekstar.Bank.Tinkoff;
import ru.geekstar.Card.*;
import ru.geekstar.PhysicalPerson.PhysicalPerson;

public class Main {

    public static void main(String[] args) {

        PhysicalPerson I = new PhysicalPerson("Илья", "Перепелкин", "+905010833253", (byte)30, 'M');
        PhysicalPerson friend = new PhysicalPerson("Герман", "Греф", "+79008203535", (byte)52, 'M');

        System.out.println("Физ.лица в системе " + PhysicalPerson.personCount);

        Sberbank sberbank = new Sberbank();
        Tinkoff tinkoff = new Tinkoff();

        I.registerPhysicalPersonToBank(sberbank);
        I.registerPhysicalPersonToBank(tinkoff);

        friend.registerPhysicalPersonToBank(sberbank);

        SberVisaGold mySberVisaGold1 = (SberVisaGold) I.openCard(sberbank, SberVisaGold.class, SberPayCardAccount.class,"RUB", "1515");
        SberVisaGold mySberVisaGold2 = (SberVisaGold) I.openCard(sberbank, SberVisaGold.class, SberPayCardAccount.class, "RUB", "1717");

        TinkoffBlackMir myTinkoffBlackMir1 = (TinkoffBlackMir) I.openCard(tinkoff, TinkoffBlackMir.class, TinkoffPayCardAccount.class, "RUB", "0011");

        SberMastercardGold sberMastercardGold = (SberMastercardGold) I.openCard(sberbank, SberMastercardGold.class, SberPayCardAccount.class, "RUB", "2535");

        SberMastercardTravel sberMastercardTravel = (SberMastercardTravel) I.openCard(sberbank, SberMastercardTravel.class, SberPayCardAccount.class, "RUB", "7895");
        I.switchAccountOfMulticurrencyCard(sberMastercardTravel, "USD");

        TinkoffAirlinesMir tinkoffAirlinesMir = (TinkoffAirlinesMir) I.openCard(tinkoff, TinkoffAirlinesMir.class, TinkoffPayCardAccount.class, "RUB", "0022");
        I.switchAccountOfMulticurrencyCard(tinkoffAirlinesMir, "RUB");

        SberSavingsAccount mySberSavingsAccount1 = (SberSavingsAccount) I.openAccount (sberbank, SberSavingsAccount.class, "RUB");
        SberSavingsAccount mySberSavingsAccount2 = (SberSavingsAccount) I.openAccount (sberbank, SberSavingsAccount.class, "RUB");

        SberVisaGold friendSberVisaGold1 = (SberVisaGold) friend.openCard(sberbank, SberVisaGold.class, SberPayCardAccount.class, "RUB", "1818");

        I.depositingCash2Card(sberMastercardTravel, 8300.00f);

        I.depositingCash2Card(tinkoffAirlinesMir, 7500.00f);

        I.depositingCash2Card(mySberVisaGold1, 7600.50f);
        I.depositingCash2Card(sberMastercardGold, 2000.00f);
        I.depositingCash2Card(myTinkoffBlackMir1, 30000.00f);

        I.payByCard(sberMastercardTravel, 3700.00f, "Bike", "Турция", "7895");
        I.payByCard(mySberVisaGold1, 100.50f, "ЖКХ", "1515");
        I.payByCard(sberMastercardGold, 700.00f, "Пятерочка", "2535");
        I.payByCard(myTinkoffBlackMir1, 12500.00f, "iPhone", "0011");

        I.payByCard(tinkoffAirlinesMir, 3500.00f, "Ноутбук", "0022");
        I.payByCardMiles(tinkoffAirlinesMir, 2000, 30, "Билет в Турцию", "0022");

        I.payByCard(mySberVisaGold1, 110.00f, "Excursion", "Турция","1515");
        I.payByCard(sberMastercardGold, 200.00f, "Attraction", "Турция", "2535");
        I.payByCardBonuses(mySberVisaGold1,157.00f, 2, "Starbucks", "1515");

        I.transferCard2Card(mySberVisaGold1, mySberVisaGold2, 250.00f);
        I.transferCard2Card(mySberVisaGold1, friendSberVisaGold1, 55.00f);
        I.transferCard2Card(sberMastercardGold, mySberVisaGold1, 100.00f);
        I.transferCard2Card(mySberVisaGold1, sberMastercardGold, 107.00f);

        I.transferCard2Account(mySberVisaGold1, mySberSavingsAccount1, 95.00f);
        I.transferCard2Account(sberMastercardGold, mySberSavingsAccount1, 57.00f);

        I.transferAccount2Card(mySberSavingsAccount1, mySberVisaGold1, 15.00f);
        I.transferAccount2Card(mySberSavingsAccount1, sberMastercardGold, 33.00f);
        I.transferAccount2Account(mySberSavingsAccount1, mySberSavingsAccount2, 50.00f);

        I.depositingCardFromCard(mySberVisaGold1, mySberVisaGold2, 145.00f);
        I.depositingCardFromCard(sberMastercardGold, mySberVisaGold1, 77.00f);
        I.depositingCardFromAccount(mySberVisaGold1, mySberSavingsAccount1, 75.00f);
        I.depositingCardFromAccount(sberMastercardGold, mySberSavingsAccount1, 53.00f);

        I.depositingAccountFromCard(mySberSavingsAccount1, mySberVisaGold1, 350.00f);
        I.depositingAccountFromCard(mySberSavingsAccount2, sberMastercardGold, 105.00f);
        I.depositingAccountFromAccount(mySberSavingsAccount1, mySberSavingsAccount2, 25.00f);

        I.depositingCashback2Card(myTinkoffBlackMir1);
/*
        System.out.println("Вывод операции по карте " + sberMastercardGold.getNumberCard());
        I.displayCardTransactions(sberMastercardGold);

        System.out.println("\nВывод операции по карте друга " + friendSberVisaGold1.getNumberCard());
        I.displayCardTransactions(friendSberVisaGold1);

        System.out.println("\nВывод операции по карте " + mySberVisaGold2.getNumberCard());
        I.displayCardTransactions(mySberVisaGold2);

        System.out.println("\nВывод операций по счету " + mySberSavingsAccount1.getNumberAccount());
        I.displayAccountTransactions(mySberSavingsAccount1);
*/

        // Вывод всех операций по всем картам и счетам профиля клиента с сортировкой по дате и времени
        // I.displayProfileTransactions(sberbank);
        // friend.displayProfileTransactions(sberbank);

        // I.displayTransactionHistory(sberbank);
        // I.clearTransactionHistory(sberbank);
        // I.displayTransactionHistory(tinkoff);
        // I.clearTransactionHistory(tinkoff);

        I.displayAllProfileTransactions();
        friend.displayAllProfileTransactions();


        System.out.println("Количество карт: " + "\nSberMastercardGold" + ": " + SberMastercardGold.count + "\nSberMastercardTravel" + ": " +
                SberMastercardTravel.count + "\nSberVisaGold" + ": " + SberVisaGold.count + "\nTinkoffAirlinesMir" + ": " + TinkoffAirlinesMir.count +
                "\nTinkoffBlackMir" + ": " + TinkoffBlackMir.count);
    }

}