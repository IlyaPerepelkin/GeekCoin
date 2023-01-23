import java.util.Currency;

public class Card {

    private float deposit;
    private String numberCard;
    private String paySystems;
    private int countTransactions = 0;
    private String[] transactions = new String[50];

    // новая переменная экземпляра "Валюта"
    private char currency; // "₽";


    // сеттер и геттер для валюты
    public char getCurrency() {
        return currency;
    }

    public void setCurrency(char currency) {
        this.currency = currency;
    }

    public float getDeposit() {
        return deposit;
    }

    public void setDeposit(float deposit) {
        this.deposit = deposit;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        this.numberCard = numberCard;
    }

    public String getPaySystems() {
        return paySystems;
    }
    public void setPaySystems(String paySystems) {
        this.paySystems = paySystems;
    }

    public int getCountTransactions() {
        return countTransactions;
    }

    public String[] getTransactions() {
        return transactions;
    }

    public void setTransactions(String transactions) {
        this.transactions[countTransactions++] = transactions;
    }

    public void setCountTransactions(int countTransactions) {
        this.countTransactions = countTransactions;
    }


    public void pay(float sumPay) {
        // списать cумму покупки с карты
        boolean payStatus;
        byte check = 0;
        do {
            payStatus = withdrawal(sumPay);
            if (payStatus) { // payStatus == true
                String transactions = paySystems + " " + numberCard + ": " + "Покупка " + sumPay + currency + " Остаток на карте " + deposit + currency;
                setTransactions(transactions);
                System.out.println(transactions);
            } else check++;
        } while (!payStatus && check < 3);

        /*
         TODO: перевести сумму на счет магазина
         */
    }

    public void transfer(float sumTransfer) {
        float comission;
        if (sumTransfer < 50000) {
            comission = 0.0f;
        } else {
            comission = sumTransfer * 0.01f;
        }
        // списать деньги с карты
        boolean transferStatus;
        byte check = 0;
        do {
            transferStatus = withdrawal(sumTransfer + comission);
            if (transferStatus) {
                String transactions = paySystems + " " + numberCard + ": " + "Переведено " + sumTransfer + currency + " Комиссия составила " + comission + currency + " Остаток на карте " + deposit + currency;
                setTransactions(transactions);
                System.out.println(transactions);
            } else check++;
        } while (!transferStatus && check <3);
    }

    public void depositing(float sumDepositing)  {
        // внесение денег на карту
        deposit = deposit + sumDepositing;
        String transactions = paySystems + " " + numberCard + ": " + "Внесено " + sumDepositing + currency + " Остаток на карте " + deposit + currency;
        setTransactions(transactions);
        System.out.println(transactions);
    }

    private boolean withdrawal(float sum) {
        if (deposit >= sum) {
            deposit = deposit - sum;
            return true;
        } else {
            String transactions = paySystems + " " + numberCard + ": " + "Недостаточно денег " + deposit + currency;
            setTransactions(transactions);
            return false;
        }
    }
        // новый вид добавления денег на карту

        public boolean plusMoney(float sum) {
            if (sum > 0) {
                deposit = deposit + sum;
                return true;
            } else {
                String transactions = paySystems + " " + numberCard + ": " + "Операция недоступна" + deposit + currency;
                setTransactions(transactions);
                return false;
            }
        }
         public void pay2 (float sumPay2) {
             boolean payStatus;
             byte check = 0;
             do {
                 payStatus = plusMoney(sumPay2);
                 if (payStatus) { // payStatus == true
                     String transactions = paySystems + " " + numberCard + ": " + "Карта пополнена на " + sumPay2 + " Остаток на карте " + deposit + currency;
                     setTransactions(transactions);
                     System.out.println(transactions);
                 } else check++;
             } while (!payStatus && check <3);
         }

         // создаем конфликт двух веток
}



