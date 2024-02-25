package ru.panas.restwithoutframework.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.panas.restwithoutframework.DAO.PersonDaoJDBC;
import ru.panas.restwithoutframework.service.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet with GET, POST, DELETE, PUT methods
 *
 * @author Ivan Panasenko
 */

@WebServlet(urlPatterns = "/rest/*")
public class RestServlet extends HttpServlet {

    private static final String PERSON_CREATED_SUCCESS_JSON = "{\"personId\" : \"%d\" }";
    private static final String BOOK_CREATED_SUCCESS_JSON = "{\"bookId\" : \"%d\" }";
    private static final String ADD_ERROR = "Произошла ошибка, сущность не добавлена!\n";
    private static final String PERSON_DELETE_ERROR = "Произошла ошибка, пользователь не удалён!\n";
    private static final String BOOK_DELETE_ERROR = "Произошла ошибка, книга не удалена!\n";
    private static final String UPDATE_ERROR = "Произошла ошибка, сущность не обновлена!\n";

    private static final Logger LOGGER = LoggerFactory.getLogger(RestServlet.class);
    private PersonDaoJDBC personDaoJDBC;
    private RestApiService restApiGet;
    private RestApiService restApiPost;
    private RestApiService restApiPut;
    private RestApiService restApiDelete;

    @Override
    public void init() throws ServletException {
        final Object personDaoJdbc = getServletContext().getAttribute("personDAO");
        final Object restApiGetService = getServletContext().getAttribute("restApiGetService");
        final Object restApiPostService = getServletContext().getAttribute("restApiPostService");
        final Object restApiPutService = getServletContext().getAttribute("restApiPutService");
        final Object restApiDeleteService = getServletContext().getAttribute("restApiDeleteService");

        this.personDaoJDBC = (PersonDaoJDBC) personDaoJdbc;
        this.restApiGet = (RestApiGetService) restApiGetService;
        this.restApiPost = (RestApiPostService) restApiPostService;
        this.restApiPut = (RestApiPutService) restApiPutService;
        this.restApiDelete = (RestApiDeleteService) restApiDeleteService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Request {} to URI: {}", request.getMethod(), request.getRequestURI());
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        try {
            String userResponse = restApiGet.restRequest(pathInfo).orElseThrow(SQLException::new);
            response.setContentType("application/json; charset=UTF-8");
            response.setStatus(200);
            PrintWriter printWriter = response.getWriter();
            printWriter.write(userResponse);
        } catch (SQLException ex) {
            ex.printStackTrace();
            PrintWriter printWriter = response.getWriter();
            if (pathInfo.contains("persons")) {
                printWriter.write("No person by this ID");

                // возможно допишу для книг
            } else if (pathInfo.contains("books")) {
                printWriter.write("No book by this ID");
            }
            response.setStatus(404);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Request {} to URI: {}", request.getMethod(), request.getRequestURI());
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");

        try {
            long generatedId = restApiPost.restRequest(pathInfo, request);
            response.setContentType("application/json; charset=UTF-8");
            if (pathInfo.contains("persons")) {
                response.getWriter().write(String.format(PERSON_CREATED_SUCCESS_JSON, generatedId));
            } else if (pathInfo.contains("books")) {
                response.getWriter().write(String.format(BOOK_CREATED_SUCCESS_JSON, generatedId));
            }
            response.setStatus(201);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(ADD_ERROR);
            response.setStatus(400);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Request {} to URI: {}", request.getMethod(), request.getRequestURI());
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        try {
            long deleteRows = restApiDelete.restRequest(pathInfo, request);
            if (deleteRows != 0) {
                response.setStatus(200);
            } else {
                throw new SQLException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(400);
            if (pathInfo.contains("persons")) {
                response.getWriter().write(PERSON_DELETE_ERROR);
            } else if (pathInfo.contains("books")) {
                response.getWriter().write(BOOK_DELETE_ERROR);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOGGER.info("Пришёл запрос {} на URI: {}", request.getMethod(), request.getRequestURI());
        String pathInfo = request.getPathInfo();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");

        try {
            restApiPut.restRequest(pathInfo, request);
            response.setStatus(200);
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(400);
            response.getWriter().write(UPDATE_ERROR);
        }
    }
}
