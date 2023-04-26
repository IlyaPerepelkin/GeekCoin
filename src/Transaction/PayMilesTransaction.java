package Transaction;

public class PayMilesTransaction extends PayTransaction {

    public int balanceMiles;


    public int getBalanceMiles() {
        return balanceMiles;
    }

    public void setBalanceMiles(int balanceMiles) {
        this.balanceMiles = balanceMiles;
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getNameCard(getFromCard()) + " " + getTypeOperation() + getBuyProductOrService() + " " +
                " Статус: " + getStatusOperation() + " " + "Баланс миль: " + getBalanceMiles();

        return transaction;
    }

}
