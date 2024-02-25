package ru.panas.restwithoutframework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.panas.restwithoutframework.DAO.BookDAO;
import ru.panas.restwithoutframework.DAO.DTO.PersonDto;
import ru.panas.restwithoutframework.DAO.PersonDAO;
import ru.panas.restwithoutframework.models.Person;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * PUT service
 * @author Ivan Panasenko
 */

@Getter
@Setter
@AllArgsConstructor
public class RestApiPutService implements RestApiService {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private PersonDAO personDAO;
    private BookDAO bookDAO;

    @Override
    public Optional<String> restRequest(String requestPath) throws SQLException, JsonProcessingException {
        throw new UnsupportedOperationException();
    }

    @Override
    public long restRequest(String requestPath, HttpServletRequest request) throws SQLException, IOException {
        long updatedRows = 0;

        if (requestPath.matches("^/persons/\\d+$")) {
            String[] parts = requestPath.split("/");
            String personIdParam = parts[2];
            int personId = Integer.parseInt(personIdParam);
            String bodyParams = request.getReader().lines().collect(Collectors.joining());

        // Возможно дописать обновление только владельца
            PersonDto personDto = objectMapper.readValue(bodyParams, PersonDto.class);
            personDto.setId(personId);

            updatedRows = personDAO.update(personDto);
        }

        return updatedRows;
    }
}
