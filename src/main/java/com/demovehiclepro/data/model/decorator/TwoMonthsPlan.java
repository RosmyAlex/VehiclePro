package com.demovehiclepro.data.model.decorator;

import com.demovehiclepro.data.model.VehiclePaymentPlan;

public class TwoMonthsPlan extends VehiclePaymentPlanDecorator{

    public TwoMonthsPlan(VehiclePaymentPlan vehiclePaymentPlan) {
        super(vehiclePaymentPlan);
    }

    @Override
    public Double calculatePrice() {
        return super.calculatePrice()/2;
    }
}
