package ru.panas.restwithoutframework.DAO;

import ru.panas.restwithoutframework.DAO.DTO.PersonDto;
import ru.panas.restwithoutframework.models.Book;
import ru.panas.restwithoutframework.models.Person;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 *  interface for request to table Person of DB
 */

public interface PersonDAO {

    /**
     * method for find all of persons
     * @return List of persons
     */
    List<Person> findAll();

    /**
     * method for find person by id
     * @param id parameter of person at table
     * @return the found person
     * @throws SQLException if error of request
     */
    Optional<Person> findById(Long id) throws SQLException;

    /**
     * method update person in the table
     * @param personDto updatable person
     * @return count raws update
     * @throws SQLException if error of request
     */
    int update(PersonDto personDto) throws SQLException;

    /**
     * method delete person from the table
     * @param id parameter of person at table
     * @return count raws delete
     * @throws SQLException if error of request
     */
    int deletePersonById(Long id) throws SQLException;

    /**
     * method for insert new person in the table
     * @param personDto book for insert
     * @return id parameter of new person at table
     * @throws SQLException if error of request
     */
    long insertPerson(PersonDto personDto) throws SQLException;
}
