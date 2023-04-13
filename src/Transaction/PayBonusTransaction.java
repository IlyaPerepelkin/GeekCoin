package Transaction;

public class PayBonusTransaction extends PayTransaction {

    private int payBonuses; // что в bonusesToPay
    private int balanceBonuses; // актуальный баланс бонусов на карте после списания


    public int getPayBonuses() {
        return payBonuses;
    }

    public void setPayBonuses(int payBonuses) {
        this.payBonuses = payBonuses;
    }

    public int getBalanceBonuses() {
        return balanceBonuses;
    }

    public void setBalanceBonuses(int balanceBonuses) {
        this.balanceBonuses = balanceBonuses;
    }

    @Override
    public String getStringTransaction() {

        String transaction = getLocalDateTime() + " " + getNameCard(getFromCard()) + " " + "Оплата бонусами " + getBuyProductOrService() + ": " +
                getPayBonuses() + " " + "бонусов" + " Статус: " + getStatusOperation()+ " Доступно бонусов: " + getBalanceBonuses();

        return transaction;
    }

}
