package Bank;

import Account.Account;
import Account.SberPayCardAccount;
import Card.Card;
import ClientProfile.PhysicalPersonProfile;
import ClientProfile.SberPhysicalPersonProfile;
import PhysicalPerson.PhysicalPerson;

public interface IServicePhysicalPerson {

    PhysicalPersonProfile registerPhysicalPersonProfile(PhysicalPerson physicalPerson);

    Card openCard(PhysicalPersonProfile physicalPersonProfile, Card card, String currencyCode, String pinCode);

    Account openAccount(PhysicalPersonProfile physicalPersonProfile, Account account, String currencyCode);

}
