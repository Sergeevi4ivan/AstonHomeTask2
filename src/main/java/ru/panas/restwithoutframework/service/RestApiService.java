package ru.panas.restwithoutframework.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Request Handler REST (GET, POST, PUT, DELETE)
 * @author Ivan Panasenko
 */
public interface RestApiService {

    /**
     * method for GET request
     * @param requestPath path of request
     * @return request from data base
     * @throws SQLException
     * @throws JsonProcessingException
     */
    Optional<String> restRequest(String requestPath) throws SQLException, JsonProcessingException;

    /**
     * method for DELETE, POST, PUT requests
     * @param requestPath path of request
     * @param request request from client
     * @return updated rows
     * @throws SQLException
     * @throws IOException
     */
    long restRequest(String requestPath, HttpServletRequest request) throws SQLException, IOException;
}
