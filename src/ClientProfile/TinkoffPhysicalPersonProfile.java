package ClientProfile;

public class TinkoffPhysicalPersonProfile extends PhysicalPersonProfile {

    private float percentBonusOfSumPay;

    private float limitTransfersToClientTinkoffWithoutCommissionMonthInRUB;


    public float getPercentBonusOfSumPay() {
        return percentBonusOfSumPay;
    }

    public void setPercentBonusOfSumPay(float percentBonusOfSumPay) {
        this.percentBonusOfSumPay = percentBonusOfSumPay;
    }

    public float getLimitTransfersToClientTinkoffWithoutCommissionMonthInRUB() {
        return limitTransfersToClientTinkoffWithoutCommissionMonthInRUB;
    }

    public void setLimitTransfersToClientTinkoffWithoutCommissionMonthInRUB(float limitTransfersToClientSberWithoutCommissionMonthInRUB) {
        this.limitTransfersToClientTinkoffWithoutCommissionMonthInRUB = limitTransfersToClientSberWithoutCommissionMonthInRUB;
    }

}
