package MVC;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Control {
    private Model.DriverDatabase db;
    private SearchView searchView;
    
    public Control() {
        db = new Model.DriverDatabase();
        searchView = new SearchView();
        initSearchView();
    }
    
    private void initSearchView() { //
        searchView.addSearchListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String licenseInput = searchView.getLicenseInput();
                //ตรวจสอบความถูกต้องของหมายเลขใบขับขี่ (9 หลัก ตัวแรกต้องไม่เป็น 0)
                if (!licenseInput.matches("^[1-9]\\d{8}$")) {
                    searchView.setMessage("Invalid license number"); //ขึ้นข้อความนี้หากหมายเลขใบขับขี่ไม่ถูกต้อง 
                    return;
                }
                
                Model.Driver driver = db.getDriverByLicense(licenseInput);
                if (driver == null) {
                    searchView.setMessage("Driver not found"); // ขึ้นข้อความว่าไม่พบผู้ขับขี่
                    return;
                }
                
                //เปิดหน้าจอตามประเภทของผู้ขับขี่
                if (driver.getDriverType() == Model.DriverType.GENERAL) {
                    GeneralDriverView generalView = new GeneralDriverView(driver);
                    generalView.setVisible(true);
                    generalView.addCompetencyListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            generalView.toggleCompetencyButton();
                        }
                    });
                } else if (driver.getDriverType() == Model.DriverType.NEWBIE) {
                    //ผู้ขับขี่มือใหม่
                    NewbieDriverView newbieView = new NewbieDriverView(driver);
                    newbieView.setVisible(true);
                    newbieView.addWrittenListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            newbieView.toggleWrittenButton();
                            checkNewbieCompletion(newbieView, driver);
                        }
                    });
                    newbieView.addPracticalListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            newbieView.togglePracticalButton();
                            checkNewbieCompletion(newbieView, driver);
                        }
                    });
                } else if (driver.getDriverType() == Model.DriverType.PUBLIC) {
                    //ผู้ขับขี่ขนส่งสาธารณะ
                    PublicTransportDriverView publicView = new PublicTransportDriverView(driver);
                    publicView.setVisible(true);
                    publicView.addTrainingListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            publicView.toggleTrainingButton();
                        }
                    });
                    publicView.addCompetencyListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            publicView.toggleCompetencyButton();
                        }
                    });
                }
            }
        });
        
        //เมนูสำหรับเปิดหน้ารายงาน
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu"); 
        JMenuItem reportItem = new JMenuItem("Report");
        reportItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ReportView reportView = new ReportView(db);
                reportView.setVisible(true);
            }
        });
        menu.add(reportItem);
        menuBar.add(menu);
        searchView.setJMenuBar(menuBar);
        
        searchView.setVisible(true);
    }
    
    //method ตรวจสอบการสอบของผู้ขับขี่มือใหม่ที่สอบข้อเขียนและสอบปฏิบัติแล้ว
    private void checkNewbieCompletion(NewbieDriverView newbieView, Model.Driver driver) {
        if (newbieView.isWrittenTestCompleted() && newbieView.isPracticalTestCompleted()) {
            //เปลี่ยนประเภทจาก มือใหม่ เป็น บุคคลทั่วไป
            driver.setDriverType(Model.DriverType.GENERAL);
            JOptionPane.showMessageDialog(newbieView, "Exams completed\nDriver type changed to General");
            newbieView.dispose();
            GeneralDriverView generalView = new GeneralDriverView(driver);
            generalView.setVisible(true);
            generalView.addCompetencyListener(e -> generalView.toggleCompetencyButton());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Control();
            }
        });
    }
}