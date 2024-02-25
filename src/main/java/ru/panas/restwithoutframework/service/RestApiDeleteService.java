package ru.panas.restwithoutframework.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.panas.restwithoutframework.DAO.BookDAO;
import ru.panas.restwithoutframework.DAO.PersonDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * DELETE service
 * @author Ivan Panasenko
 */
@Getter
@Setter
@AllArgsConstructor
public class RestApiDeleteService implements RestApiService {

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
            
            long personId = Long.parseLong(personIdParam);
            updatedRows = personDAO.deletePersonById(personId);
        } else if (requestPath.matches("^/books/\\d+$")) {
            String[] parts = requestPath.split("/");
            String bookIdParam = parts[2];

            long bookId = Long.parseLong(bookIdParam);
//            updatedRows = bookDAO.deleteBookById(bookId);
        }
        return updatedRows;
    }
}
