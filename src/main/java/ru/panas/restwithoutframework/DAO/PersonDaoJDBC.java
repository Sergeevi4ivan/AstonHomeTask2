package ru.panas.restwithoutframework.DAO;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.panas.restwithoutframework.DAO.ConnectionManager.ConnectionManager;
import ru.panas.restwithoutframework.DAO.DTO.PersonDto;
import ru.panas.restwithoutframework.models.Book;
import ru.panas.restwithoutframework.models.Passport;
import ru.panas.restwithoutframework.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class PersonDaoJDBC implements PersonDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDaoJDBC.class);

    private ConnectionManager connectionManager;


    public PersonDaoJDBC(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public PersonDaoJDBC() {
    }

    // временно для проверки тестов. Возможно можно будет удалить
    public PersonDaoJDBC(JdbcTemplate jdbcTemplate) {

    }

    @Override
    public List<Person> findAll() {

        List<Person> personList = new ArrayList<>();

        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.SELECT_ALL_PERSONS.QUERY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    personList.add(parsePersonFromResultSet(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);

        }
        return personList;
    }

    @Override
    public Optional<Person> findById(Long id) throws SQLException{
        Person person = null;
        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.SELECT_PERSON_BY_ID.QUERY)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    person = parsePersonFromResultSet(resultSet);
                    person.setBooks(getBooksByPersonId(Math.toIntExact(id)));
                    person.setPassport(parsePassportFromResultSet(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
        return Optional.ofNullable(person);
    }

    @Override
    public int update(PersonDto personDto) throws SQLException {
        int rowsUpdated = 0;

        Person updatePerson = findById((long) personDto.getId()).get();
        if (personDto.getName() != null) updatePerson.setName(personDto.getName());
        if (personDto.getAge() != 0) updatePerson.setAge(personDto.getAge());

        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.UPDATE_PERSON_BY_ID.QUERY)){
                preparedStatement.setString(1, updatePerson.getName());
                preparedStatement.setInt(2, updatePerson.getAge());
                preparedStatement.setInt(3, personDto.getId());

                rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }

        return rowsUpdated;
    }

    @Override
    public long insertPerson(PersonDto personDto) throws SQLException {
        connectionManager.beginConnection();

        try (Connection connection = connectionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.INSERT_PERSON.QUERY, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, personDto.getName());
            preparedStatement.setInt(2, personDto.getAge());

            preparedStatement.executeUpdate();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()){
                resultSet.next();

                return resultSet.getLong(1);
            }
        }catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public int deletePersonById(Long id) throws SQLException {
        int updatedRows;

        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.DELETE_PERSON_BY_ID.QUERY)) {
            preparedStatement.setLong(1, id);
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
        return updatedRows;
    }

    private List<Book> getBooksByPersonId(int id) throws SQLException {
        List<Book> books = new ArrayList<>();
        Book book;

        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.GET_BOOKS_BY_PERSON.QUERY)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    book = parseBookFromResultSet(resultSet);
                    book.setOwner(parsePersonFromResultSet(resultSet));
                    books.add(book);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }


        return books;
    }

    private Book parseBookFromResultSet(ResultSet resultSet) throws SQLException{
        Book book = new Book();

        book.setId(Integer.parseInt(resultSet.getString("bookId")));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setYearProduction(Integer.parseInt(resultSet.getString("yearProduction")));

        return book;
    }

    private Person parsePersonFromResultSet(ResultSet resultSet) throws SQLException{
        Person person = new Person();

        person.setId(Integer.parseInt(resultSet.getString("id")));
        person.setName(resultSet.getString("name"));
        person.setAge(Integer.parseInt(resultSet.getString("age")));

        return person;
    }

    protected Passport parsePassportFromResultSet(ResultSet resultSet) throws SQLException{
        Passport passport = new Passport();

        String personId = resultSet.getString("personId");

        if (personId != null) {
            passport.setPassportId(Integer.parseInt(resultSet.getString("personId")));
            passport.setAddress(resultSet.getString("address"));
            passport.setNumber(Integer.parseInt(resultSet.getString("number")));
        }

        return passport;
    }

    /**
     * enum for SQL queries
     */

    enum SQLquery {

        SELECT_PERSON_BY_ID("SELECT person.id, person.age, person.name, books.title, passport.personId, passport.address, passport.number " +
                "FROM person LEFT JOIN books on books.personId = person.id " +
                "LEFT JOIN passport on passport.personId = person.id WHERE person.id=(?);"),
        UPDATE_PERSON_BY_ID("UPDATE person set name=(?), age=(?) WHERE id=(?)"),
        DELETE_PERSON_BY_ID("DELETE from person WHERE id=(?)"),
        SELECT_ALL_PERSONS("SELECT * FROM person"),
        INSERT_PERSON("INSERT INTO person(name, age) values(?, ?)"),
        GET_BOOKS_BY_PERSON("SELECT * FROM books LEFT JOIN person on person.id=books.personId WHERE personId=(?)");

        String QUERY;
        SQLquery(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
