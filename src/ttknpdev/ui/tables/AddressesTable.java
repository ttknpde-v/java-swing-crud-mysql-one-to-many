package ttknpdev.ui.tables;

import ttknpdev.entities.Address;
import ttknpdev.entities.Employee;
import ttknpdev.log.MyLog;
import ttknpdev.repositories.AddressRepository;
import ttknpdev.services.AddressService;
import ttknpdev.ui.frame.MyFrame;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class AddressesTable extends MouseAdapter implements ActionListener {
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

    private JPanel panelAddressesTable;
    private JLabel labelTitle;
    private JLabel labelEid;
    private JTextField textFieldEid;
    private JLabel labelFirstnameLastname;
    private JScrollPane scrollPanelTable;
    private JTable tableAddresses;
    private JButton buttonSearch;
    private JButton buttonDelete;
    //
    private MyFrame frame;
    private MyLog myLog;
    private AddressesModel addressesModel;
    private AddressRepository<Address> addressRepository;
    private Integer row;
    private ImageIcon icon;
    private Image image;
    private Image newImg;

    public AddressesTable() {
        frame = new MyFrame("Address(es) Table");
        frame.setVisible(true);
        frame.setSize(990, 720);
        frame.setContentPane(panelAddressesTable);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        myLog = new MyLog(AddressesTable.class);
        buttonDelete.setEnabled(false); // must click search bt before delete
        // I want to show my table First!
        addressesModel = new AddressesModel(List.of(new Address()));
        tableAddresses.setModel(addressesModel);
        addressRepository = new AddressService();
    }

    public void display() {
        buttonSearch.addActionListener(this);
        buttonDelete.addActionListener(this);
        tableAddresses.addMouseListener(this);
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

        if (actionEvent.getActionCommand().equals("Search")) {
            // myLog.log4j.info("search");
            Employee employee = addressRepository.readsByEid(textFieldEid.getText());

            if (employee != null) {

                labelFirstnameLastname.setText("Firstname " + employee.getFirstname() + "  Lastname " + employee.getLastname());
                buttonDelete.setEnabled(true);
                List<Address> addresses = employee.getAddresses();

                /* update table and scroll panel */
                addressesModel.setAddresses(addresses);
                tableAddresses.setModel(addressesModel);
                scrollPanelTable.getViewport().add(tableAddresses);

            } else {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);
                addressesModel.setAddresses(List.of(new Address("", "", "", "")));
                tableAddresses.setModel(addressesModel);
                scrollPanelTable.getViewport().add(tableAddresses);
                labelFirstnameLastname.setText("XXX XXX");

            }
        } else if (actionEvent.getActionCommand().equals("Delete")) {
            // myLog.log4j.info("delete");
            if (row == null) {

                icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\warn.png");
                image = icon.getImage();
                newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                icon = new ImageIcon(newImg);
                JOptionPane.showMessageDialog(frame, "choose some row before delete", "warning", JOptionPane.INFORMATION_MESSAGE, icon);
                myLog.log4j.info("can't delete cause you didn't choose row");


            } else {
                /* You know row than you know aid */
                try {
                    deleteByPrimaryKey(addressesModel.addresses.get(row).getAid());
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
                    image = icon.getImage();
                    newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
                    icon = new ImageIcon(newImg);
                    JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);
                }
            }

        }
    }

    private void deleteByPrimaryKey(String aid) {


        Integer row = addressRepository.delete(aid );

        if (row > 0) {

            Employee employee = addressRepository.readsByEid(textFieldEid.getText());
            if (employee != null) {
                addressesModel.setAddresses(employee.getAddresses());
                tableAddresses.setModel(addressesModel);
                // myLog.log4j.info("deleted");
            } else {
                addressesModel.setAddresses(List.of(new Address("", "", "", "")));
                tableAddresses.setModel(addressesModel);
            }
            scrollPanelTable.getViewport().add(tableAddresses); // ** add component again

        } else if (row == 0) {

            icon = new ImageIcon("B:\\practice-java-one\\LearnJavaCore\\java-swing-crud-mysql-one-to-many\\images\\remove.png");
            image = icon.getImage();
            newImg = image.getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
            icon = new ImageIcon(newImg);
            JOptionPane.showMessageDialog(frame, "failed", "message", JOptionPane.INFORMATION_MESSAGE, icon);

        }

    }

    @Override
    public void mouseClicked(MouseEvent event) { // this override method use for JTable when has event

        if (event.getClickCount() == 1) {  // 2 it means to detect double click events and 1 it means click
            /* Way to get row (order first zero) */
            JTable target = (JTable) event.getSource();
            row = target.getSelectedRow(); /* selected a row */
        }

    }
}
