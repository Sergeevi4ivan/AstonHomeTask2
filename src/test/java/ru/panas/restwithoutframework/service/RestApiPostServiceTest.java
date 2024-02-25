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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestApiPostServiceTest {

    @Mock
    private PersonDAO mockPersonDAO;
    @Mock
    private HttpServletRequest mockHttpServletRequest;
    @InjectMocks
    RestApiPostService restApiPostService;

    @Test
    void restRequest_ShouldReturnGeneratedIdInDB() throws IOException, SQLException {

        long generatedId = 0;
        String requestPath = "/persons/";
        String bodyParams = "{\"id\":0,\"name\":\"Petrov Petr Petrovi4\",\"age\":28,\"books\":[],\"passport\":null}";

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                new ByteArrayInputStream(
                        bodyParams.getBytes(StandardCharsets.UTF_8))));

        doReturn(bufferedReader).when(mockHttpServletRequest).getReader();

        generatedId = restApiPostService.restRequest(requestPath, mockHttpServletRequest);

        assertThat(generatedId).isEqualTo(0L);

    }

    @Test
    void RestRequest_ShouldThrowUnsupportedOperationException() {

        assertThatThrownBy(() -> restApiPostService
                .restRequest("requestPath"))
                .isInstanceOf(UnsupportedOperationException.class);
    }


}