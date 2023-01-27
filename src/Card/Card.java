package Card;

import Account.SberPayCardAccount;
import Account.SberSavingsAccount;
import Bank.Sberbank;
import ClientProfile.SberPhysicalPersonProfile;
import Transaction.DepositingTransaction;

import java.time.LocalDateTime;
import java.util.Currency;
public class Card {

    private Sberbank bank;

    private SberPhysicalPersonProfile cardHolder;

    private SberPayCardAccount payCardAccount;

    private String numberCard;

    private String statusCard;


    public Sberbank getBank() {
        return bank;
    }

    public void setBank(Sberbank bank) {
        this.bank = bank;
    }

    public SberPhysicalPersonProfile getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(SberPhysicalPersonProfile cardHolder) {
        this.cardHolder = cardHolder;
    }

    public SberPayCardAccount getPayCardAccount() {
        return payCardAccount;
    }

    public void setPayCardAccount(SberPayCardAccount payCardAccount) {
        this.payCardAccount = payCardAccount;
    }

    public String getNumberCard() {
        return numberCard;
    }

    public void setNumberCard(String numberCard) {
        String number = numberCard.replace(" ", "");
        String regex = "[0-9]+";
        if (number.length() == 16 && number.matches(regex)) this.numberCard = numberCard;
        else System.out.println("Недопустимый номер карты");
    }

    public String getStatusCard() {
        return statusCard;
    }

    public void setStatusCard(String statusCard) {
        this.statusCard = statusCard;
    }

    // Оплата картой
    public void payByCard(float sumPay, String byProductOrService) {

    }

    // Оплата картой за рубежом
    public void payByCard(float sumPay, String byProductOrService, String country) {

    }

    // Перевести с карты на карту
    public void transferCard2Card(SberVisaGold toCard, float sumTransfer) {

    }

    // Перевести с карты на счет
    public void transferCard2Account(SberSavingsAccount toAccount, float sumTransfer) {

    }

    // внести наличные на карту
    public void depositingCash2Card(float sumDepositing) {
        // инициализировать транзакцию пополнения
        DepositingTransaction depositingTransaction = new DepositingTransaction();
        depositingTransaction.setLocalDateTime(LocalDateTime.now());
        depositingTransaction.setToCard((SberVisaGold) this);
        depositingTransaction.setSum(sumDepositing);
        depositingTransaction.setCurrencySymbol(payCardAccount.getCurrencySymbol());
        depositingTransaction.setTypeOperation("Внесение наличных");

        // запрсоить разрешение банка на проведение операции с проверкой статуса карты
        String authorization = bank.authorization(this);
        // извлекаем массив строк разделяя их символом @
        String[] authorizationData = authorization.split("@");
        // извлекаем код авторазации
        String authorizationCode = authorizationData[0];
        // вносим в транзакцию код авторизации
        depositingTransaction.setAuthorizationCode(authorizationCode);
        // извлекаем сообщение из авторизации
        String authorizationMessage = authorizationData[1];
        // извлекаем статус из сообщения авторизации
        String authorizationStatus = authorizationMessage.substring(0, authorizationMessage.indexOf(":"));

        // если разрешение получено, то выполняем пополнение
        if (authorizationStatus.equalsIgnoreCase("Succsess")) {
            boolean topUpStatus = payCardAccount.topUp(sumDepositing);
            if (topUpStatus) {
                // внести в транзакцию статус пополнения
                depositingTransaction.setStatusOperation("Внесение наличных прошло успешно");
            } else depositingTransaction.setStatusOperation("Внесение наличных не прошло");
        } else {
            // иначе выводим сообщение о статусе авторизации, чтобы понимать что пошло не так
            String authorizationStatusMessage = authorizationMessage.substring(authorizationMessage.indexOf(":"));
            depositingTransaction.setStatusOperation(authorizationStatusMessage);
        }

        // внести в транзакцию баланс карты после пополнения
        depositingTransaction.setBalance(getPayCardAccount().getBalance());

        // TODO: добавить и привязать тразакцию пополнения к счету карты зачисления
        payCardAccount.addDepositingTransaction(depositingTransaction);
    }

    // Пополнить карту с карты
    public void depositingCardFromCard(SberVisaGold fromCard, float sumDepositing) {

    }

    // Пополнить карту со счета
    public void depositingCardFromAccount(SberSavingsAccount fromAccount, float sumDepositing) {

    }

    // Вывести транзакции по счету карты
    public void displayCardTransactions () {

    }

/*
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

         TODO: перевести сумму на счет магазина

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
*/

}



