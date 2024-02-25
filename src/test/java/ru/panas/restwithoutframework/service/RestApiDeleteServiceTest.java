package ru.panas.restwithoutframework.service;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.panas.restwithoutframework.DAO.PersonDAO;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;


@ExtendWith(MockitoExtension.class)
class RestApiDeleteServiceTest {

    @Mock
    PersonDAO mockPersonDAO;
    @Mock
    private HttpServletRequest mockHttpServletRequest;

    @InjectMocks
    RestApiDeleteService restApiDeleteService;

    @Test
    void restRequest_ShouldThrowUnsupportedOperationException() {
        assertThatThrownBy(() -> restApiDeleteService
                .restRequest("requestPath"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void RestRequest_ShouldReturnDeletedIdFromDB() throws SQLException, IOException {

        long generatedId = 0;
        String requestPath = "/persons/1";
        generatedId = restApiDeleteService.restRequest(requestPath, mockHttpServletRequest);

        assertThat(generatedId).isEqualTo(0L);
    }
}