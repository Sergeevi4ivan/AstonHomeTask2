package ru.panas.restwithoutframework.DAO.DataSourse;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Connection pool
 *
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DataSourceHikariPostgreSQL {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceHikariPostgreSQL.class);
    final static String USER = "root";
    final static String PASSWORD = "root";
    final static String URL = "jdbc:postgresql://localhost:5432/postgres";

    private static final HikariConfig config = new HikariConfig();
    private static final HikariDataSource dataSource;

    static {
        config.setJdbcUrl(URL);
        config.setUsername(USER);
        config.setPassword(PASSWORD);


        config.setDriverClassName(org.postgresql.Driver.class.getName());
//        config.setConnectionTimeout(15000); //ms
//        config.setIdleTimeout(60000); //ms
//        config.setMaxLifetime(600000);//ms
//        config.setAutoCommit(false);
//        config.setMinimumIdle(5);
//        config.setMaximumPoolSize(10);
//        config.setPoolName("restServiceDbPool");
//        config.setRegisterMbeans(true);
//        config.addDataSourceProperty("cachePrepStmts", "true");
//        config.addDataSourceProperty("prepStmtCacheSize", "250");
//        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        dataSource = new HikariDataSource(config);
    }

    private DataSourceHikariPostgreSQL() {
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public static DataSource getHikariDataSource() {
        return dataSource;
    }

}
