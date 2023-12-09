package ttknpdev.ui.forms;

import ttknpdev.entities.Employee;
import ttknpdev.log.MyLog;
import ttknpdev.repositories.EmployeeRepository;
import ttknpdev.services.EmployeeService;
import ttknpdev.ui.frame.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class EmployeeFormCreate implements ActionListener {
    private JLabel labelTitle;
    private JLabel labelEid;
    private JTextField textFieldEid;
    private JLabel labelFirstname;
    private JTextField textFieldFirstname;
    private JLabel labelLastname;
    private JTextField textFieldLastname;
    private JLabel labelPosition;
    private JTextField textFieldPosition;
    private JRadioButton radioButtonActive;
    private JLabel labelSalary;
    private JTextField textFieldSalary;
    private JButton buttonCreate;
    private JPanel panelEmployeeCreate;

    // my attributes on below
    private MyFrame frame;
    private MyLog myLog;
    private AddressFormCreate addressFormCreate;
    private EmployeeRepository<Employee> employeeRepository;

    public EmployeeFormCreate() {
        myLog = new MyLog(EmployeeFormCreate.class);
        frame = new MyFrame("Employee Form");
        frame.setVisible(true);
        frame.setSize(695, 565);
        frame.setContentPane(panelEmployeeCreate);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        employeeRepository = new EmployeeService();
    }

    public void display() {
        buttonCreate.addActionListener(this);
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
       myActionPerformed();
    }
    private void myActionPerformed () {
        int confirmDialog = JOptionPane.showConfirmDialog(frame, "Do you want to create address?", "message", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        ImageIcon icon;
        Image image;
        Image newImg;
        boolean active;

        // yes = 0 , no = 1
        if (confirmDialog == 0) {

            active = radioButtonActive.isSelected();

            Employee employee = new Employee(
                    textFieldEid.getText(),
                    textFieldFirstname.getText(),
                    textFieldLastname.getText(),
                    textFieldPosition.getText(),
                    active,
                    Float.valueOf(textFieldSalary.getText())
            );

            Integer resultEmployee = employeeRepository.create(employee);

            if (resultEmployee > 0) { // can create employee

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\checked.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "created", "message", JOptionPane.INFORMATION_MESSAGE, icon);

                // create address form create frame
                addressFormCreate = new AddressFormCreate(employee.getEid());
                addressFormCreate.display();
                // remember when line 96 is working  it will go to line 110
                myLog.log4j.info("created employee then create address");

            } else if (resultEmployee == 0) { // can't create employee

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);

            }

            setDefaultInput();


        } else { // if no can create employee only

            active = radioButtonActive.isSelected();

            Employee employee = new Employee(
                    textFieldEid.getText(),
                    textFieldFirstname.getText(),
                    textFieldLastname.getText(),
                    textFieldPosition.getText(),
                    active,
                    Float.valueOf(textFieldSalary.getText())
            );

            Integer result = employeeRepository.create(employee);

            if (result > 0) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\checked.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "created", "message", JOptionPane.INFORMATION_MESSAGE, icon);
                // myLog.log4j.info("can't delete cause you didn't choose row");

            } else if (result == 0) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);

            }

            setDefaultInput();

        }
    }
    private void setDefaultInput () {
        textFieldFirstname.setText("");
        textFieldLastname.setText("");
        textFieldEid.setText("");
        textFieldSalary.setText("");
        textFieldPosition.setText("");
        radioButtonActive.setSelected(false);
    }
}
