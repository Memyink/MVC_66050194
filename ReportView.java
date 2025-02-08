package MVC;

import javax.swing.*;
import java.awt.*;

public class ReportView extends JFrame {
    private JTextArea reportArea;
    
    //กำหนดค่าหน้าต่างการรายงานและแสดงผลรายงาน
    public ReportView(Model.DriverDatabase db) {
        setTitle("Driver Report");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        
        JScrollPane scrollPane = new JScrollPane(reportArea);
        add(scrollPane, BorderLayout.CENTER);
        
        generateReport(db);
    }
    
    //สร้างรายงานที่แสดงข้อมูลจำนวนผู้ขับขี่ตามประเภทและสถานะของใบขับขี่
    private void generateReport(Model.DriverDatabase db) {
        StringBuilder sb = new StringBuilder();
        sb.append("Driver Report\n");
        for (Model.DriverType type : Model.DriverType.values()) {
            sb.append("Type: " + type + "\n");
            int total = db.countByType(type);
            sb.append("Total: " + total + "\n");
            for (Model.LicenseStatus status : Model.LicenseStatus.values()) {
                int count = db.countByStatus(status, type);
                sb.append("Status " + status + ": " + count + "\n");
            }
            sb.append("\n");
        }
        reportArea.setText(sb.toString());
    }
}
    

