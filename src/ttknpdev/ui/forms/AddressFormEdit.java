package ttknpdev.ui.forms;

import ttknpdev.entities.Address;
import ttknpdev.entities.Employee;
import ttknpdev.log.MyLog;
import ttknpdev.repositories.AddressRepository;
import ttknpdev.services.AddressService;
import ttknpdev.ui.frame.MyFrame;
import ttknpdev.ui.tables.AddressesTable;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class AddressFormEdit implements ActionListener {


    private class AddressesModel extends AbstractTableModel {

        private List<Address> addresses;
        private final String[] columnNames;
        private final Class[] columnClass;

        public AddressesModel(List<Address> addresses) {
            // can reduce
            columnNames = new String[4];
            columnNames[0] = "Identity";
            columnNames[1] = "Country";
            columnNames[2] = "City";
            columnNames[3] = "Details";


            columnClass = new Class[4];
            columnClass[0] = String.class;
            columnClass[1] = String.class;
            columnClass[2] = String.class;
            columnClass[3] = String.class;


            this.addresses = addresses;
        }

        public void setAddresses(List<Address> addresses) {
            // this.employees.remove(0);
            this.addresses = addresses;
            System.out.println(this.addresses);
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
            return addresses.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int i, int i1) {
            Address address = addresses.get(i);
            switch (i1) {
                case 0 -> {
                    return address.getAid();
                }
                case 1 -> {
                    return address.getCountry();
                }
                case 2 -> {
                    return address.getCity();
                }
                case 3 -> {
                    return address.getDetails();
                }
                default -> {
                    return null;
                }
            }
        }

        /*public String getAid (int row) {
            return addresses.get(row).getAid();
        }*/

    }
    private JPanel panelAddressFormEdit;
    private JLabel labelTitle;
    private JLabel labelAid;
    private JTextField textFieldAid;
    private JButton buttonSearch;
    private JScrollPane scrollPanelTable;
    private JTable tableAddress;
    private JLabel labelCountry;
    private JTextField textFieldCountry;
    private JLabel labelCity;
    private JTextField textFieldCity;
    private JLabel labelDetails;
    private JTextField textFieldDetails;
    private JButton buttonUpdate;

    //
    private MyFrame frame;
    private MyLog myLog;
    private AddressesModel addressesModel;
    private AddressRepository<Address> addressRepository;

    public AddressFormEdit() {
        frame = new MyFrame("Address Form Edit");
        frame.setVisible(true);
        frame.setSize(695, 735);
        frame.setContentPane(panelAddressFormEdit);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myLog = new MyLog(AddressFormEdit.class);
        setDefaultTable();
        setEnabledFalse();
        addressRepository = new AddressService();
    }
    private void setEnabledFalse() {
        textFieldCity.setEnabled(false);
        textFieldCountry.setEnabled(false);
        textFieldDetails.setEnabled(false);
        buttonUpdate.setEnabled(false);
    }

    private void setEnabledTrue() {
        textFieldCity.setEnabled(true);
        textFieldCountry.setEnabled(true);
        textFieldDetails.setEnabled(true);
        buttonUpdate.setEnabled(true);
    }
    public void display() {
        buttonSearch.addActionListener(this);
        buttonUpdate.addActionListener(this);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                addressRepository.closeConnect();
                System.out.println("closing to use this frame and connect database");
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        myActionPerformed(actionEvent);
    }
    private void setDefaultTable() {
        // I want to show my table First!
        addressesModel = new AddressesModel(List.of(new Address()));
        tableAddress.setModel(addressesModel);
        scrollPanelTable.getViewport().add(tableAddress);
    }
    private void myActionPerformed(ActionEvent actionEvent) {
        ImageIcon icon;
        Image image;
        Image newImg;
        Address address;
        String aid = textFieldAid.getText();
        if (actionEvent.getActionCommand().equals("Search")) {
            //
            address = addressRepository.read(aid);

            if (address != null) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\checked.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "selected", "message", JOptionPane.INFORMATION_MESSAGE, icon);

                setEnabledTrue();
                addressesModel.setAddresses(List.of(address));
                tableAddress.setModel(addressesModel);
                scrollPanelTable.getViewport().add(tableAddress);

                textFieldCountry.setText(address.getCountry());
                textFieldCity.setText(address.getCity());
                textFieldDetails.setText(address.getDetails());

            } else {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);

                setEnabledFalse();
                setDefaultTable();

                textFieldCountry.setText("");
                textFieldCity.setText("");
                textFieldDetails.setText("");

            }

        } else if (actionEvent.getActionCommand().equals("Update")) {

            address = new Address(aid,textFieldCountry.getText(),textFieldCity.getText(),textFieldDetails.getText());
            Integer result = addressRepository.update(address);

            if (result > 0) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\checked.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "updated", "message", JOptionPane.INFORMATION_MESSAGE, icon);

                // *** updated table
                addressesModel.setAddresses(List.of(address));
                tableAddress.setModel(addressesModel);
                scrollPanelTable.getViewport().add(tableAddress);

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
