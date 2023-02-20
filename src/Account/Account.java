package Account;

import Bank.Sberbank;
import Card.SberVisaGold;
import ClientProfile.SberPhysicalPersonProfile;
import Transaction.DepositingTransaction;
import Transaction.TransferTransaction;

import java.time.LocalDateTime;
import java.util.Arrays;

public class Account {

    private Sberbank bank;

    private SberPhysicalPersonProfile accountHolder;

    private String numberAccount;

    private String currencyCode;

    private char currencySymbol;

    private float balance;

    private TransferTransaction[] transferTransactions = new TransferTransaction[50];

    private DepositingTransaction[] depositingTransactions = new DepositingTransaction[50];

    private int countTransferTransactions;

    private int countDepositingTransactions;


    public Sberbank getBank() {
        return bank;
    }

    public void setBank(Sberbank bank) {
        this.bank = bank;
    }

    public SberPhysicalPersonProfile getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(SberPhysicalPersonProfile accountHolder) {
        this.accountHolder = accountHolder;
    }

    public String getNumberAccount() {
        return numberAccount;
    }

    public void setNumberAccount(String numberAccount) {
        this.numberAccount = numberAccount;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public char getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencyCode) {
        if (currencyCode.equals("RUB")) this.currencySymbol = '₽';
        else if (currencyCode.equals("USD")) this.currencySymbol = '$';
        else if (currencyCode.equals("EUR")) this.currencySymbol = '€';
        else System.out.println("Недопустимый код валюты: " + currencyCode);
    }

    public float getBalance() {
        return balance;
    }

    public boolean setBalance(float balance) {
        if (balance >= 0) {
            this.balance = balance;
            return true;
        } else {
            System.out.println("Отрицательное недопустимое значение баланса: " + balance);
            return false;
        }
    }

    public TransferTransaction[] getTransferTransactions() {
        return transferTransactions;
    }

    public void setTransferTransactions(TransferTransaction[] transferTransactions) {
        this.transferTransactions = transferTransactions;
    }

    public DepositingTransaction[] getDepositingTransactions() {
        return depositingTransactions;
    }

    public void setDepositingTransactions(DepositingTransaction[] depositingTransactions) {
        this.depositingTransactions = depositingTransactions;
    }

    public int getCountTransferTransactions() {
        return countTransferTransactions;
    }

    public void setCountTransferTransactions(int countTransferTransactions) {
        this.countTransferTransactions = countTransferTransactions;
    }

    public int getCountDepositingTransactions() {
        return countDepositingTransactions;
    }

    public void setCountDepositingTransactions(int countDepositingTransactions) {
        this.countDepositingTransactions = countDepositingTransactions;
    }


    // Перевести со счета на карту
    public void transferAccount2Card(SberVisaGold toCard, float sumTransfer) {
        // инициализировать транзакцию перевода
        TransferTransaction transferTransaction = new TransferTransaction();
        transferTransaction.setLocalDateTime(LocalDateTime.now());
        transferTransaction.setFromAccount((SberSavingsAccount) this);
        transferTransaction.setToCard(toCard);
        transferTransaction.setSum(sumTransfer);
        transferTransaction.setCurrencySymbol(currencySymbol);
        transferTransaction.setTypeOperation("Перевод на карту");

        //определяем валюту счета списания
        String fromCurrencyCode = getCurrencyCode();

        // рассчитать комиссию за перевод на свою или чужую карту моего или другого банка
        float commission = bank.getCommission(accountHolder, fromCurrencyCode, sumTransfer, toCard);

        // внести в транзакцию перевода данные о комиссии
        transferTransaction.setCommission(commission);

        // проверить баланс счета и достаточно ли денег
        boolean checkBalance = checkBalance(sumTransfer + commission);
        if (checkBalance) {
            // проверить не превышен ли лимит по оплатам и переводам в сутки
            boolean exceededLimitPaymentsTransfersDay = accountHolder.exceededLimitPaymentsTransfersDay(sumTransfer, fromCurrencyCode);
            if (!exceededLimitPaymentsTransfersDay) {
                // если не превышен, то выполнить списание суммы и комиссии со счета
                boolean withdrawalStatus = withdrawal(sumTransfer + commission);
                if (withdrawalStatus) {
                    // внести в транзакцию перевода статус списания
                    transferTransaction.setStatusOperation("Списание прошло успешно");

                    // инициализировать транзакцию пополнения
                    DepositingTransaction depositingTransaction = new DepositingTransaction();
                    depositingTransaction.setLocalDateTime(LocalDateTime.now());
                    depositingTransaction.setFromAccount((SberSavingsAccount) this);
                    depositingTransaction.setToCard(toCard);
                    depositingTransaction.setTypeOperation("Перевод со счета");
                    depositingTransaction.setSum(sumTransfer);
                    depositingTransaction.setCurrencySymbol(toCard.getPayCardAccount().getCurrencySymbol());

                    // если валюты списания и зачисления не совпадают, то конвертировать сумму перевода в валюту карты зачисления по курсу банка
                    String toCurrencyCode = toCard.getPayCardAccount().getCurrencyCode();
                    // сравнить валюты списания и зачисления
                    if (!fromCurrencyCode.equals(toCurrencyCode)) {
                        // если они не равны, то вызвать метод convertToCurrencyExchangeRateBank
                        sumTransfer = bank.convertToCurrencyExchangeRateBank(sumTransfer, fromCurrencyCode, currencyCode);
                    }

                    // зачислить на карту
                    boolean topUpStatus = toCard.getPayCardAccount().topUp(sumTransfer);
                    if (topUpStatus) {
                        // внести в транзакцию пополнения статус пополнения
                        depositingTransaction.setStatusOperation("Пополнение прошло успешно");

                        // внести в транзакцию баланс карты после пополнения
                        depositingTransaction.setBalance(toCard.getPayCardAccount().getBalance());

                        // добавить и привязать транзакцию пополнения к счету карты зачисления
                        toCard.getPayCardAccount().addDepositingTransaction(depositingTransaction);

                        // внести в транзакцию статус перевода
                        transferTransaction.setStatusOperation("Перевод прошел успешно");

                        // прибавить сумму перевода к общей сумме совершенных оплат и переводов за сутки, чтобы контролировать лимиты
                        getAccountHolder().updateTotalPaymentsTransfersDay(sumTransfer, fromCurrencyCode, toCard);

                        // TODO: перевести комиссию на счет банка
                    } else transferTransaction.setStatusOperation("Перевод не прошел");
                } else transferTransaction.setStatusOperation("Списание не прошло");
            } else transferTransaction.setStatusOperation("Превышен лимит по сумме операций в сутки");
        } else transferTransaction.setStatusOperation("Недостаточно средств");

        // внести в транзакцию перевода баланс карты после списания
        transferTransaction.setBalance(getBalance());

        // добавить и привязать транзакцию перевода к счету списания
        addTransferTransaction(transferTransaction);
    }

    public void transferAccount2Account(SberSavingsAccount toAccount, float sumTransfer) {
        TransferTransaction transferTransaction = new TransferTransaction();
        transferTransaction.setLocalDateTime(LocalDateTime.now());
        transferTransaction.setFromAccount((SberSavingsAccount) this);
        transferTransaction.setToAccount(toAccount);
        transferTransaction.setSum(sumTransfer);
        transferTransaction.setCurrencySymbol(currencySymbol);
        transferTransaction.setTypeOperation("Перевод на счет");

        String fromCurrencyCode = getCurrencyCode();

        float commission = bank.getCommission(accountHolder, fromCurrencyCode, sumTransfer, toAccount);

        transferTransaction.setCommission(commission);

        boolean checkBalance = checkBalance(sumTransfer + commission);
        if (checkBalance) {
            boolean exceededLimitPaymentsTransfersDay = accountHolder.exceededLimitPaymentsTransfersDay(sumTransfer, fromCurrencyCode);
            if (!exceededLimitPaymentsTransfersDay) {
                boolean withdrawalStatus = withdrawal(sumTransfer + commission);
                if (withdrawalStatus) {
                    transferTransaction.setStatusOperation("Списание прошло успешно");


                    DepositingTransaction depositingTransaction = new DepositingTransaction();
                    depositingTransaction.setLocalDateTime(LocalDateTime.now());
                    depositingTransaction.setFromAccount((SberSavingsAccount) this);
                    depositingTransaction.setToAccount(toAccount);
                    depositingTransaction.setTypeOperation("Перевод со счета");
                    depositingTransaction.setSum(sumTransfer);
                    depositingTransaction.setCurrencySymbol(toAccount.getCurrencySymbol());

                    // если валюты списания и зачисления не совпадают, то конвертировать сумму перевода в валюту карты зачисления по курсу банка

                    String toCurrencyCode = toAccount.getCurrencyCode();
                    // сравнить валюты списания и зачисления
                    if (!fromCurrencyCode.equals(toCurrencyCode)) {
                        // если они не равны, то вызвать метод convertToCurrencyExchangeRateBank
                        sumTransfer = bank.convertToCurrencyExchangeRateBank(sumTransfer, fromCurrencyCode, currencyCode);
                    }

                    boolean topUpStatus = toAccount.topUp(sumTransfer);
                    if (topUpStatus) {

                        depositingTransaction.setStatusOperation("Пополнение прошло успешно");

                        depositingTransaction.setBalance(toAccount.getBalance());

                        toAccount.addDepositingTransaction(depositingTransaction);

                        transferTransaction.setStatusOperation("Перевод прошел успешно");

                        getAccountHolder().updateTotalPaymentsTransfersDay(sumTransfer, fromCurrencyCode, toAccount);

                        // TODO: перевести комиссию на счет банка
                    } else transferTransaction.setStatusOperation("Перевод не прошел");
                } else transferTransaction.setStatusOperation("Списание не прошло");
            } else transferTransaction.setStatusOperation("Превышен лимит по сумме операций в сутки");
        } else transferTransaction.setStatusOperation("Недостаточно средств");

        transferTransaction.setBalance(getBalance());

        addTransferTransaction(transferTransaction);

    }

    // Пополнить счет с карты
    public void depositingAccountFromCard(SberVisaGold fromCard, float sumDepositing) {
        // то есть перевести с карты на счет
        fromCard.transferCard2Account((SberSavingsAccount) this, sumDepositing);
    }

    public void depositingAccountFromAccount(SberSavingsAccount fromAccount, float sumDepositing) {
        fromAccount.transferAccount2Account((SberSavingsAccount) this, sumDepositing);
    }

    // пополнить баланс
    public boolean topUp(float sum) {
        setBalance(balance + sum);
        return true;
    }

    // добавить транзакцию о пополнении
    public void addDepositingTransaction(DepositingTransaction depositingTransaction) {
        depositingTransactions[countDepositingTransactions++] = depositingTransaction;
    }

    // добавить транзакцию перевода
    public void addTransferTransaction(TransferTransaction transferTransaction) {
        transferTransactions[countTransferTransactions++] = transferTransaction;
    }

    //  Проверить достаточно ли денег на балансе
    public boolean checkBalance(float sum) {
        if (sum <= balance) return true;
        return false;
    }

    // Списать средства в три попытки в случае сбоя
    public boolean withdrawal(float sum) {
        boolean writeOffStatus;
        byte errorTransaction = 0;
        do {
            writeOffStatus = writeOff(sum);
            if (!writeOffStatus) errorTransaction++;
        } while (!writeOffStatus && errorTransaction < 3);

        return writeOffStatus;
    }

    // списать со счета
    private boolean writeOff(float sum) {
        return setBalance(balance - sum);
    }

    // Вывести транзакции по счету
    public void displayAccountTransactions() {
        // сформировать общий массив транзакций перевода и пополнения в человеко читаемом формате
        String[] allTransferDepositingTransactions = getAllTransferDepositingTransactions();

        // отсортировать транзакции по дате
        Arrays.sort(allTransferDepositingTransactions);

        // вывести все транзакции
        for (int idTransaction = 0; idTransaction < allTransferDepositingTransactions.length; idTransaction++) {
            System.out.println("#" + (idTransaction + 1) + " " + allTransferDepositingTransactions[idTransaction]);
        }

    }

    public String[] getAllTransferDepositingTransactions() {
        // объявить массив транзакций перевода и пополнения по счету длиной равной общему количеству транзакций
        String[] allTransferDepositingTransactions = new String[countTransferTransactions + countDepositingTransactions];

        int countTransferDepositingTransactions = 0;
        // перебрать транзакции перевода и пополнения и добавить их в общий массив в человеко читаемом формате
        for (int idTransaction = 0; idTransaction < countTransferTransactions; idTransaction++) {
            allTransferDepositingTransactions[countTransferDepositingTransactions++] = transferTransactions[idTransaction].getStringTransaction();
        }
        for (int idTransaction = 0; idTransaction < countDepositingTransactions; idTransaction++) {
            allTransferDepositingTransactions[countTransferDepositingTransactions++] = depositingTransactions[idTransaction].getStringTransaction();
        }

        return allTransferDepositingTransactions;
    }

}
