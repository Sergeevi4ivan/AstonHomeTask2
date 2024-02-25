package ru.panas.restwithoutframework.DAO;

import ru.panas.restwithoutframework.DAO.DTO.BookDto;
import ru.panas.restwithoutframework.models.Book;
import ru.panas.restwithoutframework.models.Person;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *  interface for request to table BOOK of DB
 */

public interface BookDAO {

    /**
     * method for find all of books
     * @return List of books
     */
    List<Book> findAll();

    /**
     * method for find book by id
     * @param id parameter of book at table
     * @return the found book
     * @throws SQLException if error of request
     */
    Optional<Book> findById(Long id) throws SQLException;

    /**
     * method update book in the table
     * @param book updatable book
     * @return count raws update
     * @throws SQLException if error of request
     */
    int update(Book book) throws SQLException;

    /**
     * method delete book from the table
     * @param id parameter of book at table
     * @return count raws delete
     * @throws SQLException if error of request
     */
    int deleteBookById(Long id) throws SQLException;

    /**
     * method for insert new book in the table
     * @param bookDto book for insert
     * @return id parameter of new book at table
     * @throws SQLException if error of request
     */
    long insertBook(BookDto bookDto) throws SQLException;
}
