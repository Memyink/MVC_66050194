package MVC;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class NewbieDriverView extends JFrame {
    private JLabel licenseLabel;
    private JLabel typeLabel;
    private JLabel ageLabel;
    private JLabel statusLabel;
    private JButton writtenButton;
    private JButton practicalButton;
    private boolean writtenTestCompleted = false;
    private boolean practicalTestCompleted = false;
    private Model.Driver driver;
    
    public NewbieDriverView(Model.Driver driver) {
        this.driver = driver;
        setTitle("Driver - Newbie");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        licenseLabel = new JLabel("License Number: " + driver.getLicenseNumber());
        typeLabel = new JLabel("Type: Newbie");
        ageLabel = new JLabel("Age: " + driver.getAge());
        statusLabel = new JLabel("Status: " + driver.getStatus());
        
        writtenButton = new JButton("Written Test");
        practicalButton = new JButton("Practical Test");
        
        //ตรวจสอบเงื่อนไขอายุ
        if (driver.getAge() > 50) {
            driver.setStatus(Model.LicenseStatus.EXPIRED);
            statusLabel.setText("Status: " + driver.getStatus());
            writtenButton.setEnabled(false);
            practicalButton.setEnabled(false);
        } else if (driver.getAge() < 16) {
            driver.setStatus(Model.LicenseStatus.SUSPENDED);
            statusLabel.setText("Status: " + driver.getStatus());
            writtenButton.setEnabled(false);
            practicalButton.setEnabled(false);
        }
        
        JPanel panel = new JPanel(new GridLayout(6, 1));
        panel.add(licenseLabel);
        panel.add(typeLabel);
        panel.add(ageLabel);
        panel.add(statusLabel);
        panel.add(writtenButton);
        panel.add(practicalButton);
        
        add(panel);
    }
    
    public void addWrittenListener(ActionListener listener) {
        writtenButton.addActionListener(listener);
    }
    
    public void addPracticalListener(ActionListener listener) {
        practicalButton.addActionListener(listener);
    }
    
    public void toggleWrittenButton() {
        if (!writtenTestCompleted) {
            writtenButton.setText("End Written Test");
            writtenTestCompleted = true;
        } else {
            writtenButton.setText("Written Test");
            writtenTestCompleted = false;
        }
    }
    
    public void togglePracticalButton() {
        if (!practicalTestCompleted) {
            practicalButton.setText("End Practical Test");
            practicalTestCompleted = true;
        } else {
            practicalButton.setText("Practical Test");
            practicalTestCompleted = false;
        }
    }
    
    public boolean isWrittenTestCompleted() {
        return writtenTestCompleted;
    }
    
    public boolean isPracticalTestCompleted() {
        return practicalTestCompleted;
    }
}


    

