package com.demovehiclepro.data.model.decorator;

import com.demovehiclepro.data.model.VehiclePaymentPlan;

public class FourMonthsPlan extends VehiclePaymentPlanDecorator{

    public FourMonthsPlan(VehiclePaymentPlan vehiclePaymentPlan) {
        super(vehiclePaymentPlan);
    }

    @Override
    public Double calculatePrice() {
        return super.calculatePrice()/4;
    }
}
