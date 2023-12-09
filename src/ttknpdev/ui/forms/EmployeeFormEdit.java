package ttknpdev.ui.forms;

import ttknpdev.entities.Employee;
import ttknpdev.log.MyLog;
import ttknpdev.repositories.EmployeeRepository;
import ttknpdev.services.EmployeeService;
import ttknpdev.ui.frame.MyFrame;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class EmployeeFormEdit implements ActionListener {


    private class EmployeeModel extends AbstractTableModel {

        private List<Employee> employees;
        private final String[] columnNames;
        private final Class[] columnClass;

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

        public void setEmployees(List<Employee> employees) {
            // this.employees.remove(0);
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

    private JPanel panelEmployeeEdit;
    private JLabel labelTitle;
    private JScrollPane scrollPanelTable;
    private JTable tableEmployee;
    private JTextField textFieldEid;
    private JLabel labelFirstname;
    private JLabel labelLastname;
    private JTextField textFieldLastname;
    private JLabel labelPosition;
    private JTextField textFieldPosition;
    private JLabel labelSalary;
    private JTextField textFieldSalary;
    private JRadioButton radioButtonActive;
    private JButton buttonUpdate;
    private JLabel labelEid;
    private JTextField textFieldFirstname;
    private JButton buttonSearch;
    // my attributes
    private MyFrame frame;
    private EmployeeModel employeeModel;
    private MyLog myLog;
    private  EmployeeRepository<Employee> employeeRepository;

    public EmployeeFormEdit() {
        myLog = new MyLog(EmployeeFormEdit.class);
        frame = new MyFrame("Employee Form Edit");
        frame.setVisible(true);
        frame.setSize(695, 735);
        frame.setContentPane(panelEmployeeEdit);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setDefaultTable();
        /* when user don't find user id So user can't input any things */
        setEnabledFalse();
        employeeRepository = new EmployeeService();
    }

    public void display() {
        buttonSearch.addActionListener(this);
        buttonUpdate.addActionListener(this);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closing to use this frame and connect database");
                employeeRepository.closeConnect();
            }
        });
    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        myActionPerformed(actionEvent);
    }
    private void setEnabledFalse() {
        textFieldFirstname.setEnabled(false);
        textFieldSalary.setEnabled(false);
        textFieldPosition.setEnabled(false);
        textFieldLastname.setEnabled(false);
        radioButtonActive.setEnabled(false);
        buttonUpdate.setEnabled(false);
    }
    private void setEnableTrue() {
        textFieldFirstname.setEnabled(true);
        textFieldSalary.setEnabled(true);
        textFieldPosition.setEnabled(true);
        textFieldLastname.setEnabled(true);
        radioButtonActive.setEnabled(true);
        buttonUpdate.setEnabled(true);
    }
    private void setDefaultTable() {
        // I want to show my table First!
        employeeModel = new EmployeeModel(List.of(new Employee()));
        tableEmployee.setModel(employeeModel);
        scrollPanelTable.getViewport().add(tableEmployee);
    }

    private void myActionPerformed(ActionEvent actionEvent) {
        ImageIcon icon;
        Image image;
        Image newImg;
        Employee employee;
        String eid = textFieldEid.getText();

        if (actionEvent.getActionCommand().equals("Search")) {

            employee = employeeRepository.read(eid);

            if (employee != null) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\checked.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "selected", "message", JOptionPane.INFORMATION_MESSAGE, icon);

                // change employee from default it was null each attribute
                employeeModel.setEmployees(List.of(employee));
                tableEmployee.setModel(employeeModel);
                // *** updated table then update JScrollPanelTable
                scrollPanelTable.getViewport().add(tableEmployee);

                setEnableTrue();

                // set default value textField
                textFieldFirstname.setText(employee.getFirstname());
                textFieldLastname.setText(employee.getLastname());
                textFieldPosition.setText(employee.getPosition());
                radioButtonActive.setSelected(employee.getActive());
                textFieldSalary.setText(String.valueOf(employee.getSalary()));


            } else {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);


                textFieldFirstname.setText("");
                textFieldLastname.setText("");
                textFieldPosition.setText("");
                radioButtonActive.setSelected(false);
                textFieldSalary.setText("");

                setDefaultTable();
                setEnabledFalse();

            }

        } else if (actionEvent.getActionCommand().equals("Update")) {

            boolean active = radioButtonActive.isSelected();
            employee = new Employee(
                    textFieldEid.getText(),
                    textFieldFirstname.getText(),
                    textFieldLastname.getText(),
                    textFieldPosition.getText(),
                    active,
                    Float.valueOf(textFieldSalary.getText())
            );

            Integer result = employeeRepository.update(employee);

            if (result > 0) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\checked.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "updated", "message", JOptionPane.INFORMATION_MESSAGE, icon);

                // *** updated table
                employeeModel.setEmployees(List.of(employee));
                tableEmployee.setModel(employeeModel);
                scrollPanelTable.getViewport().add(tableEmployee);

            } else if (result == 0) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);
                myLog.log4j.info("failed to update");

            }

        }
    }

}
