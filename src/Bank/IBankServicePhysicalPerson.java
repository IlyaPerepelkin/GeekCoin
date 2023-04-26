package Bank;

import Account.Account;
import Account.PayCardAccount;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

public interface IBankServicePhysicalPerson {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    default Card openCard(PhysicalPersonProfile physicalPersonProfile, Card card, PayCardAccount payCardAccount, String currencyCode, String pinCode){
        // установить свойства карты
        card.setBank(physicalPersonProfile.getBank());
        card.setNumberCard(physicalPersonProfile.getBank().generateNumberCard());
        card.setCardHolder(physicalPersonProfile);
        card.setPinCode(pinCode);

        // открыть платежный счет
        PayCardAccount cardAccount = (PayCardAccount) openAccount(physicalPersonProfile, payCardAccount, currencyCode);

        // привязать карту к платежному счету
        payCardAccount.getCards().add(card);

        // привязать платежный счет к карте
        card.setPayCardAccount(cardAccount);
        card.setStatusCard("Активна");

        physicalPersonProfile.getCards().add(card);

        return card;
    }

    default Account openAccount(PhysicalPersonProfile physicalPersonProfile, Account account, String currencyCode) {
        // установить свойства платежного счета
        account.setBank(physicalPersonProfile.getBank());
        account.setNumberAccount(physicalPersonProfile.getBank().generateNumberAccount());
        account.setAccountHolder(physicalPersonProfile);
        account.setCurrencyCode(currencyCode);
        account.setCurrencySymbol(currencyCode);

        physicalPersonProfile.getAccounts().add(account);

        return account;
    }

}
