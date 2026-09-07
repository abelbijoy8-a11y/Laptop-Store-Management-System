package project;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;
import javax.swing.JScrollPane;

public class mainpage extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private Connection conn;
    static Scanner scanner = new Scanner(System.in);
    
    public void setConnection(Connection conn) {
        this.conn=conn;
    }

    public mainpage() {
        setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Laptop ID");
        lblNewLabel.setBounds(10, 51, 86, 12);
        add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("Model Name");
        lblNewLabel_1.setBounds(10, 88, 86, 12);
        add(lblNewLabel_1);
        
        JLabel lblNewLabel_2 = new JLabel("Price");
        lblNewLabel_2.setBounds(10, 130, 44, 12);
        add(lblNewLabel_2);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(310, 10, 405, 273);
        add(scrollPane);
        
        table = new JTable();
        scrollPane.setViewportView(table);
        table.setModel(new DefaultTableModel(
            new Object[][] {},
            new String[] {
                "Laptop ID", "Model Name", "Price", "Brand"
            }
        ));
        
        textField = new JTextField();
        textField.setBounds(106, 48, 178, 18);
        add(textField);
        textField.setColumns(10);
        
        textField_1 = new JTextField();
        textField_1.setBounds(106, 85, 178, 18);
        add(textField_1);
        textField_1.setColumns(10);
        
        textField_2 = new JTextField();
        textField_2.setBounds(106, 127, 178, 18);
        add(textField_2);
        textField_2.setColumns(10);
        
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[] {"HP", "Dell", "Lenovo", "Asus"}));
        comboBox.setBounds(106, 171, 178, 20);
        add(comboBox);
        
        JLabel lblNewLabel_3 = new JLabel("Brand");
        lblNewLabel_3.setBounds(10, 175, 64, 12);
        add(lblNewLabel_3);
        
        JLabel lblNewLabel_4 = new JLabel("");
        lblNewLabel_4.setBounds(92, 271, 129, 12);
        add(lblNewLabel_4);
        
        JButton btnNewButton = new JButton("Add");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int laptopId = Integer.parseInt(textField.getText());
                    String modelName = textField_1.getText();
                    double price = Double.parseDouble(textField_2.getText());
                    int brandId = comboBox.getSelectedIndex() + 1;
                    
                    if(addLaptop(laptopId, brandId, modelName, price) == 1) {
                        lblNewLabel_4.setText("New laptop added");
                    } else {
                        lblNewLabel_4.setText("Error adding laptop");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric values for ID and Price.");
                }
            }
        });
        btnNewButton.setBounds(10, 241, 84, 20);
        add(btnNewButton);
        
        JButton btnNewButton_1 = new JButton("Update");
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int laptopId = Integer.parseInt(textField.getText());
                    String modelName = textField_1.getText();
                    double price = Double.parseDouble(textField_2.getText());
                    int brandId = comboBox.getSelectedIndex() + 1;
                    
                    if(updateLaptop(price, modelName, brandId, laptopId) == 1) {
                        lblNewLabel_4.setText("Laptop updated");
                    } else {
                        lblNewLabel_4.setText("Error updating laptop");
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numeric values for ID and Price.");
                }
            }
        });
        btnNewButton_1.setBounds(106, 241, 84, 20);
        add(btnNewButton_1);
        
        JButton btnNewButton_3 = new JButton("Refresh Table");
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String tblquery = "SELECT l.laptop_id, l.model_name, l.price, b.brand_name " +
                            "FROM Laptops l " +
                            "INNER JOIN Brands b ON l.brand_id = b.brand_id";
                    PreparedStatement pst = conn.prepareStatement(tblquery);
                    ResultSet rs = pst.executeQuery();
                    table.setModel(DbUtils.resultSetToTableModel(rs));
                } catch(Exception e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
        btnNewButton_3.setBounds(172, 10, 114, 20);
        add(btnNewButton_3);
        
        JButton btnNewButton_2 = new JButton("Delete");
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    int laptopId = Integer.parseInt(textField.getText());
                    if(deleteLaptop(laptopId) == 1) {
                        lblNewLabel_4.setText("Laptop deleted");
                    } else {
                        lblNewLabel_4.setText("Error deleting laptop");
                    }
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid numeric Laptop ID.");
                }
            }
        });
        btnNewButton_2.setBounds(200, 241, 84, 20);
        add(btnNewButton_2);
    }
    
    public int addLaptop(int id, int brandId, String model, double price) {
        try {
            String sql = "INSERT INTO Laptops VALUES (?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setString(2, model);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, brandId);
            
            pstmt.executeUpdate();
            System.out.println("Laptop added!");
            return 1;
        } catch (Exception e) {
            System.out.println("Error adding: " + e.getMessage());
            JOptionPane.showMessageDialog(mainpage.this,
                    "Error adding laptop: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    public int updateLaptop(double newPrice, String newModel, int newBrand, int id) {
        try {
            String sql = "UPDATE Laptops SET price = ?, model_name = ?, brand_id = ? WHERE laptop_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setDouble(1, newPrice);
            pstmt.setString(2, newModel);
            pstmt.setInt(3, newBrand);
            pstmt.setInt(4, id);
            
            pstmt.executeUpdate();
            System.out.println("Laptop updated!");
            return 1;
        } catch (Exception e) {
            System.out.println("Error updating: " + e.getMessage());
            JOptionPane.showMessageDialog(mainpage.this,
                    "Error updating laptop: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
    
    public int deleteLaptop(int id) {
        try {
            String sql = "DELETE FROM Laptops WHERE laptop_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return 1;
        } catch(Exception e) {
            System.out.println("Error deleting: " + e.getMessage());
            JOptionPane.showMessageDialog(mainpage.this,
                    "Error deleting laptop: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            return 0;
        }
    }
}