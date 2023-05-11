package Bank;

import Account.Account;
import Account.PayCardAccount;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

public interface IBankServicePhysicalPerson {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    default Card openCard(PhysicalPersonProfile physicalPersonProfile, Class<? extends Card> classCard, Class<? extends PayCardAccount> classPayCardAccount, String currencyCode, String pinCode) {

        // открыть платежный счет
        PayCardAccount bankPayCardAccount = (PayCardAccount) openAccount(physicalPersonProfile, classPayCardAccount, currencyCode);

        Card card = null;
        try {
            card = classCard.getConstructor(PhysicalPersonProfile.class, PayCardAccount.class, String.class)
                    .newInstance(physicalPersonProfile, bankPayCardAccount, pinCode);
            } catch (Exception e) {
            System.out.println(e);
        }

        // привязать карту к платежному счету
        bankPayCardAccount.getCards().add(card);

        //привязать карту к профилю клиента
        physicalPersonProfile.getCards().add(card);

        return card;
    }

    default Account openAccount(PhysicalPersonProfile physicalPersonProfile, Class<? extends Account> classAccount, String currencyCode) {
        Account account = null;
        try {
            account = classAccount.getConstructor(PhysicalPersonProfile.class, String.class)
                    .newInstance(physicalPersonProfile, currencyCode);

                physicalPersonProfile.getAccounts().add(account);
            } catch (Exception e) {
            System.out.println(e);
        }

        return account;

    }

}
