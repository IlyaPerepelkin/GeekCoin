package Transaction;

import ClientProfile.TinkoffPhysicalPersonProfile;

public class PayMilesTransaction extends PayTransaction {

    public int miles;

    public int getMiles() {
        return miles;
    }

    public void setMiles(int miles) {
        this.miles = miles;
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getNameCard(getFromCard()) + " " + getBuyProductOrService() + ": " +
            " Миль использовано: " + getMiles();

        return transaction;
    }

}
