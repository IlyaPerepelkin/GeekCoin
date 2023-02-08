package ClientProfile;

import Account.SberSavingsAccount;
import Card.SberVisaGold;

public class SberPhysicalPersonProfile extends PhysicalPersonProfile {

    private float limitTransfersToClientSberWithoutCommissionMonthInRUB;

    private float totalTransfersToClientSberWithoutCommissionMonthInRUB;


    public float getLimitTransfersToClientSberWithoutCommissionMonthInRUB() {
        return limitTransfersToClientSberWithoutCommissionMonthInRUB;
    }

    public void setLimitTransfersToClientSberWithoutCommissionMonthInRUB(float limitTransfersToClientSberWithoutCommissionMonthInRUB) {
        this.limitTransfersToClientSberWithoutCommissionMonthInRUB = limitTransfersToClientSberWithoutCommissionMonthInRUB;
    }

    public float getTotalTransfersToClientSberWithoutCommissionMonthInRUB() {
        return totalTransfersToClientSberWithoutCommissionMonthInRUB;
    }

    public void setTotalTransfersToClientSberWithoutCommissionMonthInRUB(float totalTransfersToClientSberWithoutCommissionMonthInRUB) {
        this.totalTransfersToClientSberWithoutCommissionMonthInRUB = totalTransfersToClientSberWithoutCommissionMonthInRUB;
    }


    //  проверить не превышен ли лимит по переводам клиентам Сбера в месяц
    public boolean exceededLimitTransfersToClientSberWithoutCommissionMonthInRUB(float sumTransfer) {
        if (totalTransfersToClientSberWithoutCommissionMonthInRUB + sumTransfer > limitTransfersToClientSberWithoutCommissionMonthInRUB) return true;
        return false;
    }

    // Обнулять сумму переводов клиентам Сбера каждое первое число месяца
    public void zeroingTotalTransfersToClientSberWithoutCommissionMonthInRUB() {
        // TODO: если 00:00 1-е число месяца, то TotalTransfersToClientSberWithoutCommissionMonthInRUB = 0;
    }

    // Прибавить сумму перевода на карту к общей сумме всех переводов на карту клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
    public void updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(SberVisaGold toCard, float sumTransfer) {
        boolean isMyCard = isClientCard(toCard);
        boolean isCardMyBank = getBank().isCardBank(toCard);
        // если карта не моя, но моего банка, то есть клиент Сбера, то суммируем
        if (!isMyCard && isCardMyBank) totalTransfersToClientSberWithoutCommissionMonthInRUB += sumTransfer;
    }

    // Прибавить сумму перевода на счет к общей сумме всех переводов на счета клиентам Сбера без комиссии за месяц, чтобы контролировать лимиты
    public void updateTotalTransfersToClientSberWithoutCommissionMonthInRUB(SberSavingsAccount toAccount, float sumTransfer) {
        boolean isMyAccount = isClientAccount(toAccount);
        boolean isAccountMyBank = getBank().isAccountBank(toAccount);
        // если счет не мой, но моего банка, то есть клиент Сбера, то суммируем
        if (!isMyAccount && isAccountMyBank) totalTransfersToClientSberWithoutCommissionMonthInRUB += sumTransfer;
    }

    @Override
    // Вывод всех операций по всем картам и счетам профиля физического лица в Сбере
    public void displayProfileTransactions() {
        // дополним метод уникальной информацией, присуще только Сберу
        System.out.println("Все операции по картам и счетам клиента " + getPhysicalPerson().getFirstName() + " " + getPhysicalPerson().getLastName() +
                " в " + getBank().getBankName() + "Банке");

        System.out.println("Переводы клиентам " + getBank().getBankName() +
                " без комиссии за текущий месяц: " + getTotalTransfersToClientSberWithoutCommissionMonthInRUB() + "₽ Доступный лимит: " +
                (getLimitTransfersToClientSberWithoutCommissionMonthInRUB() - getTotalTransfersToClientSberWithoutCommissionMonthInRUB()) + "₽ из " +
                getLimitTransfersToClientSberWithoutCommissionMonthInRUB() + "₽");

        // и вызываем родительскую верисю метода
        super.displayProfileTransactions();
    }
}
