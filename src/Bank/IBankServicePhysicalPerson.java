package Bank;

import Account.Account;
import Account.PayCardAccount;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

public interface IBankServicePhysicalPerson {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    default Card openCard(PhysicalPersonProfile physicalPersonProfile, Class<? extends Card> classCard, Class<? extends Account> classAccount, String currencyCode, String pinCode){

        // открыть платежный счет
        PayCardAccount bankPayCardAccount = (PayCardAccount) openAccount(physicalPersonProfile, account, currencyCode);

        Card card = null;
        try {
            card = classCard.getConstructor(PhysicalPersonProfile.class, PayCardAccount.class, String.class)
                    .newInstance(physicalPersonProfile, bankPayCardAccount, pinCode);
        } catch (Exception e) {
            System.out.println(e);
        }

        Account account = null;
        try {
            account = classAccount.getConstructor(PhysicalPersonProfile.class, PayCardAccount.class, String.class)
                    .newInstance(physicalPersonProfile, bankPayCardAccount, currencyCode);
        } catch (Exception e) {
            System.out.println(e);
        }

        // привязать карту к платежному счету
        bankPayCardAccount.getCards().add(card);

        //привязать карту к профилю клиента
        physicalPersonProfile.getCards().add(card);

        return card;
    }

    default Account openAccount(PhysicalPersonProfile physicalPersonProfile, Account account, String currencyCode) {
        // установить свойства платежного счета
        account.setBank(physicalPersonProfile.getBank());
        account.setNumberAccount(Bank.generateNumberAccount());
        account.setAccountHolder(physicalPersonProfile);
        account.setCurrencyCode(currencyCode);
        account.setCurrencySymbol(currencyCode);

        physicalPersonProfile.getAccounts().add(account);

        return account;
    }

}
