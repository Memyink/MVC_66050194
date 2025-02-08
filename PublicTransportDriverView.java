package MVC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PublicTransportDriverView extends JFrame {
    private JLabel licenseLabel;
    private JLabel typeLabel;
    private JLabel ageLabel;
    private JLabel statusLabel;
    private JLabel complaintLabel;
    private JButton trainingButton;
    private JButton competencyButton;
    private boolean trainingCompleted = false;
    private boolean competencyTestStarted = false;
    private Model.Driver driver;
    
    public PublicTransportDriverView(Model.Driver driver) {
        this.driver = driver;
        setTitle("Driver - Public Transport");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        licenseLabel = new JLabel("License Number: " + driver.getLicenseNumber());
        typeLabel = new JLabel("Type: Public Transport");
        ageLabel = new JLabel("Age: " + driver.getAge());
        statusLabel = new JLabel("Status: " + driver.getStatus());
        complaintLabel = new JLabel("Complaint Count: " + driver.getComplaintCount());
        
        trainingButton = new JButton("Training");
        competencyButton = new JButton("Competency Test");
        
        //ตรวจสอบเงื่อนไขอายุ
        if (driver.getAge() > 60) {
            driver.setStatus(Model.LicenseStatus.EXPIRED);
            statusLabel.setText("Status: " + driver.getStatus());
            trainingButton.setEnabled(false);
            competencyButton.setEnabled(false);
        } else if (driver.getAge() < 20) {
            driver.setStatus(Model.LicenseStatus.SUSPENDED);
            statusLabel.setText("Status: " + driver.getStatus());
            trainingButton.setEnabled(false);
            competencyButton.setEnabled(false);
        } else {
            if (driver.getComplaintCount() > 5) {
                //ถ้าร้องเรียนเกิน 5 ให้เปลี่ยนสถานะเป็น SUSPENDED ชั่วคราว
                driver.setStatus(Model.LicenseStatus.SUSPENDED);
                statusLabel.setText("Status: " + driver.getStatus());
                competencyButton.setEnabled(false); //ต้องผ่านการอบรมก่อน
                trainingButton.setEnabled(true);
            } else {
                //หากร้องเรียนน้อยหรือเท่ากับ 5 ไม่ต้องอบรม
                trainingButton.setEnabled(false);
                competencyButton.setEnabled(true);
            }
        }
        
        JPanel panel = new JPanel(new GridLayout(7, 1));
        panel.add(licenseLabel);
        panel.add(typeLabel);
        panel.add(ageLabel);
        panel.add(statusLabel);
        panel.add(complaintLabel);
        panel.add(trainingButton);
        panel.add(competencyButton);
        
        add(panel);
    }
    
    public void addTrainingListener(ActionListener listener) {
        trainingButton.addActionListener(listener);
    }
    
    public void addCompetencyListener(ActionListener listener) {
        competencyButton.addActionListener(listener);
    }
    
    public void toggleTrainingButton() {
        if (!trainingCompleted) {
            trainingButton.setText("End Training");
            trainingCompleted = true;
            //เมื่ออบรมเสร็จ เปลี่ยนสถานะกลับเป็น NORMAL และเปิดใช้งานปุ่มทดสอบสมรรถนะ
            driver.setStatus(Model.LicenseStatus.NORMAL);
            statusLabel.setText("Status: " + driver.getStatus());
            competencyButton.setEnabled(true);
        } else {
            trainingButton.setText("Training");
            trainingCompleted = false;
        }
    }
    
    public void toggleCompetencyButton() {
        if (!competencyTestStarted) {
            competencyButton.setText("End Competency Test");
            competencyTestStarted = true;
        } else {
            competencyButton.setText("Competency Test");
            competencyTestStarted = false;
        }
    }
}

    

