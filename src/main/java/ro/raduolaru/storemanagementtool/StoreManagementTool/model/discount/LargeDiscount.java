package ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount;

public final class LargeDiscount implements DiscountPolicy {
    @Override
    public Double applyDiscount(Double price) {
        return price * 0.9;
    }

    @Override
    public String discountInfo() {
        return "10% discount";
    }
}
