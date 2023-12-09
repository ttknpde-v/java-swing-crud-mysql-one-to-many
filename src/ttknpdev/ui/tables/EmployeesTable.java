package ttknpdev.ui.tables;

import ttknpdev.entities.Employee;
import ttknpdev.log.MyLog;
import ttknpdev.repositories.EmployeeRepository;
import ttknpdev.services.EmployeeService;
import ttknpdev.ui.frame.MyFrame;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class EmployeesTable extends MouseAdapter implements ActionListener {
    private class EmployeeModel extends AbstractTableModel {

        private List<Employee> employees;
        private String[] columnNames;
        private Class[] columnClass;

        public EmployeeModel(List<Employee> employees) {
            // can reduce
            columnNames = new String[6];
            columnNames[0] = "Identity";
            columnNames[1] = "Firstname";
            columnNames[2] = "Lastname";
            columnNames[3] = "Position";
            columnNames[4] = "Active";
            columnNames[5] = "Salary";

            columnClass = new Class[6];
            columnClass[0] = String.class;
            columnClass[1] = String.class;
            columnClass[2] = String.class;
            columnClass[3] = String.class;
            columnClass[4] = Boolean.class;
            columnClass[5] = Float.class;

            this.employees = employees;
        }

        public EmployeeModel() {
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            return columnClass[columnIndex];
        }

        @Override
        public int getRowCount() {
            return employees.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            Employee employee = employees.get(i);
            switch (i1) {
                case 0 -> {
                    return employee.getEid();
                }
                case 1 -> {
                    return employee.getFirstname();
                }
                case 2 -> {
                    return employee.getLastname();
                }
                case 3 -> {
                    return employee.getPosition();
                }
                case 4 -> {
                    return employee.getActive();
                }
                case 5 -> {
                    return employee.getSalary();
                }
                default -> {
                    return null;
                }
            }
        }

    }

    private JPanel panelEmployeeTable;
    private JLabel labelTitle;
    private JTable tableEmployees;
    private JScrollPane scrollPanelTable;
    private JButton buttonDelete;

    // my attributes on below
    private MyFrame frame;
    private EmployeeModel model;
    private Integer row;
    private MyLog myLog;
    private EmployeeRepository employeeRepository;
    private ImageIcon icon;
    private Image image;
    private Image newImg;

    public EmployeesTable() {

        myLog = new MyLog(EmployeesTable.class);
        frame = new MyFrame("Employees Table");
        frame.setVisible(true);
        frame.setSize(630, 565);
        frame.setContentPane(panelEmployeeTable);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        employeeRepository = new EmployeeService();

    }


    public void display() {

        /*List<Employee> employees = employeeRepository.reads();
        model = new EmployeeModel(employees);
        tableEmployees.setModel(model);*/
        setTable();

        tableEmployees.addMouseListener(this);
        buttonDelete.addActionListener(this);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closing to use this frame and connect database");
                employeeRepository.closeConnect();
            }
        });


    }

    @Override
    public void mouseClicked(MouseEvent event) { // this override method use for JTable when has event

        if (event.getClickCount() == 1) {  // 2 it means to detect double click events and 1 it means click
            /* Way to get row (order first zero) */
            JTable target = (JTable) event.getSource();
            row = target.getSelectedRow(); /* selected a row */
        }

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (row == null) {

            icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\warn.png");
            image = icon.getImage();
            newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newImg);
            JOptionPane.showMessageDialog(frame, "choose some row before delete", "warning", JOptionPane.INFORMATION_MESSAGE, icon);
            myLog.log4j.info("can't delete cause you didn't choose row");


        } else {

            String eid = model.employees.get(row).getEid(); // retrieve attribute Eid from row
            deleteByPrimaryKey(eid);

        }
    }

    private void setTable() {
        model= new EmployeeModel(employeeRepository.reads());
        tableEmployees.setModel(model);
        scrollPanelTable.getViewport().add(tableEmployees); // ** add component again
    }

    private void deleteByPrimaryKey(String eid) {


        Integer row = employeeRepository.delete(eid);

        if (row > 0) {

            setTable();
            myLog.log4j.info("deleted");

        } else if (row == 0) {

            icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
            image = icon.getImage();
            newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newImg);
            JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);

        }

    }
}
