package ttknpdev.entities;

import java.util.List;

public class Employee {
    private String eid;
    private String firstname;
    private String lastname;
    private String position;
    private Boolean active;
    private Float salary;
    private List<Address> addresses;

    public Employee(String eid, String firstname, String lastname, String position, Boolean active, Float salary) {
        this.eid = eid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.position = position;
        this.active = active;
        this.salary = salary;
    }

    public void setAll(String eid, String firstname, String lastname, String position, Boolean active, Float salary) {
        this.eid = eid;
        this.firstname = firstname;
        this.lastname = lastname;
        this.position = position;
        this.active = active;
        this.salary = salary;
    }

    public Employee() {
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "eid='" + eid + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", position='" + position + '\'' +
                ", active=" + active +
                ", salary=" + salary +
                '}';
    }
}
