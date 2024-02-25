package ru.panas.restwithoutframework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.panas.restwithoutframework.DAO.BookDAO;
import ru.panas.restwithoutframework.DAO.PersonDAO;
import ru.panas.restwithoutframework.models.Person;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestApiGetServiceTest {

    @Mock
    private PersonDAO personDAO;

    @Mock
    private BookDAO bookDAO;

    @InjectMocks
    RestApiGetService restApiGetService;



    @Test
    void restRequest_ShouldReturnPersonJSON() throws SQLException, JsonProcessingException {

        Person person = new Person();
        person.setId(2);
        person.setName("Ivan");
        person.setAge(25);

        String requestPath = "/persons/2";
        String actualJSON = "Optional[{\"id\":2,\"name\":\"Ivan\",\"age\":25,\"books\":[],\"passport\":null}]";

        when(personDAO.findById(2L)).thenReturn(Optional.of(person));
        String expectedJSON = restApiGetService.restRequest(requestPath).toString();

        assertThat(actualJSON).contains(expectedJSON);

    }

    @Test

    void RestRequest_ShouldThrowUnsupportedOperationException() throws SQLException, IOException {
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        assertThatThrownBy(() -> restApiGetService
                .restRequest("requestPath", httpServletRequest))
                .isInstanceOf(UnsupportedOperationException.class);

    }
}