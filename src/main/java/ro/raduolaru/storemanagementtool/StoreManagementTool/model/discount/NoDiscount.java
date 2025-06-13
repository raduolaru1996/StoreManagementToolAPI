package ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount;

public final class NoDiscount implements DiscountPolicy{
    @Override
    public Double applyDiscount(Double price) {
        return price;
    }

    @Override
    public String discountInfo() {
        return "No discount";
    }
}
