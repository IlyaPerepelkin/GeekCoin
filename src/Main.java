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
        
    }
}