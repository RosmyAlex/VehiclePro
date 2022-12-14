package com.demovehiclepro.service.vehicle;

import com.demovehiclepro.data.enums.PaymentPlan;
import com.demovehiclepro.data.model.Vehicle;
import com.demovehiclepro.data.model.VehiclePaymentPlan;
import com.demovehiclepro.data.repository.VehiclePaymentPlanRepository;
import com.demovehiclepro.data.repository.VehicleRepository;
import com.demovehiclepro.dtos.NewVehicleDTO;
import com.demovehiclepro.exceptions.VehicleException;
import com.demovehiclepro.service.vehicle.paymentStrategy.FourMonthsPaymentPlan;
import com.demovehiclepro.service.vehicle.paymentStrategy.OneOffPaymentPlan;
import com.demovehiclepro.service.vehicle.paymentStrategy.PaymentContext;
import com.demovehiclepro.service.vehicle.paymentStrategy.SixMonthsPaymentPlan;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class VehicleServiceImpl implements VehicleService {

    final
    VehicleRepository vehicleRepository;

    @Autowired
    VehiclePaymentPlanRepository vehiclePaymentPlanRepository;

    public VehicleServiceImpl(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @Override
    public Vehicle addVehicle(NewVehicleDTO newVehicleDTO) {
        Optional<Vehicle> optionalVehicle
                = vehicleRepository.findByModelAndColor(newVehicleDTO.getModel(), newVehicleDTO.getColor());
        if (optionalVehicle.isPresent()) {
            throw new VehicleException("Create Failed! Model already exists");
        }

        Vehicle newVehicle = new Vehicle();
        newVehicle.setModel(newVehicleDTO.getModel());
        newVehicle.setColor(newVehicleDTO.getColor());
        newVehicle.setPrice(newVehicleDTO.getPrice());
        newVehicle.setCapacity(newVehicleDTO.getCapacity());

        Set<PaymentPlan> paymentPlans = newVehicleDTO.getPaymentPlan();

        Set<VehiclePaymentPlan> processedPaymentPlans =  processPaymentPlan(newVehicleDTO, paymentPlans);

        newVehicle.setVehiclePaymentPlans(processedPaymentPlans);

        vehicleRepository.save(newVehicle);

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(newVehicleDTO);
            VehicleListDisplay.VehicleData vehicleData = VehicleListDisplay.VehicleData.getInstance();
            vehicleData.notifyUpdate(new TextMessage(json));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return newVehicle;
    }

    private Set<VehiclePaymentPlan> processPaymentPlan(NewVehicleDTO newVehicleDTO, Set<PaymentPlan> paymentPlans){
        Set<VehiclePaymentPlan> vehiclePaymentPlans = new HashSet<>();

        paymentPlans.forEach(paymentPlan -> {
            PaymentContext paymentContext = null;
            Double priceToPay = null;
            VehiclePaymentPlan vehiclePaymentPlan = new VehiclePaymentPlan(newVehicleDTO.getPrice());

            switch (paymentPlan){
                case ONE_OFF:
                    vehiclePaymentPlan.setPaymentPlan(PaymentPlan.ONE_OFF);
                    paymentContext = new PaymentContext(new OneOffPaymentPlan());

                    break;

                case FOUR_MONTHS_INSTALLMENT:
                    vehiclePaymentPlan.setPaymentPlan(PaymentPlan.FOUR_MONTHS_INSTALLMENT);
                    vehiclePaymentPlan.setRate(0.10);
                    paymentContext = new PaymentContext(new FourMonthsPaymentPlan());

                    break;

                case SIX_MONTHS_INSTALLMENT:
                    vehiclePaymentPlan.setPaymentPlan(PaymentPlan.SIX_MONTHS_INSTALLMENT);
                    vehiclePaymentPlan.setRate(0.12);
                    paymentContext = new PaymentContext(new SixMonthsPaymentPlan());

                    break;
            }

            priceToPay = paymentContext.calculatePrice(vehiclePaymentPlan);
            vehiclePaymentPlan.setPriceToPay(priceToPay);
            VehiclePaymentPlan savedPaymentPlan = vehiclePaymentPlanRepository.save(vehiclePaymentPlan);

            vehiclePaymentPlans.add(savedPaymentPlan);
        });

        return vehiclePaymentPlans;
    }

    @Override
    public List<Vehicle> getVehicles() {
        return vehicleRepository.findAll();
    }
}
