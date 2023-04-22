package Bank;

import Account.Account;
import Account.PayCardAccount;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;
import Bank.Bank;

public interface IBankServicePhysicalPerson {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    default Card openCard(PhysicalPersonProfile physicalPersonProfile, Card card, String currencyCode, String pinCode){
        // установить свойства карты
        card.setBank(this);
        card.setNumberCard(bank.generateNumberCard());
        card.setCardHolder(physicalPersonProfile);
        card.setPinCode(pinCode);

        // открыть платежный счет
        PayCardAccount payCardAccount = openAccount(physicalPersonProfile, new PayCardAccount(), currencyCode);

        // привязать карту к платежному счету
        payCardAccount.getCards().add(card);

        // привязать платежный счет к карте
        card.setPayCardAccount(payCardAccount);
        card.setStatusCard("Активна");

        return card;
    }

    default Account openAccount(PhysicalPersonProfile physicalPersonProfile, Account account, String currencyCode) {
        // установить свойства платежного счета
        account.setBank(this);
        account.setNumberAccount(bank.generateNumberAccount());
        account.setAccountHolder(physicalPersonProfile);
        account.setCurrencyCode(currencyCode);
        account.setCurrencySymbol(currencyCode);

        return account;
    }

}
