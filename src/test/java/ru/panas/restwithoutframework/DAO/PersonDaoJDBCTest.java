package ru.panas.restwithoutframework.DAO;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import ru.panas.restwithoutframework.DAO.ConnectionManager.ConnectionManager;
import ru.panas.restwithoutframework.models.Person;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonDaoJDBCTest {

//    private EmbeddedDatabase embeddedDatabase;
//    private JdbcTemplate jdbcTemplate;
//    @InjectMocks
//    private PersonDaoJDBC personDaoJDBC;
//
//    @BeforeEach
//    public void setUp() {
//        embeddedDatabase = new EmbeddedDatabaseBuilder()
//                .addDefaultScripts()
//                .setType(EmbeddedDatabaseType.H2)
//                .build();
//
//        jdbcTemplate = new JdbcTemplate(embeddedDatabase);
//        personDaoJDBC = new PersonDaoJDBC(jdbcTemplate);
//    }
//
//    @AfterEach
//    public void tearDown() {
//        embeddedDatabase.shutdown();
//    }
//
//    @Mock
//    private ConnectionManager mockConnectionManager;


    @Test
    void findAll() throws SQLException, ClassNotFoundException {

//        Connection connection = jdbcTemplate.getDataSource().getConnection();
//        doNothing().when(mockConnectionManager).beginConnection();
//
//        assertThat(personDaoJDBC.findAll()).isNotNull();
//        assertThat(personDaoJDBC.findAll().size()).isEqualTo(3);

    }

    @Test
    void findById() {
    }

    @Test
    void update() {
    }

    @Test
    void insertPerson() {
    }

    @Test
    void deletePersonById() {
    }

    @Test
    void parsePassportFromResultSet() {
    }

    @Test
    void getConnectionManager() {
    }
}
