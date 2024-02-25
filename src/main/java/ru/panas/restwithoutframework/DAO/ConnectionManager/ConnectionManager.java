package ru.panas.restwithoutframework.DAO.ConnectionManager;

import java.sql.Connection;

/**
 * interface for connection management
 * @author Ivan Panasenko
 */

public interface ConnectionManager {

    /**
     * method get connection from sql.dataSourse
     */
    void beginConnection();

    /**
     * method for close connection if it not null
     */
    void close();

    /**
     * if connection not null, then method return current connection
     * @return sql.connection
     */
    Connection getCurrentConnection();
}
