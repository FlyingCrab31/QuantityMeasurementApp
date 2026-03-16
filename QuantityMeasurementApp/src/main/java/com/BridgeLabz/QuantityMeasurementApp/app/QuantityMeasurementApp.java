package com.bridgelabz.quantityMeasurementApp.app;

import com.bridgelabz.quantityMeasurementApp.controller.QuantityMeasurementController;
import com.bridgelabz.quantityMeasurementApp.dto.QuantityDTO;
import com.bridgelabz.quantityMeasurementApp.repository.IQuantityMeasurementRepository;
import com.bridgelabz.quantityMeasurementApp.repository.QuantityMeasurementCacheRepository;
import com.bridgelabz.quantityMeasurementApp.service.IQuantityMeasurementService;
import com.bridgelabz.quantityMeasurementApp.service.QuantityMeasurementServiceImpl;

public class QuantityMeasurementApp {
    public static void main(String[] args) {

        IQuantityMeasurementRepository repository =
                QuantityMeasurementCacheRepository.getInstance();

        IQuantityMeasurementService service =
                new QuantityMeasurementServiceImpl(repository);

        QuantityMeasurementController controller =
                new QuantityMeasurementController(service);

        QuantityDTO q1 = new QuantityDTO(10,"FEET");
        QuantityDTO q2 = new QuantityDTO(10,"FEET");

        boolean result = controller.performComparison(q1,q2);

        System.out.println("Comparison Result: " + result);
    }
}
