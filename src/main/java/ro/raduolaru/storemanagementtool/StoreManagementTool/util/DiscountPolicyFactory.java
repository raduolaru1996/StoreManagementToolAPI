package ro.raduolaru.storemanagementtool.StoreManagementTool.util;

import ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount.DiscountPolicy;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount.LargeDiscount;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount.NoDiscount;
import ro.raduolaru.storemanagementtool.StoreManagementTool.model.discount.SmallDiscount;

public class DiscountPolicyFactory {
    public static DiscountPolicy discountForPrice(Double price) {
        if (price < 50.0){
            return new NoDiscount();
        } else if (price < 100.0) {
            return new SmallDiscount();
        } else {
            return new LargeDiscount();
        }
    }
}
