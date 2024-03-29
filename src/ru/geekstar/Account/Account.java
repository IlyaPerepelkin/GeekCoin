package ru.geekstar.Account;

import ru.geekstar.Bank.Bank;
import ru.geekstar.Card.Card;
import ru.geekstar.ClientProfile.PhysicalPersonProfile;
import ru.geekstar.Currency;
import ru.geekstar.Transaction.DepositingTransaction;
import ru.geekstar.Transaction.TransferTransaction;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Account {

    private Bank bank;

    private PhysicalPersonProfile accountHolder;

    private String numberAccount;

    private String currencyCode;

    private String currencySymbol;

    private float balance;

    private ArrayList<TransferTransaction> transferTransactions = new ArrayList<>();

    private ArrayList<DepositingTransaction> depositingTransactions = new ArrayList<>();

    public static final String  WRITE_OFF_SUCCESSFUL = "Списание прошло успешно";
    public static final String REFILL_COMPLETED_SUCCESSFULLY = "Пополнение прошло успешно";
    public static final String TRANSFER_FAILED = "Перевод не прошёл";
    public static final String WRITE_OFF_FAILED = "Списание не прошло";
    public static final String INSUFFICIENT_FUNDS = "Недостаточно средств";
    public static final String TRANSFER_SUCCESSFUL = "Перевод прошёл успешно";
    public static final String LIMIT_AMOUNT_OF_TRANSACTIONS_PER_DAY_EXCEEDED = "Превышен лимит по сумме операций в сутки";

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public PhysicalPersonProfile getAccountHolder() {
        return accountHolder;
    }

    public void setAccountHolder(PhysicalPersonProfile accountHolder) {
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

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencyCode) {
        if (currencyCode.equals(Currency.RUB.toString()) || currencyCode.equals(Currency.USD.toString())
                || currencyCode.equals(Currency.EUR.toString())) this.currencySymbol = Bank.getCurrencySymbol(currencyCode);
        else System.out.println("Недопустимый код валюты: " + currencyCode);
    }

    public float getBalance() {
        return balance;
    }

    public synchronized boolean setBalance(float balance) {
        if (balance >= 0) {
            this.balance = balance;
            return true;
        } else {
            System.out.println("Отрицательное недопустимое значение баланса: " + balance);
            return false;
        }
    }

    public ArrayList<TransferTransaction> getTransferTransactions() {
        return transferTransactions;
    }

    public void setTransferTransactions(ArrayList<TransferTransaction> transferTransactions) {
        this.transferTransactions = transferTransactions;
    }

    public ArrayList<DepositingTransaction> getDepositingTransactions() {
        return depositingTransactions;
    }

    public void setDepositingTransactions(ArrayList<DepositingTransaction> depositingTransactions) {
        this.depositingTransactions = depositingTransactions;
    }


    public Account(PhysicalPersonProfile accountHolder, String currencyCode) {
        this.accountHolder = accountHolder;
        this.currencyCode = currencyCode;
        this.bank = accountHolder.getBank();
        this.numberAccount = Bank.generateNumberAccount();
        setCurrencySymbol(currencyCode);
    }
    
    // Перевести со счета на карту
    public void transferAccount2Card(Card toCard, float sumTransfer) {
        // инициализировать транзакцию перевода
        TransferTransaction transferTransaction = new TransferTransaction(this, toCard, "Перевод на карту", sumTransfer, currencySymbol);

        // определяем валюту счёта списания
        String fromCurrencyCode = getCurrencyCode();

        // рассчитать комиссию за перевод на свою или чужую карту моего или другого банка
        float commission = bank.getCommission(accountHolder, fromCurrencyCode, sumTransfer, toCard);

        // внести в транзакцию перевода данные о комиссии
        transferTransaction.setCommission(commission);

        // проверить баланс счёта и достаточно ли денег
        boolean checkBalance = checkBalance(sumTransfer + commission);
        if (checkBalance) {
            // проверить не превышен ли лимит по оплатам и переводам в сутки
            boolean exceededLimitPaymentsTransfersDay = accountHolder.exceededLimitPaymentsTransfersDay(sumTransfer, fromCurrencyCode);
            if (!exceededLimitPaymentsTransfersDay) {
                // если не превышен, то выполнить списание суммы и комиссии со счёта
                boolean withdrawalStatus = withdrawal(sumTransfer + commission);
                if (withdrawalStatus) {
                    // внести в транзакцию перевода статус списания
                    transferTransaction.setStatusOperation(WRITE_OFF_SUCCESSFUL);

                    // определяем валюту карты зачисления
                    String toCurrencyCode = toCard.getPayCardAccount().getCurrencyCode();

                    String depositingTypeOperation = "Пополнение " + (!fromCurrencyCode.equals(toCurrencyCode) ? sumTransfer + " " + currencySymbol : "") + " со счёта";

                    // если валюты списания и зачисления не совпадают, то конвертировать сумму перевода в валюту карты зачисления по курсу банка
                    sumTransfer = bank.convertToCurrencyExchangeRateBank(sumTransfer, fromCurrencyCode, toCurrencyCode);

                    // инициализировать транзакцию пополнения
                    DepositingTransaction depositingTransaction = new DepositingTransaction(this, toCard, depositingTypeOperation, sumTransfer, toCard.getPayCardAccount().getCurrencySymbol());

                    // зачислить на карту
                    boolean topUpStatus = toCard.getPayCardAccount().topUp(sumTransfer);
                    if (topUpStatus) {
                        // внести в транзакцию пополнения статус пополнения
                        depositingTransaction.setStatusOperation(REFILL_COMPLETED_SUCCESSFULLY);

                        // внести в транзакцию баланс карты после пополнения
                        depositingTransaction.setBalance(toCard.getPayCardAccount().getBalance());

                        // добавить и привязать транзакцию пополнения к счёту карты зачисления
                        toCard.getPayCardAccount().getDepositingTransactions().add(depositingTransaction);

                        // внести в транзакцию статус перевода
                        transferTransaction.setStatusOperation(TRANSFER_SUCCESSFUL);

                        // прибавить сумму перевода к общей сумме совершённых оплат и переводов за сутки, чтобы контролировать лимиты
                        getAccountHolder().updateTotalPaymentsTransfersDay(sumTransfer, fromCurrencyCode, toCard);

                        // TODO: и перевести комиссию на счёт банка

                    } else transferTransaction.setStatusOperation(TRANSFER_FAILED);
                } else transferTransaction.setStatusOperation(WRITE_OFF_FAILED);
            } else transferTransaction.setStatusOperation(LIMIT_AMOUNT_OF_TRANSACTIONS_PER_DAY_EXCEEDED);
        } else transferTransaction.setStatusOperation(INSUFFICIENT_FUNDS);

        // внести в транзакцию перевода баланс карты после списания
        transferTransaction.setBalance(getBalance());

        // добавить и привязать транзакцию перевода к счёту списания
        transferTransactions.add(transferTransaction);
    }

    public void transferAccount2Account(Account toAccount, float sumTransfer) {
        // инициализировать транзакцию перевода
        TransferTransaction transferTransaction = new TransferTransaction(this, toAccount, "Перевод на счёт", sumTransfer, currencySymbol);

        // определяем валюту счёта списания
        String fromCurrencyCode = getCurrencyCode();

        // рассчитать комиссию за перевод на свой или чужой счёт моего или другого банка
        float commission = bank.getCommission(accountHolder, fromCurrencyCode, sumTransfer, toAccount);
        // внести в транзакцию данные о комиссии
        transferTransaction.setCommission(commission);

        // проверяем баланс счёта и хватит ли денег
        boolean checkBalance = checkBalance(sumTransfer + commission);
        if (checkBalance) {
            // проверить не превышен ли лимит по оплатам и переводам в сутки
            boolean exceededLimitPaymentsTransfersDay = accountHolder.exceededLimitPaymentsTransfersDay(sumTransfer, fromCurrencyCode);
            if (!exceededLimitPaymentsTransfersDay) {
                // если не превышен, то выполнить списание суммы и комиссии со счёта
                boolean withdrawalStatus = withdrawal(sumTransfer + commission);
                if (withdrawalStatus) {
                    // внести в транзакцию статус списания
                    transferTransaction.setStatusOperation(WRITE_OFF_SUCCESSFUL);

                    // определяем валюту счёта зачисления
                    String toCurrencyCode = toAccount.getCurrencyCode();

                    String depositingTypeOperation = "Пополнение " + (!fromCurrencyCode.equals(toCurrencyCode) ? sumTransfer + " " + currencySymbol : "") + " со счёта";

                    // если валюты списания и зачисления не совпадают, то конвертировать сумму перевода в валюту счёта зачисления по курсу банка
                    sumTransfer = bank.convertToCurrencyExchangeRateBank(sumTransfer, fromCurrencyCode, toCurrencyCode);

                    // инициализировать транзакцию пополнения
                    DepositingTransaction depositingTransaction = new DepositingTransaction(this, toAccount, depositingTypeOperation, sumTransfer, toAccount.getCurrencySymbol());

                    // зачислить на счёт
                    boolean topUpStatus = toAccount.topUp(sumTransfer);
                    if (topUpStatus) {
                        // внести в транзакцию статус пополнения
                        depositingTransaction.setStatusOperation(REFILL_COMPLETED_SUCCESSFULLY);

                        // внести в транзакцию баланс счёта после пополнения
                        depositingTransaction.setBalance(toAccount.getBalance());

                        // добавить и привязать транзакцию пополнения к счёту зачисления
                        toAccount.getDepositingTransactions().add(depositingTransaction);

                        // внести в транзакцию статус перевода
                        transferTransaction.setStatusOperation(TRANSFER_SUCCESSFUL);

                        // прибавить сумму перевода к общей сумме совершённых оплат и переводов за сутки, чтобы контролировать лимиты
                        getAccountHolder().updateTotalPaymentsTransfersDay(sumTransfer, fromCurrencyCode, toAccount);

                        // TODO: и перевести комиссию на счёт банка

                    } else transferTransaction.setStatusOperation(TRANSFER_FAILED);
                } else transferTransaction.setStatusOperation(WRITE_OFF_FAILED);
            } else transferTransaction.setStatusOperation(LIMIT_AMOUNT_OF_TRANSACTIONS_PER_DAY_EXCEEDED);
        } else transferTransaction.setStatusOperation(INSUFFICIENT_FUNDS);

        // внести в транзакцию баланс карты после списания
        transferTransaction.setBalance(getBalance());

        // добавить и привязать транзакцию перевода к счёту списания
        transferTransactions.add(transferTransaction);
    }

    // Пополнить счет с карты
    public void depositingAccountFromCard(Card fromCard, float sumDepositing) {
        // то есть перевести с карты на счет
        fromCard.transferCard2Account(this, sumDepositing);
    }

    public void depositingAccountFromAccount(Account fromAccount, float sumDepositing) {
        fromAccount.transferAccount2Account( this, sumDepositing);
    }

    // Пополнить баланс
    public synchronized final boolean topUp(float sum) {
        setBalance(balance + sum);
        return true;
    }

    // Проверить достаточно ли денег на балансе
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
    public synchronized final boolean writeOff(float sum) {
        return setBalance(balance - sum);
    }

    // Вывести транзакции по счету
    public void displayAccountTransactions() {
        // сформировать общий массив транзакций перевода и пополнения в человеко читаемом формате
        String[] allTransferDepositingTransactions = getAllAccountTransactions();

        // отсортировать транзакции по дате
        Arrays.sort(allTransferDepositingTransactions);

        // вывести все транзакции
        for (int idTransaction = 0; idTransaction < allTransferDepositingTransactions.length; idTransaction++) {
            System.out.println("#" + (idTransaction + 1) + " " + allTransferDepositingTransactions[idTransaction]);
        }

    }

    public String[] getAllAccountTransactions() {
        // объявить массив транзакций перевода и пополнения по счету длиной равной общему количеству транзакций
        String[] allTransferDepositingTransactions = new String[transferTransactions.size() + depositingTransactions.size()];

        int countTransferDepositingTransactions = 0;
        // перебрать транзакции перевода и пополнения и добавить их в общий массив в человеко читаемом формате
        for (int idTransaction = 0; idTransaction < transferTransactions.size(); idTransaction++) {
            allTransferDepositingTransactions[countTransferDepositingTransactions++] = transferTransactions.get(idTransaction).getStringTransaction();
        }
        for (int idTransaction = 0; idTransaction < depositingTransactions.size(); idTransaction++) {
            allTransferDepositingTransactions[countTransferDepositingTransactions++] = depositingTransactions.get(idTransaction).getStringTransaction();
        }

        return allTransferDepositingTransactions;

    }

}
