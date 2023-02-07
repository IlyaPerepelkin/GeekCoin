import Account.SberSavingsAccount;
import Bank.Sberbank;
import Card.SberVisaGold;
import PhysicalPerson.PhysicalPerson;

public class Main {
    public static void main(String[] args) {

        PhysicalPerson I = new PhysicalPerson();
        I.setFirstName("Игорь");
        I.setLastName("Коннов");
        I.setTelephone("+79273253258");
        I.setAge((byte)32);
        I.setGender('M');

        PhysicalPerson friend = new PhysicalPerson();
        friend.setFirstName("Герман");
        friend.setLastName("Греф");
        friend.setTelephone("+79008203535");
        friend.setAge((byte)52);
        friend.setGender('M');

        Sberbank sberbank = new Sberbank();
        sberbank.setBankName("Сбер");

        I.registerToBank(sberbank);
        friend.registerToBank(sberbank);

        SberVisaGold mySberVisaGold1 = I.openCard(sberbank, new SberVisaGold(), "RUB");
        SberVisaGold mySberVisaGold2 = I.openCard(sberbank, new SberVisaGold(), "RUB");

        SberSavingsAccount mySberSavingsAccount1 = I.openAccount (sberbank, new SberSavingsAccount(), "RUB");
        SberSavingsAccount mySberSavingsAccount2 = I.openAccount (sberbank, new SberSavingsAccount(), "RUB");

        SberVisaGold friendSberVisaGold1 = friend.openCard(sberbank, new SberVisaGold(), "RUB");

        I.depositingCash2Card(mySberVisaGold1, 7600.50f);

        I.payBayCard(mySberVisaGold1, 100.05f, "ЖКХ");
        I.payBayCard(mySberVisaGold1, 110.00f, "Excursion", "Турция");

        I.transferCard2Card(mySberVisaGold1, mySberVisaGold2, 250.00f);
        I.transferCard2Card(mySberVisaGold1, friendSberVisaGold1, 55.00f);

        I.transferCard2Account(mySberVisaGold1, mySberSavingsAccount1, 95.00f);

        I.transferAccount2Card(mySberSavingsAccount1, mySberVisaGold1, 15.00f);

        I.depositingCardFromCard(mySberVisaGold1, mySberVisaGold2, 145.00f);
        I.depositingCardFromAccount(mySberVisaGold1, mySberSavingsAccount1, 75.00f);

        I.depositingAccountFromCard(mySberSavingsAccount1, mySberVisaGold1, 350.00f);

        System.out.println("Вывод операции по карте " + mySberVisaGold1.getNumberCard());
        I.displayCardTransactions(mySberVisaGold1);

        System.out.println("\nВывод операции по карте друга " + friendSberVisaGold1.getNumberCard());
        I.displayCardTransactions(friendSberVisaGold1);

        System.out.println("\nВывод операции по карте " + mySberVisaGold2.getNumberCard());
        I.displayCardTransactions(mySberVisaGold2);

        System.out.println("\nВывод операций по счету " + mySberSavingsAccount1.getNumberAccount());
        I.displayAccountTransactions(mySberSavingsAccount1);

        // Вывод всех операций по всем картам и счетам профиля клиента

    }
}