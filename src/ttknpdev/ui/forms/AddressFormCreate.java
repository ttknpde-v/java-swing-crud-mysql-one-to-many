package ttknpdev.ui.forms;

import ttknpdev.entities.Address;
import ttknpdev.log.MyLog;
import ttknpdev.repositories.AddressRepository;
import ttknpdev.services.AddressService;
import ttknpdev.ui.frame.MyFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddressFormCreate implements ActionListener {
    private JLabel labelTitle;
    private JLabel labelAid;
    private JTextField textFieldAid;
    private JLabel labelCountry;
    private JTextField textFieldCountry;
    private JLabel labelCity;
    private JTextField textFieldCity;
    private JLabel labelDetails;
    private JTextField textFieldDetails;
    private JButton buttonCreate;
    private JPanel panelAddressCreate;
    // my attributes on below
    private MyFrame frame;
    private MyLog myLog;
    private AddressRepository<Address> addressRepository;
    private String eid;

    public AddressFormCreate(String eid) {
        myLog = new MyLog(AddressFormCreate.class);
        frame = new MyFrame("Address Form");
        frame.setVisible(true);
        frame.setSize(695, 445);
        frame.setContentPane(panelAddressCreate);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addressRepository = new AddressService();
        this.eid = eid;
    }

    public void display () {
        buttonCreate.addActionListener(this);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("closing to use this frame and connect database");
                addressRepository.closeConnect();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        myActionPerformed();
    }
    private void myActionPerformed () {

        ImageIcon icon;
        Image image;
        Image newImg;

        Address address = new Address(textFieldAid.getText(),textFieldCountry.getText(),textFieldCity.getText(),textFieldDetails.getText());
        // myLog.log4j.info(employee);
        Integer resultAddress = addressRepository.create(address);

        if (resultAddress > 0) { // can create address

            icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\checked.png");
            image = icon.getImage();
            newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newImg);
            JOptionPane.showMessageDialog(frame, "created", "message", JOptionPane.INFORMATION_MESSAGE, icon);

            Integer resultRelations = addressRepository.createRelations(eid,address.getAid());

            if (resultRelations > 0) { // can create relation

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\checked.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "relation created", "message", JOptionPane.INFORMATION_MESSAGE, icon);

            } else if (resultRelations == 0) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);

            }

        } else if (resultAddress == 0) {

            icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
            image = icon.getImage();
            newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newImg);
            JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);

        }

        setDefaultInput();

    }

    private void setDefaultInput() {
        textFieldAid.setText("");
        textFieldCity.setText("");
        textFieldCountry.setText("");
        textFieldDetails.setText("");
    }
}
