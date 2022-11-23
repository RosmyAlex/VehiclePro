package com.demovehiclepro.data.model.decorator;

import com.demovehiclepro.data.model.VehiclePayment;
import com.demovehiclepro.data.model.VehiclePaymentPlan;


public class VehiclePaymentPlanDecorator extends VehiclePaymentPlan {
    private VehiclePaymentPlan vehiclePaymentPlan;

    public VehiclePaymentPlanDecorator(VehiclePaymentPlan vehiclePaymentPlan) {
        super(vehiclePaymentPlan.getId(), vehiclePaymentPlan.getPrice());
        this.vehiclePaymentPlan = vehiclePaymentPlan;
    }

    @Override
    public Double calculatePrice() {
        return vehiclePaymentPlan.getPrice();
    }
}
