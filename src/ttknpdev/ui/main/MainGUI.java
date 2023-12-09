package ttknpdev.ui.main;

import ttknpdev.log.MyLog;
import ttknpdev.ui.forms.AddressFormEdit;
import ttknpdev.ui.forms.EmployeeFormCreate;
import ttknpdev.ui.forms.EmployeeFormEdit;
import ttknpdev.ui.frame.MyFrame;
import ttknpdev.ui.tables.AddressesTable;
import ttknpdev.ui.tables.EmployeesTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainGUI implements ActionListener {
    private JPanel panelMainGUI;
    private JButton buttonBooksTable;
    private JLabel labelTitle;
    private JButton buttonBookEdit;
    private JButton buttonBookCreate;
    private JButton buttonAddressEdit;
    private JButton buttonAddressesTable;

    // my attributes on below
    private MyFrame frame;
    private MyLog myLog;
    private EmployeesTable employeesTable;
    private EmployeeFormCreate employeeFormCreate;
    private EmployeeFormEdit employeeFormEdit;
    private AddressesTable addressesTable;
    private AddressFormEdit addressFormEdit;

    public MainGUI() {

        frame = new MyFrame("Options Java swing GUI");
        frame.setVisible(true);
        frame.setSize(630, 565);
        frame.setContentPane(panelMainGUI);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        myLog = new MyLog(MainGUI.class);

    }

    public void display() {
        buttonAddressesTable.addActionListener(this);
        buttonAddressEdit.addActionListener(this);
        buttonBookCreate.addActionListener(this);
        buttonBookEdit.addActionListener(this);
        buttonBooksTable.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        if (actionEvent.getActionCommand().equals("Book Edit")) {
            myLog.log4j.info("Book Edit");
            employeeFormEdit = new EmployeeFormEdit();
            employeeFormEdit.display();
        }
        else if (actionEvent.getActionCommand().equals("Books Table")) {
            myLog.log4j.info("Books TB");
            employeesTable = new EmployeesTable();
            employeesTable.display();
        }
        else if (actionEvent.getActionCommand().equals("Create")) {
            myLog.log4j.info("Create");
            employeeFormCreate = new EmployeeFormCreate();
            employeeFormCreate.display();
        }
        else if (actionEvent.getActionCommand().equals("Address Edit")) {
            myLog.log4j.info("Ad. Edit");
            addressFormEdit = new AddressFormEdit();
            addressFormEdit.display();

        }
        else if (actionEvent.getActionCommand().equals("Address(es) Table")) {
            myLog.log4j.info("Ad. TB");
            addressesTable = new AddressesTable();
            addressesTable.display();
        }
    }
}
