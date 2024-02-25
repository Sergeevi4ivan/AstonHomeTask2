package ru.panas.restwithoutframework.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.panas.restwithoutframework.DAO.ConnectionManager.ConnectionManager;
import ru.panas.restwithoutframework.DAO.DTO.BookDto;
import ru.panas.restwithoutframework.models.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BookDaoJDBC implements BookDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookDaoJDBC.class);

    private final ConnectionManager connectionManager;

    public BookDaoJDBC(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }


    @Override
    public List<Book> findAll() {

        List<Book> bookList = new ArrayList<>();

        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.SELECT_ALL_BOOKS.QUERY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {

                    bookList.add(parseBookFromResultSet(resultSet));
                }
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return bookList;
    }

    @Override
    public Optional<Book> findById(Long id) throws SQLException{
        Book book = null;
        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.SELECT_BOOK_BY_ID.QUERY)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    book = parseBookFromResultSet(resultSet);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
        return Optional.ofNullable(book);
    }

    @Override
    public int update(Book book) throws SQLException {
        int rowsUpdated = 0;
        connectionManager.beginConnection();

        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.UPDATE_BOOK_BY_ID.QUERY)) {
            preparedStatement.setString(1, book.getTitle());
            preparedStatement.setString(2, book.getAuthor());
            preparedStatement.setInt(3, book.getYearProduction());
            preparedStatement.setInt(4, book.getId());

            rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }

        return rowsUpdated;
    }

    @Override
    public long insertBook(BookDto bookDto) throws SQLException {
        connectionManager.beginConnection();

        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.INSERT_BOOK.QUERY, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, bookDto.getTitle());
            preparedStatement.setString(2, bookDto.getAuthor());
            preparedStatement.setInt(3, bookDto.getYearProduction());

            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                resultSet.next();

                long id = resultSet.getLong(1);
                return id;
            }
        }catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public int deleteBookById(Long id) throws SQLException {
        int updatedRows;

        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.DELETE_BOOK_BY_ID.QUERY)) {
            preparedStatement.setLong(1, id);
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
        return updatedRows;
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException{
        Book book = new Book();

        book.setId(Integer.parseInt(resultSet.getString("bookId")));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setYearProduction(Integer.parseInt(resultSet.getString("yearProduction")));

        return book;
    }

    /**
     * enum for SQL queries
     */
    enum SQLquery {
        SELECT_BOOK_BY_ID("SELECT * FROM books JOIN person p on p.id = books.personId WHERE books.bookId=(?);"),
        UPDATE_BOOK_BY_ID("UPDATE books set title=(?), author=(?), yearProduction=(?) WHERE bookId=(?)"),
        DELETE_BOOK_BY_ID("DELETE from books WHERE bookId=(?);"),
        SELECT_ALL_BOOKS("SELECT * FROM books"),
        INSERT_BOOK("INSERT INTO books(title, author, yearProduction) values(?, ?, ?)");

        String QUERY;
        SQLquery(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
