package ru.panas.restwithoutframework.DAO.ConnectionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerJDBC implements ConnectionManager {

    private final DataSource dataSource;

    private Connection connection;

    public ConnectionManagerJDBC(DataSource dataSource) {
        if (dataSource == null) {
            throw new NullPointerException("Datasource is null");
        }
        this.dataSource = dataSource;
    }

    @Override
    public Connection getCurrentConnection() {
        checkConnection();
        return connection;
    }

    @Override
    public void beginConnection() {
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        checkConnection();
        try {
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *  method check connection for not null
     * @exception if connection is null throw NullPointerException("Connection is invalid")
     */
    private void checkConnection() {
        if (connection == null) {
            throw new NullPointerException("Connection is invalid");
        }
    }
}
