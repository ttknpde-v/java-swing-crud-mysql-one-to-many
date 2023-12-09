package ttknpdev.services;

import ttknpdev.configuration.Connect;
import ttknpdev.entities.Employee;
import ttknpdev.log.MyLog;
import ttknpdev.repositories.EmployeeRepository;
import ttknpdev.services.command.CommandSQL;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService implements EmployeeRepository<Employee> {

    private Connect connect;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private MyLog myLog;

    public EmployeeService() {
        connect = new Connect(); // initial connect database
        connection = connect.getConnect();
        myLog = new MyLog(EmployeeService.class);
    }

    @Override
    public List<Employee> reads() {

        ResultSet resultSet;
        List<Employee> employeeList;

        try {

            employeeList = new ArrayList<>();
            preparedStatement = connection.prepareStatement(CommandSQL.EMPLOYEE_READS);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employeeList.add(new Employee
                        (
                                resultSet.getString("eid"),
                                resultSet.getString("firstname"),
                                resultSet.getString("lastname"),
                                resultSet.getString("position"),
                                resultSet.getBoolean("active"),
                                resultSet.getFloat("salary")
                        )
                );
            }

            return employeeList;

        } catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            // return null;
        }

    }

    @Override
    public Employee read(String eid) {
        ResultSet resultSet;
        Employee employee = null;
        try {
            preparedStatement = connection.prepareStatement(CommandSQL.EMPLOYEE_READ);
            // (order , value)
            preparedStatement.setString(1, eid);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                employee = new Employee(resultSet.getString("eid"),
                        resultSet.getString("firstname"),
                        resultSet.getString("lastname"),
                        resultSet.getString("position"),
                        resultSet.getBoolean("active"),
                        resultSet.getFloat("salary"));
            }
            myLog.log4j.info(employee);
            return employee;
        } catch (SQLException sqle) {
            myLog.log4j.warn("SQLException class has error : " + sqle.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqle.getMessage());
            return null;
        }
    }

    @Override
    public Integer delete(String eid) {

        try {

            preparedStatement = connection.prepareStatement(CommandSQL.EMPLOYEE_DELETE_WHERE);
            preparedStatement.setString(1, eid);
            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }

    }

    @Override
    public Integer create(Employee obj) {

        try {

            preparedStatement = connection.prepareStatement(CommandSQL.EMPLOYEE_CREATE);
            preparedStatement.setString(1, obj.getEid());
            preparedStatement.setString(2, obj.getFirstname());
            preparedStatement.setString(3, obj.getLastname());
            preparedStatement.setString(4, obj.getPosition());
            preparedStatement.setBoolean(5, obj.getActive());
            preparedStatement.setFloat(6, obj.getSalary());

            return preparedStatement.executeUpdate();

        } catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            // throw new RuntimeException("SQLException class has error : " + sqlException.getMessage());
            return 0;
        }
    }

    @Override
    public Integer update(Employee obj) {
        try {

            preparedStatement = connection.prepareStatement(CommandSQL.EMPLOYEE_UPDATE);
            preparedStatement.setString(1, obj.getFirstname());
            preparedStatement.setString(2, obj.getLastname());
            preparedStatement.setString(3, obj.getPosition());
            preparedStatement.setBoolean(4, obj.getActive());
            preparedStatement.setFloat(5, obj.getSalary());
            preparedStatement.setString(6, obj.getEid());

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
