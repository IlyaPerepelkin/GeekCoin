public class Main {
    public static void main(String[] args) {

        Card visaCard = new Card();
        visaCard.setDeposit(7000.10f);
        visaCard.setNumberCard("4567 7899 7851 1523");
        visaCard.setPaySystems("VISA");
        visaCard.setCurrency('₽');
        visaCard.pay(200.10f);
        visaCard.pay(250.50f);
        visaCard.transfer(1000.00f);

        System.out.println("Операции по карте " + visaCard.getPaySystems() + " " + visaCard.getNumberCard() + ": ");
        String[] transactions = visaCard.getTransactions();
        int countTransactions = visaCard.getCountTransactions();
        for (int id = 0; id < countTransactions; id++) {
            // System.out.println("Операция #" + id + " " + " по карте" + transactions[id]);
        }

        Card masterCard = new Card();
        masterCard.setDeposit(5000.00f);
        masterCard.setNumberCard("7898 8521 1236 5456");
        masterCard.setPaySystems("MAESTRO");
        masterCard.setCurrency('$');
        masterCard.pay(500.55f);

        // Создаем новую карту типа UnionPayCard
        Card unionPayCard = new Card();
        unionPayCard.setDeposit(90000.50f);
        unionPayCard.setNumberCard("9999 7854 4564 5213");
        unionPayCard.setPaySystems("UnionPay");
        unionPayCard.setCurrency('€');
        unionPayCard.pay(15000.00f);


        // Создадим массив карт

        Card[] cards = new Card[3];
        cards[0] = visaCard;
        cards[1] = masterCard;
        cards[2] = unionPayCard;

        cards[0].pay(6000.00f);
        cards[1].transfer(600.20f);
        cards[2].pay2(9999.00f);
        cards[2].depositing(500.00f);
        cards[1].depositing(125.50f);


        System.out.println("Операции по всем картам: ");
        for (int idCard = 0; idCard < cards.length; idCard++) {
            Card card = cards[idCard];
            String[] cardTransactions = card.getTransactions();
            int cardCountTransactions = card.getCountTransactions();

            for (int id = 0; id < cardCountTransactions; id++) {
                System.out.println("Операция #" + id + " " + " по карте " + cardTransactions[id]);
            }

        }

    }
}