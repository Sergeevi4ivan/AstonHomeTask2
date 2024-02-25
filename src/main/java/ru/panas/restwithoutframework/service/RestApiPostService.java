package ru.panas.restwithoutframework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.panas.restwithoutframework.DAO.BookDAO;
import ru.panas.restwithoutframework.DAO.DTO.BookDto;
import ru.panas.restwithoutframework.DAO.DTO.PersonDto;
import ru.panas.restwithoutframework.DAO.PersonDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * POST service
 * @author Ivan Panasenko
 */

@Getter
@Setter
@AllArgsConstructor
public class RestApiPostService implements RestApiService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private PersonDAO personDAO;
    private BookDAO bookDAO;

    @Override
    public Optional<String> restRequest(String requestPath) throws SQLException, JsonProcessingException {
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public long restRequest(String requestPath, HttpServletRequest request) throws SQLException, IOException {
        long generatedId = 0;

        if (requestPath.matches("^/persons/$")) {
            String bodyParams = request.getReader().lines().collect(Collectors.joining());
            PersonDto personDto = objectMapper.readValue(bodyParams, PersonDto.class);
            generatedId = personDAO.insertPerson(personDto);

        } else if (requestPath.matches("^/books/$")) {
            String bodyParams = request.getReader().lines().collect(Collectors.joining());
            BookDto bookDto = objectMapper.readValue(bodyParams, BookDto.class);
            generatedId = bookDAO.insertBook(bookDto);
        }

        return generatedId;
    }
}
