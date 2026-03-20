package com.app.quantitymeasurement.util;

import java.io.InputStream;
import java.util.Properties;

public class ApplicationConfig {
    private static final String DEFAULT_ENV = "dev";
    private static final String PROPERTIES_FILE = "application.properties";

    private static ApplicationConfig instance;

    private final Properties properties = new Properties();
    private final String environment;

    private ApplicationConfig() {
        loadProperties();
        this.environment = resolveEnvironment();
    }

    public static synchronized ApplicationConfig getInstance() {
        if (instance == null) {
            instance = new ApplicationConfig();
        }
        return instance;
    }

    private void loadProperties() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            if (input != null) {
                properties.load(input);
            }
        } catch (Exception ignored) {
            // Fallback to defaults when properties file is missing.
        }
    }

    private String resolveEnvironment() {
        String env = System.getProperty("app.env");
        if (env == null || env.isBlank()) {
            env = System.getProperty("env");
        }
        if (env == null || env.isBlank()) {
            env = properties.getProperty("app.env", DEFAULT_ENV);
        }
        return env.trim();
    }

    public String getEnvironment() {
        return environment;
    }

    public String getRepositoryType() {
        return getProperty("repository.type", "database");
    }

    public String getDbUrl() {
        return getEnvProperty("db.url", "jdbc:h2:mem:quantity_db;DB_CLOSE_DELAY=-1;MODE=MySQL");
    }

    public String getDbUser() {
        return getEnvProperty("db.user", "sa");
    }

    public String getDbPassword() {
        return getEnvProperty("db.password", "");
    }

    public String getSchemaResourcePath() {
        return getEnvProperty("db.schema.path", "db/schema.sql");
    }

    public int getPoolMaxSize() {
        return getIntProperty("db.pool.maxSize", 8);
    }

    public int getPoolMinIdle() {
        return getIntProperty("db.pool.minIdle", 2);
    }

    public long getPoolConnectionTimeoutMs() {
        return getLongProperty("db.pool.connectionTimeoutMs", 3000L);
    }

    private String getProperty(String key, String defaultValue) {
        String system = System.getProperty(key);
        if (system != null && !system.isBlank()) {
            return system;
        }
        return properties.getProperty(key, defaultValue);
    }

    private String getEnvProperty(String key, String defaultValue) {
        String envKey = environment + "." + key;
        String system = System.getProperty(envKey);
        if (system != null && !system.isBlank()) {
            return system;
        }
        String value = properties.getProperty(envKey);
        if (value != null && !value.isBlank()) {
            return value;
        }
        return getProperty(key, defaultValue);
    }

    private int getIntProperty(String key, int defaultValue) {
        String value = getProperty(key, String.valueOf(defaultValue));
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }

    private long getLongProperty(String key, long defaultValue) {
        String value = getProperty(key, String.valueOf(defaultValue));
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException ex) {
            return defaultValue;
        }
    }
}
