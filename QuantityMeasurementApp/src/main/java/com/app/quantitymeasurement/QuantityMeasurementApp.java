package com.app.quantitymeasurement;

import com.app.quantitymeasurement.controller.QuantityMeasurementController;
import com.app.quantitymeasurement.dto.QuantityDTO;
import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.services.IQuantityMeasurementService;
import com.app.quantitymeasurement.services.QuantityMeasurementServiceImpl;
import com.app.quantitymeasurement.util.ApplicationConfig;
import com.app.quantitymeasurement.util.RepositoryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class QuantityMeasurementApp {
    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementApp.class);

    private final QuantityMeasurementController controller;

    public QuantityMeasurementApp(IQuantityMeasurementRepository repository) {
        IQuantityMeasurementService service = new QuantityMeasurementServiceImpl(repository);
        this.controller = new QuantityMeasurementController(service);
        logger.info("QuantityMeasurementApp initialized with repository: {}", repository.getClass().getSimpleName());
    }

    public static void main(String[] args) {
        ApplicationConfig config = ApplicationConfig.getInstance();
        IQuantityMeasurementRepository repository = RepositoryFactory.createRepository(config);
        QuantityMeasurementApp app = new QuantityMeasurementApp(repository);

        try {
            app.runDemo();
        } finally {
            app.reportMeasurements();
            app.deleteAllMeasurements();
            app.closeResources();
        }
    }

    private void runDemo() {
        QuantityDTO q1 = new QuantityDTO(10, "FEET");
        QuantityDTO q2 = new QuantityDTO(120, "INCHES");

        boolean comparison = controller.performComparison(q1, q2);
        logger.info("Comparison Result: {}", comparison);

        QuantityDTO added = controller.performAddition(q1, q2);
        logger.info("Addition Result: {} {}", added.getValue(), added.getUnit());

        QuantityDTO converted = controller.convert(q1, "INCHES");
        logger.info("Conversion Result: {} {}", converted.getValue(), converted.getUnit());

        logger.info("Pool Statistics: {}", controller.getPoolStatistics());
    }

    private void reportMeasurements() {
        List<QuantityMeasurementEntity> all = controller.getAllMeasurements();
        logger.info("Total Measurements Stored: {}", all.size());
    }

    public void deleteAllMeasurements() {
        controller.deleteAllMeasurements();
        logger.info("All measurements deleted from repository");
    }

    public void closeResources() {
        controller.releaseResources();
        logger.info("Repository resources closed");
    }
}
