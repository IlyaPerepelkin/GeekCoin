package Bank;

import Account.Account;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

public interface IBankServicePhysicalPerson {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    Card openCard(PhysicalPersonProfile physicalPersonProfile, Card card, String currencyCode, String pinCode);

    Account openAccount(PhysicalPersonProfile physicalPersonProfile, Account account, String currencyCode);

}
