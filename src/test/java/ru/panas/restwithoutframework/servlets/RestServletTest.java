package ru.panas.restwithoutframework.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.panas.restwithoutframework.service.RestApiGetService;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
//@SpringBootTest
class RestServletTest {

    @Mock
    HttpServletRequest mockHttpServletRequest;
    @Mock
    HttpServletResponse mockHttpServletResponse;
    @InjectMocks
    RestServlet restServlet;


    static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");

        return DriverManager.getConnection("jdbc:h2:~/db;INIT=RUNSCRIPT FROM 'classpath:schema.sql'");

    }



    @Test
    void doGet() throws ServletException, IOException, SQLException, ClassNotFoundException {


//        TestRestTemplate restTemplate = new TestRestTemplate();
//
//        String resourceUrl = "http://localhost:8081//rest/persons/";
//
//
//        doReturn(resourceUrl).when(mockHttpServletRequest).getPathInfo();
//
//        restServlet.doGet(mockHttpServletRequest, mockHttpServletResponse);
//
//        assertThat(mockHttpServletResponse.getStatus()).isEqualTo(200);

//        String pathInfo = "/persons/2";
//        Optional<String> optional = Optional.of("\"name\" : \"John\"");
//
//
//        doReturn(optional).when(restApiGet).restRequest(pathInfo);
//
//        restServlet.doGet(request, response);
//
//        assertThat("application/json; charset=UTF-8").isEqualTo(response.getContentType());

    }

    @Test
    void doPost() {
    }

    @Test
    void doDelete() {
    }

    @Test
    void doPut() {
    }
}