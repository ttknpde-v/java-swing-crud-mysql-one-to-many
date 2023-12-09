package ttknpdev.services.command;

public class CommandSQL {
    public static String EMPLOYEE_READS = "select * from employees";
    public static String EMPLOYEE_READ = "select * from employees where eid = ?";
    public static String ADDRESS_READ = "select * from addresses where aid = ?";
    public static String EMPLOYEE_DELETE_WHERE = "delete from employees where eid = ?";
    public static String ADDRESS_DELETE_WHERE = "delete from addresses where aid = ?";
    public static String ADDRESS_BEFORE_DELETE_WHERE = "delete from employee_addresses where aid = ?";
    public static String EMPLOYEE_CREATE = "insert into employees values (" +
            "? , ? , ? , ? , ? , ? ) ";
    public static String EMPLOYEE_UPDATE = "update employees set firstname = ? , lastname = ? , position = ? , active = ? , salary = ? " +
            "where eid = ? ";
    public static String ADDRESS_UPDATE = "update addresses set country = ? , city = ? , details = ? " +
            "where aid = ? ";
    public static String ADDRESS_CREATE = "insert into addresses values (" +
            "? , ? , ? , ? ) ";
    public static String ADDRESS_READS_BY_EID = "select * from employees AS e " +
            "join employee_addresses AS e_m " +
            "on e.eid = e_m.eid " +
            "join addresses AS a " +
            "on a.aid = e_m.aid " +
            "where e.eid = ? ";
    public static String EMPLOYEE_ADDRESSES_CREATE = "insert into employee_addresses values (" +
            "? , ? ) ";

}
