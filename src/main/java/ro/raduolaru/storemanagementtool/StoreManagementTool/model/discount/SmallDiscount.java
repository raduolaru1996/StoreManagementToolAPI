package ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount;

public final class SmallDiscount implements DiscountPolicy{
    @Override
    public Double applyDiscount(Double price) {
        return price * 0.95;
    }

    @Override
    public String discountInfo() {
        return "5% discount";
    }
}
