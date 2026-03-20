package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.repository.IQuantityMeasurementRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementCacheRepository;
import com.app.quantitymeasurement.repository.QuantityMeasurementDatabaseRepository;

public final class RepositoryFactory {
    private RepositoryFactory() {
    }

    public static IQuantityMeasurementRepository createRepository(ApplicationConfig config) {
        String type = config.getRepositoryType();
        if ("database".equalsIgnoreCase(type)) {
            return new QuantityMeasurementDatabaseRepository(config);
        }
        return QuantityMeasurementCacheRepository.getInstance();
    }
}
