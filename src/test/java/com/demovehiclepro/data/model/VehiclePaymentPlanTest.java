package com.demovehiclepro.data.model;

import com.demovehiclepro.data.model.decorator.TwoMonthsPlan;
import org.junit.jupiter.api.Test;

class VehiclePaymentPlanTest {

    @Test
    void createThreeMonthsPlan(){
        VehiclePaymentPlan vehiclePaymentPlan = new VehiclePaymentPlan(12,67500.00);

        VehiclePaymentPlan fourMonthsPlan = new TwoMonthsPlan(new TwoMonthsPlan(vehiclePaymentPlan));

        vehiclePaymentPlan.setPriceToPay(fourMonthsPlan.calculatePrice());
    }

}