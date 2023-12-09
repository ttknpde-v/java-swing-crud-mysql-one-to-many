package ttknpdev.services;

import ttknpdev.configuration.Connect;
import ttknpdev.entities.Address;
import ttknpdev.entities.Employee;
import ttknpdev.log.MyLog;
import ttknpdev.repositories.AddressRepository;
import ttknpdev.services.command.CommandSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AddressService implements AddressRepository<Address> {
    private Connect connect;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private MyLog myLog;

    public AddressService() {
        connect = new Connect();
        connection = connect.getConnect();
        myLog = new MyLog(AddressService.class);
    }

    @Override
    public List<Address> reads() {
        return null;
    }

    @Override
    public Address read(String aid) {
        ResultSet resultSet;
        Address address = null;
        try {
            preparedStatement = connection.prepareStatement(CommandSQL.ADDRESS_READ);
            preparedStatement.setString(1, aid);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                address = new Address(resultSet.getString("aid"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("details")
                );
            }
            return address;

        } catch (SQLException | NullPointerException exception) {
            myLog.log4j.warn("SQLException class has error : " + exception.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            return null; // return for condition in next step
        }
    }

    @Override
    public Employee readsByEid(String eid) {
        // return null;
        ResultSet resultSet;
        Employee employee = null;
        List<Address> addresses = new ArrayList<>();

        try {
            preparedStatement = connection.prepareStatement(CommandSQL.ADDRESS_READS_BY_EID);
            preparedStatement.setString(1, eid);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                employee = new Employee(resultSet.getString("eid"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("position"),
                        resultSet.getBoolean("active"),
                        resultSet.getFloat("salary"));

                addresses.add(new Address(
                        resultSet.getString("aid"),
                        resultSet.getString("country"),
                        resultSet.getString("city"),
                        resultSet.getString("details")
                ));
            }

            // assert employee != null;
            employee.setAddresses(addresses); // add List addresses to List addresses of Employee

            myLog.log4j.info(employee);
            myLog.log4j.info(employee.getAddresses());

            return employee;

        } catch (SQLException | NullPointerException exception) {
            myLog.log4j.warn("SQLException class has error : " + exception.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            return null; // return for condition in next step
        }
    }

    @Override
    public Integer create(Address obj) {
        try {

            preparedStatement = connection.prepareStatement(CommandSQL.ADDRESS_CREATE);
            preparedStatement.setString(1, obj.getAid());
            preparedStatement.setString(2, obj.getCountry());
            preparedStatement.setString(3, obj.getCity());
            preparedStatement.setString(4, obj.getDetails());
            myLog.log4j.info("created");

            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer createRelations(String eid, String aid) {

        try {

            preparedStatement = connection.prepareStatement(CommandSQL.EMPLOYEE_ADDRESSES_CREATE);
            preparedStatement.setString(1, eid);
            preparedStatement.setString(2, aid);

            myLog.log4j.info("created");

            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer update(Address obj) {
        try {

            preparedStatement = connection.prepareStatement(CommandSQL.ADDRESS_UPDATE);
            preparedStatement.setString(1, obj.getCountry());
            preparedStatement.setString(2, obj.getCity());
            preparedStatement.setString(3, obj.getDetails());
            preparedStatement.setString(4, obj.getAid());


            myLog.log4j.info("updated");

            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer delete(String aid) { //  , String eid
        try {

            preparedStatement = connection.prepareStatement(CommandSQL.ADDRESS_BEFORE_DELETE_WHERE);
            preparedStatement.setString(1, aid);
            // preparedStatement.setString(2, eid);
            myLog.log4j.info("deleted relation");
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(CommandSQL.ADDRESS_DELETE_WHERE);
            preparedStatement.setString(1, aid);
            myLog.log4j.info("deleted");
            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public void closeConnect() {
        connect.closeConnect();
    }
}
