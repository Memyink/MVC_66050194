package MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SearchView extends JFrame {
    private JTextField licenseField;
    private JButton searchButton;
    private JLabel messageLabel;

    //กำหนดค่าหน้าต่างค้นหาผู้ขับขี่
    public SearchView() {
        setTitle("Driver Search");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        licenseField = new JTextField(15);
        searchButton = new JButton("Search");
        messageLabel = new JLabel("");

        JPanel panel = new JPanel(new GridLayout(3, 1));
        panel.add(new JLabel("License Number:"));
        panel.add(licenseField);
        panel.add(searchButton);

        add(panel, BorderLayout.CENTER);
        add(messageLabel, BorderLayout.SOUTH);
    }

    public String getLicenseInput() {
        return licenseField.getText().trim();
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void addSearchListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
}

