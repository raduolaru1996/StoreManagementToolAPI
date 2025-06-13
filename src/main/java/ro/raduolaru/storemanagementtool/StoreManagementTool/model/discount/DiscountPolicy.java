package ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount;

public sealed interface DiscountPolicy permits NoDiscount, SmallDiscount, LargeDiscount {
    Double applyDiscount(Double price);
    String discountInfo();
}
