package MVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class GeneralDriverView extends JFrame {
    private JLabel licenseLabel;
    private JLabel typeLabel;
    private JLabel ageLabel;
    private JLabel statusLabel;
    private JButton competencyButton;
    private boolean competencyTestStarted = false;
    private Model.Driver driver;
    
    //กำหนดค่าหน้าต่างแสดงข้อมูลของผู้ขับขี่ทั่วไป
    public GeneralDriverView(Model.Driver driver) {
        this.driver = driver;
        setTitle("Driver - General");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        licenseLabel = new JLabel("License Number: " + driver.getLicenseNumber());
        typeLabel = new JLabel("Type: General");
        ageLabel = new JLabel("Age: " + driver.getAge());
        statusLabel = new JLabel("Status: " + driver.getStatus());
        
        competencyButton = new JButton("Competency Test"); 
        
        //ตรวจสอบเงื่อนไขอายุของผู้ขับขี่
        if (driver.getAge() > 70) {
            driver.setStatus(Model.LicenseStatus.EXPIRED);
            statusLabel.setText("Status: " + driver.getStatus());
            competencyButton.setEnabled(false);
        } else if (driver.getAge() < 16) {
            driver.setStatus(Model.LicenseStatus.SUSPENDED);
            statusLabel.setText("Status: " + driver.getStatus());
            competencyButton.setEnabled(false);
        }
        
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.add(licenseLabel);
        panel.add(typeLabel);
        panel.add(ageLabel);
        panel.add(statusLabel);
        panel.add(competencyButton);
        
        add(panel);
    }
    
    //จัดการกับการคลิกปุ่ม
    public void addCompetencyListener(ActionListener listener) {
        competencyButton.addActionListener(listener);
    }
    
    //เปลี่ยนข้อความปุ่มตามสถานะการทดสอบ
    public void toggleCompetencyButton() {
        if (!competencyTestStarted) {
            competencyButton.setText("End Test");
            competencyTestStarted = true;
        } else {
            competencyButton.setText("Competency Test");
            competencyTestStarted = false;
        }
    }
}



