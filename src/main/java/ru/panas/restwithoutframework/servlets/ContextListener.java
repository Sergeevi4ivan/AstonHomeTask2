package ru.panas.restwithoutframework.servlets;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import ru.panas.restwithoutframework.DAO.BookDAO;
import ru.panas.restwithoutframework.DAO.BookDaoJDBC;
import ru.panas.restwithoutframework.DAO.ConnectionManager.ConnectionManager;
import ru.panas.restwithoutframework.DAO.ConnectionManager.ConnectionManagerJDBC;
import ru.panas.restwithoutframework.DAO.DataSourse.DataSourceHikariPostgreSQL;
import ru.panas.restwithoutframework.DAO.PersonDAO;
import ru.panas.restwithoutframework.DAO.PersonDaoJDBC;
import ru.panas.restwithoutframework.service.*;

import javax.sql.DataSource;

/**
 * Listener load before all servlets for initialized
 * connection and classes
 */

@WebListener
public class ContextListener implements ServletContextListener {

    private BookDAO bookDaoJDBC;
    private PersonDAO personDaoJDBC;
    private RestApiService restApiGetService;
    private RestApiService restApiPostService;
    private RestApiService restApiPutService;
    private RestApiService restApiDeleteService;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        final ServletContext servletContext = sce.getServletContext();

        DataSource dataSource = DataSourceHikariPostgreSQL.getHikariDataSource();
        ConnectionManager connectionManager = new ConnectionManagerJDBC(dataSource);

        this.bookDaoJDBC = new BookDaoJDBC(connectionManager);
        this.personDaoJDBC = new PersonDaoJDBC(connectionManager);

        restApiGetService = new RestApiGetService(personDaoJDBC, bookDaoJDBC);
        restApiPostService = new RestApiPostService(personDaoJDBC, bookDaoJDBC);
        restApiPutService = new RestApiPutService(personDaoJDBC, bookDaoJDBC);
        restApiDeleteService = new RestApiDeleteService(personDaoJDBC, bookDaoJDBC);

//        servletContext.setAttribute("bookDAO", bookDaoJDBC);
        servletContext.setAttribute("personDAO", personDaoJDBC);
        servletContext.setAttribute("restApiGetService", restApiGetService);
        servletContext.setAttribute("restApiPostService", restApiPostService);
        servletContext.setAttribute("restApiPutService", restApiPutService);
        servletContext.setAttribute("restApiDeleteService", restApiDeleteService);

    }
}
