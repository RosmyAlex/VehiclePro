package com.demovehiclepro.data.model.decorator;

import com.demovehiclepro.data.model.VehiclePaymentPlan;

public class OneOffPlan extends VehiclePaymentPlanDecorator{

    public OneOffPlan(VehiclePaymentPlan vehiclePaymentPlan) {
        super(vehiclePaymentPlan);
    }

    @Override
    public Double calculatePrice() {
        return super.calculatePrice();
    }

}
