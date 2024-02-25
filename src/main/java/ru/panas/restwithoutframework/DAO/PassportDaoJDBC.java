package ru.panas.restwithoutframework.DAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.panas.restwithoutframework.DAO.ConnectionManager.ConnectionManager;
import ru.panas.restwithoutframework.models.Passport;
import ru.panas.restwithoutframework.models.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PassportDaoJDBC implements PassportDAO{

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonDaoJDBC.class);

    private final ConnectionManager connectionManager;

    public PassportDaoJDBC(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public List<Passport> findAll() {

        List<Passport> passportList = new ArrayList<>();

        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.SELECT_ALL_PASSPORTS.QUERY)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    passportList.add(parsePassportFromResultSet(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);

        }
        return passportList;
    }

    public Optional<Passport> findById(Long id) throws SQLException{
        Passport passport = null;
        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.SELECT_PASSPORT_BY_ID.QUERY)) {
            preparedStatement.setLong(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    passport = parsePassportFromResultSet(resultSet);
                }
            }
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
        return Optional.ofNullable(passport);
    }

    public int update(Passport passport) throws SQLException {
        int rowsUpdated = 0;
        connectionManager.beginConnection();

        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.UPDATE_PASSPORT_BY_ID.QUERY)){
            preparedStatement.setString(1, passport.getAddress());
            preparedStatement.setInt(2, passport.getNumber());
            preparedStatement.setInt(3, passport.getPassportId());

            rowsUpdated = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }

        return rowsUpdated;
    }

    public long insertPassport(Passport passport) throws SQLException {
        connectionManager.beginConnection();

        try (Connection connection = connectionManager.getCurrentConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.INSERT_PASSPORT.QUERY, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, passport.getAddress());
            preparedStatement.setInt(2, passport.getNumber());

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

    public int deletePassportById(Long id) throws SQLException {
        int updatedRows;

        connectionManager.beginConnection();
        try (Connection connection = connectionManager.getCurrentConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQLquery.DELETE_PASSPORT_BY_ID.QUERY)) {
            preparedStatement.setLong(1, id);
            updatedRows = preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            LOGGER.error(ex.getMessage(), ex);
            throw ex;
        }
        return updatedRows;
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

    enum SQLquery {
        SELECT_PASSPORT_BY_ID("SELECT * FROM passport JOIN person p on passport.personId = p.id WHERE passport.personId=(?);"),
        UPDATE_PASSPORT_BY_ID("UPDATE passport set address=(?), number=(?) WHERE id=(?)"),
        DELETE_PASSPORT_BY_ID("DELETE from passport WHERE id=(?)"),
        SELECT_ALL_PASSPORTS("SELECT * FROM passport JOIN person on passport.personId = person.id"),
        INSERT_PASSPORT("INSERT INTO passport(address, number) values(?, ?)"),
        GET_BOOKS_BY_PERSON("SELECT * FROM books WHERE personId=?");

        String QUERY;
        SQLquery(String QUERY) {
            this.QUERY = QUERY;
        }
    }
}
