package ttknpdev.configuration;

import ttknpdev.entities.Address;
import ttknpdev.log.MyLog;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Connect {
    /*
      we can use ResourceBundle class for accessing any properties file that you define
    */
    private ResourceBundle resourceBundle;
    private Connection connect;
    private MyLog myLog;
    /*
        private Class loadDriver; for storing some driver MYSQL
    */

    public Connect() {
        myLog = new MyLog(Connect.class);
        resourceBundle = ResourceBundle.getBundle("resources/info/my_info");
        // setConnect();
    }

    public Connection getConnect() {

        try {
            /*
                Class<?> loadDriver = Class.forName(resourceBundle.getString("MYSQL_DRIVER"));
                loadDriver.getClasses(); //  called Load Driver
                Can reduce !

            */
            Class.forName(resourceBundle.getString("MYSQL_DRIVER")); //  called Load Driver

            connect = DriverManager.getConnection(
                    resourceBundle.getString("MYSQL_URL"),
                    resourceBundle.getString("MYSQL_USERNAME"),
                    resourceBundle.getString("MYSQL_PASSWORD")
            ); // connect to database

            myLog.log4j.info("connected database");

            return connect;

        }
        catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            throw new RuntimeException(sqlException.getCause());

        }
        catch (ClassNotFoundException classNotFoundException) {

            myLog.log4j.warn("ClassNotFoundException class has error : " + classNotFoundException.getMessage());
            throw new RuntimeException(classNotFoundException.getCause());

        }

    }


    public void closeConnect() {

        try {

            connect.close();
            myLog.log4j.info("closed database");

        } catch (SQLException sqlException) {

            myLog.log4j.warn("SQLException class has error : " + sqlException.getMessage());
            throw new RuntimeException(sqlException.getCause());

        }
    }

    /*
    public static void main(String[] args) throws SQLException {
        Connect con = new Connect();
        // c.closeConnect();
        List<EmployeeAndAddresses> employeeAndAddressesList = new ArrayList<>();
        EmployeeAndAddresses employeeAndAddresses = null;
        List<Address> addresses = new ArrayList<>();
        Address address;
        PreparedStatement preparedStatement = con.connect.prepareStatement(
                "select * from employees AS e " +
                        "join employee_addresses AS e_m " +
                        "on e.eid = e_m.eid " +
                        "join addresses AS a " +
                        "on a.aid = e_m.aid " +
                        "where e.eid = 'E001';");
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {

            employeeAndAddresses = new EmployeeAndAddresses(
                    resultSet.getString("eid"),
                    resultSet.getString("firstname"),
                    resultSet.getString("lastname"),
                    resultSet.getString("position"),
                    resultSet.getBoolean("active"),
                    resultSet.getFloat("salary")
                    ,
                    null
                    );


            *//*
            List.of(new Address(
                            resultSet.getString("aid"),
                            resultSet.getString("country"),
                            resultSet.getString("city"),
                            resultSet.getString("details"))
                    )*//*
            address = new Address(
                    resultSet.getString("aid"),
                    resultSet.getString("country"),
                    resultSet.getString("city"),
                    resultSet.getString("details"));

            addresses.add(address);

            // employeeAndAddresses.setAddresses();


        }

         employeeAndAddresses.setAddresses(addresses);

         employeeAndAddressesList.add(employeeAndAddresses);


        System.out.println(employeeAndAddressesList);

        con.closeConnect();

    }
    */

}
