package ru.panas.restwithoutframework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.panas.restwithoutframework.DAO.BookDAO;
import ru.panas.restwithoutframework.DAO.PersonDAO;
import ru.panas.restwithoutframework.models.Book;
import ru.panas.restwithoutframework.models.Person;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
public class RestApiGetService implements RestApiService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private PersonDAO personDAO;
    private BookDAO bookDAO;

    @Override
    public Optional<String> restRequest(String requestPath) throws SQLException, JsonProcessingException {
        if (requestPath.matches("^/persons/\\d+$")) {
            String personIdParam = parseID(requestPath);
            long personId = Long.parseLong(personIdParam);
            Person person = personDAO.findById(personId).orElseThrow(SQLException::new);
            final String jsonPerson = objectMapper.writeValueAsString(person);
            return Optional.ofNullable(jsonPerson);
            
        } else if (requestPath.matches("^/books/\\d+$")) {
            String booksIdParam = parseID(requestPath);
            long booksId = Long.parseLong(booksIdParam);
            Book book = bookDAO.findById(booksId).orElseThrow(SQLException::new);
            final String jsonBook = objectMapper.writeValueAsString(book);
            return Optional.ofNullable(jsonBook);
            
        } else if (requestPath.matches("^/persons/$")) {
            final List<Person> allPerson = personDAO.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(allPerson));

        } else if (requestPath.matches("^/books/$")) {
            final List<Book> allBooks = bookDAO.findAll();
            return Optional.ofNullable(objectMapper.writeValueAsString(allBooks));
        }
        return Optional.empty();
    }

    @Override
    public long restRequest(String requestPath, HttpServletRequest request) throws SQLException, IOException {
        throw new UnsupportedOperationException("Unsupported Operation");
    }
    
    private String parseID(String requestPath) {
        String[] parts = requestPath.split("/");
        return parts[2];
    }
}
