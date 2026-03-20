package com.app.quantitymeasurement.util;

import com.app.quantitymeasurement.exception.DatabaseException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPool {
    private final String url;
    private final String user;
    private final String password;
    private final int maxSize;
    private final int minIdle;
    private final long connectionTimeoutMs;

    private final BlockingQueue<Connection> idleConnections;
    private final Set<Connection> allConnections = Collections.synchronizedSet(new HashSet<>());
    private final AtomicInteger activeCount = new AtomicInteger(0);

    public ConnectionPool(ApplicationConfig config) {
        this.url = config.getDbUrl();
        this.user = config.getDbUser();
        this.password = config.getDbPassword();
        this.maxSize = config.getPoolMaxSize();
        this.minIdle = Math.min(config.getPoolMinIdle(), maxSize);
        this.connectionTimeoutMs = config.getPoolConnectionTimeoutMs();

        this.idleConnections = new ArrayBlockingQueue<>(maxSize);
        initializePool();
    }

    private void initializePool() {
        for (int i = 0; i < minIdle; i++) {
            Connection connection = createConnection();
            allConnections.add(connection);
            idleConnections.offer(connection);
        }
    }

    public Connection acquire() {
        Connection connection = idleConnections.poll();
        if (connection != null && isConnectionUsable(connection)) {
            activeCount.incrementAndGet();
            return connection;
        }

        synchronized (this) {
            if (allConnections.size() < maxSize) {
                Connection newConnection = createConnection();
                allConnections.add(newConnection);
                activeCount.incrementAndGet();
                return newConnection;
            }
        }

        try {
            connection = idleConnections.poll(connectionTimeoutMs, TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new DatabaseException("Interrupted while waiting for a database connection", ex);
        }

        if (connection == null) {
            throw new DatabaseException("Timeout waiting for a database connection");
        }

        if (!isConnectionUsable(connection)) {
            removeConnection(connection);
            return acquire();
        }

        activeCount.incrementAndGet();
        return connection;
    }

    public void release(Connection connection) {
        if (connection == null) {
            return;
        }
        activeCount.updateAndGet(count -> Math.max(0, count - 1));

        if (!isConnectionUsable(connection)) {
            removeConnection(connection);
            return;
        }

        if (!idleConnections.offer(connection)) {
            removeConnection(connection);
        }
    }

    public String getStatistics() {
        int total = allConnections.size();
        int active = activeCount.get();
        int idle = idleConnections.size();
        return String.format("PoolStats{total=%d, active=%d, idle=%d, max=%d}", total, active, idle, maxSize);
    }

    public void shutdown() {
        for (Connection connection : allConnections) {
            try {
                connection.close();
            } catch (SQLException ignored) {
                // Ignore close failures.
            }
        }
        allConnections.clear();
        idleConnections.clear();
    }

    private Connection createConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new DatabaseException("Unable to create database connection", ex);
        }
    }

    private boolean isConnectionUsable(Connection connection) {
        try {
            return connection != null && !connection.isClosed() && connection.isValid(2);
        } catch (SQLException ex) {
            return false;
        }
    }

    private void removeConnection(Connection connection) {
        allConnections.remove(connection);
        try {
            connection.close();
        } catch (SQLException ignored) {
            // Ignore close failures.
        }
    }
}
