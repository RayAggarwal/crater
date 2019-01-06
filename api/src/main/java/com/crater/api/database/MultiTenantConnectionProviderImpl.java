package com.crater.api.database;

import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MultiTenantConnectionProviderImpl implements MultiTenantConnectionProvider {

    private DataSource dataSource;
    private String defaultTenant;
    private static final Logger LOGGER = LoggerFactory.getLogger(MultiTenantConnectionProviderImpl.class);

    @Autowired
    public MultiTenantConnectionProviderImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Connection getAnyConnection() throws SQLException {
        return dataSource.getConnection();
    }

    @Override
    public void releaseAnyConnection(Connection connection) throws SQLException {
        connection.close();
    }

    @Override
    public Connection getConnection(String tenantIdentifier) throws SQLException {
        String tenant = TenantContext.getCurrentTenant();
        String useStmt;
        if (StringUtils.isEmpty(tenant)) {
            useStmt = "USE " + defaultTenant;
        } else {
            useStmt = "USE " + tenant;
        }
        final Connection connection = getAnyConnection();
        try {
            connection.createStatement().execute(useStmt);
        } catch (Exception e) {
            LOGGER.error("Error setting schema to {} with use statement: {}", tenant, useStmt, e);
            throw new HibernateException("Error setting schema to " + tenant, e);
        }
        return connection;
    }

    @Override
    public void releaseConnection(String tenantIdentifier, Connection connection) throws SQLException {
        try {
            connection.createStatement().execute("USE " + defaultTenant);
        } catch (Exception e) {
            LOGGER.error("Error releasing connection and setting connection back to main schema", e);
            throw new HibernateException("Error releasing connection", e);
        }
    }

    @Override
    public boolean supportsAggressiveRelease() {
        return true;
    }

    @Override
    public boolean isUnwrappableAs(Class unwrapType) {
        return false;
    }

    @Override
    public <T> T unwrap(Class<T> unwrapType) {
        return null;
    }
}
