package com.app.quantitymeasurement.repository;

import com.app.quantitymeasurement.entity.QuantityMeasurementEntity;
import com.app.quantitymeasurement.exception.DatabaseException;
import com.app.quantitymeasurement.util.ApplicationConfig;
import com.app.quantitymeasurement.util.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class QuantityMeasurementDatabaseRepository implements IQuantityMeasurementRepository {
    private static final Logger logger = LoggerFactory.getLogger(QuantityMeasurementDatabaseRepository.class);
    private static final Set<String> initializedSchemas = Collections.synchronizedSet(new HashSet<>());

    private final ApplicationConfig config;
    private final ConnectionPool pool;

    public QuantityMeasurementDatabaseRepository() {
        this(ApplicationConfig.getInstance());
    }

    public QuantityMeasurementDatabaseRepository(ApplicationConfig config) {
        this.config = config;
        this.pool = new ConnectionPool(config);
        initializeSchemaOnce();
        logger.info("Database repository initialized with env={}", config.getEnvironment());
    }

    @Override
    public void save(QuantityMeasurementEntity entity) {
        String insertEntitySql = "INSERT INTO quantity_measurement_entity "
                + "(operand1, operand2, unit1, unit2, operation, result, measurement_type) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        String insertHistorySql = "INSERT INTO quantity_measurement_history "
                + "(entity_id, operand1, operand2, unit1, unit2, operation, result, measurement_type) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        Connection connection = pool.acquire();
        try {
            connection.setAutoCommit(false);
            Long entityId = null;
            try (PreparedStatement statement = connection.prepareStatement(insertEntitySql, Statement.RETURN_GENERATED_KEYS)) {
                bindEntity(statement, entity, 1);
                statement.executeUpdate();
                try (ResultSet keys = statement.getGeneratedKeys()) {
                    if (keys.next()) {
                        entityId = keys.getLong(1);
                    }
                }
            }

            if (entityId != null) {
                try (PreparedStatement historyStatement = connection.prepareStatement(insertHistorySql)) {
                    historyStatement.setLong(1, entityId);
                    bindEntity(historyStatement, entity, 2);
                    historyStatement.executeUpdate();
                }
            }

            connection.commit();
        } catch (SQLException ex) {
            rollbackQuietly(connection);
            throw new DatabaseException("Failed to save quantity measurement", ex);
        } finally {
            restoreAutoCommit(connection);
            pool.release(connection);
        }
    }

    @Override
    public List<QuantityMeasurementEntity> getAllMeasurements() {
        String sql = "SELECT id, operand1, operand2, unit1, unit2, operation, result, measurement_type, created_at "
                + "FROM quantity_measurement_entity ORDER BY id";
        return queryEntities(sql, null);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByOperation(String operation) {
        String sql = "SELECT id, operand1, operand2, unit1, unit2, operation, result, measurement_type, created_at "
                + "FROM quantity_measurement_entity WHERE LOWER(operation) = LOWER(?) ORDER BY id";
        return queryEntities(sql, operation);
    }

    @Override
    public List<QuantityMeasurementEntity> getMeasurementsByType(String measurementType) {
        String sql = "SELECT id, operand1, operand2, unit1, unit2, operation, result, measurement_type, created_at "
                + "FROM quantity_measurement_entity WHERE LOWER(measurement_type) = LOWER(?) ORDER BY id";
        return queryEntities(sql, measurementType);
    }

    @Override
    public long getTotalCount() {
        String sql = "SELECT COUNT(*) FROM quantity_measurement_entity";
        Connection connection = pool.acquire();
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getLong(1);
            }
            return 0;
        } catch (SQLException ex) {
            throw new DatabaseException("Failed to count measurements", ex);
        } finally {
            pool.release(connection);
        }
    }

    @Override
    public void deleteAll() {
        Connection connection = pool.acquire();
        try {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate("DELETE FROM quantity_measurement_history");
                statement.executeUpdate("DELETE FROM quantity_measurement_entity");
            }
            connection.commit();
        } catch (SQLException ex) {
            rollbackQuietly(connection);
            throw new DatabaseException("Failed to delete measurements", ex);
        } finally {
            restoreAutoCommit(connection);
            pool.release(connection);
        }
    }

    @Override
    public String getPoolStatistics() {
        return pool.getStatistics();
    }

    @Override
    public void releaseResources() {
        pool.shutdown();
    }

    private void initializeSchemaOnce() {
        String schemaKey = config.getDbUrl() + "|" + config.getSchemaResourcePath();
        if (initializedSchemas.contains(schemaKey)) {
            return;
        }
        synchronized (QuantityMeasurementDatabaseRepository.class) {
            if (initializedSchemas.contains(schemaKey)) {
                return;
            }
            executeSchema(config.getSchemaResourcePath());
            initializedSchemas.add(schemaKey);
        }
    }

    private void executeSchema(String schemaPath) {
        List<String> statements = loadSchemaStatements(schemaPath);
        if (statements.isEmpty()) {
            logger.warn("Schema file {} did not contain any SQL statements", schemaPath);
            return;
        }
        Connection connection = pool.acquire();
        try {
            connection.setAutoCommit(false);
            for (String statement : statements) {
                try (Statement sqlStatement = connection.createStatement()) {
                    sqlStatement.execute(statement);
                }
            }
            connection.commit();
        } catch (SQLException ex) {
            rollbackQuietly(connection);
            throw new DatabaseException("Failed to initialize schema", ex);
        } finally {
            restoreAutoCommit(connection);
            pool.release(connection);
        }
    }

    private List<String> loadSchemaStatements(String schemaPath) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(schemaPath);
        if (inputStream == null) {
            return List.of();
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String sql = reader.lines()
                    .filter(line -> !line.trim().startsWith("--"))
                    .collect(Collectors.joining("\n"));
            String[] parts = sql.split(";");
            List<String> statements = new ArrayList<>();
            for (String part : parts) {
                String trimmed = part.trim();
                if (!trimmed.isEmpty()) {
                    statements.add(trimmed);
                }
            }
            return statements;
        } catch (Exception ex) {
            throw new DatabaseException("Failed to read schema file", ex);
        }
    }

    private void bindEntity(PreparedStatement statement, QuantityMeasurementEntity entity, int startIndex) throws SQLException {
        int index = startIndex;
        statement.setDouble(index++, entity.getOperand1());
        statement.setDouble(index++, entity.getOperand2());
        statement.setString(index++, entity.getUnit1());
        statement.setString(index++, entity.getUnit2());
        statement.setString(index++, entity.getOperation());
        statement.setDouble(index++, entity.getResult());
        statement.setString(index, entity.getMeasurementType());
    }

    private List<QuantityMeasurementEntity> queryEntities(String sql, String filterValue) {
        Connection connection = pool.acquire();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            if (filterValue != null) {
                statement.setString(1, filterValue);
            }
            try (ResultSet resultSet = statement.executeQuery()) {
                List<QuantityMeasurementEntity> results = new ArrayList<>();
                while (resultSet.next()) {
                    results.add(mapRow(resultSet));
                }
                return results;
            }
        } catch (SQLException ex) {
            throw new DatabaseException("Failed to query measurements", ex);
        } finally {
            pool.release(connection);
        }
    }

    private QuantityMeasurementEntity mapRow(ResultSet resultSet) throws SQLException {
        long id = resultSet.getLong("id");
        double operand1 = resultSet.getDouble("operand1");
        double operand2 = resultSet.getDouble("operand2");
        String unit1 = resultSet.getString("unit1");
        String unit2 = resultSet.getString("unit2");
        String operation = resultSet.getString("operation");
        double result = resultSet.getDouble("result");
        String measurementType = resultSet.getString("measurement_type");
        Timestamp createdAt = resultSet.getTimestamp("created_at");
        return new QuantityMeasurementEntity(id, operand1, operand2, unit1, unit2, operation, result, measurementType, createdAt);
    }

    private void rollbackQuietly(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.rollback();
        } catch (SQLException ignored) {
            // Ignore rollback failures.
        }
    }

    private void restoreAutoCommit(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            if (!connection.getAutoCommit()) {
                connection.setAutoCommit(true);
            }
        } catch (SQLException ignored) {
            // Ignore reset failures.
        }
    }
}
