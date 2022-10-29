package com.demovehiclepro.data.model;

import com.demovehiclepro.data.enums.Color;
import com.demovehiclepro.data.enums.PaymentPlan;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;
    private Color color;
    private Double price;
    private Integer capacity;
    private PaymentPlan paymentPlan;


}
