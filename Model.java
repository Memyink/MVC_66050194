package MVC;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Model {

    //Enum สำหรับประเภทผู้ขับขี่
    public enum DriverType {
        GENERAL,   //บุคคลทั่วไป
        NEWBIE,    //มือใหม่
        PUBLIC     //คนขับรถสาธารณะ
    }

    //Enum สำหรับสถานะใบขับขี่
    public enum LicenseStatus {
        NORMAL,    //ปกติ
        EXPIRED,   //หมดอายุ
        SUSPENDED  //ถูกระงับ
    }

    //class Driver สำหรับเก็บข้อมูลผู้ขับขี่
    public static class Driver {
        private String licenseNumber;    //หมายเลขใบขับขี่ 
        private DriverType driverType;     //ประเภทของผู้ขับขี่
        private LocalDate birthDate;       //วันเกิด
        private LicenseStatus status;      //สถานะของใบขับขี่
        private int complaintCount;        //จำนวนร้องเรียน 

        public Driver(String licenseNumber, DriverType driverType, LocalDate birthDate, LicenseStatus status) {
            this.licenseNumber = licenseNumber;
            this.driverType = driverType;
            this.birthDate = birthDate;
            this.status = status;
            this.complaintCount = 0;
        }

        //Getter Setter method
        public String getLicenseNumber() {
            return licenseNumber;
        }

        public DriverType getDriverType() {
            return driverType;
        }

        public void setDriverType(DriverType driverType) {
            this.driverType = driverType;
        }

        public LocalDate getBirthDate() {
            return birthDate;
        }

        public LicenseStatus getStatus() {
            return status;
        }

        public void setStatus(LicenseStatus status) {
            this.status = status;
        }

        public int getComplaintCount() {
            return complaintCount;
        }

        public void setComplaintCount(int complaintCount) {
            this.complaintCount = complaintCount;
        }

        //คำนวณอายุจากวันเกิด
        public int getAge() {
            return Period.between(birthDate, LocalDate.now()).getYears();
        }
    }

    //DriverDatabase จำลองฐานข้อมูลผู้ขับขี่
    public static class DriverDatabase {
        private List<Driver> drivers;

        public DriverDatabase() {
            drivers = new ArrayList<>();
            populateSampleData();
        }

        //สร้างข้อมูลตัวอย่างอย่างน้อย 50 รายการ
        private void populateSampleData() {
            Random rand = new Random();
            //บุคคลทั่วไป 20 รายการ
            for (int i = 0; i < 20; i++) {
                String license = generateLicenseNumber(i + 100000000);
                int age = rand.nextInt(55) + 16; //อายุ 16-70
                LocalDate birthDate = LocalDate.now().minusYears(age).minusDays(rand.nextInt(365));
                Driver driver = new Driver(license, DriverType.GENERAL, birthDate, LicenseStatus.NORMAL);
                drivers.add(driver);
            }
            //มือใหม่ 15 รายการ
            for (int i = 0; i < 15; i++) {
                String license = generateLicenseNumber(i + 200000000);
                int age = rand.nextInt(35) + 16; //อายุ 16-50
                LocalDate birthDate = LocalDate.now().minusYears(age).minusDays(rand.nextInt(365));
                Driver driver = new Driver(license, DriverType.NEWBIE, birthDate, LicenseStatus.NORMAL);
                drivers.add(driver);
            }
            //คนขับรถสาธารณะ 15 รายการ
            for (int i = 0; i < 15; i++) {
                String license = generateLicenseNumber(i + 300000000);
                int age = rand.nextInt(41) + 20; //อายุ 20-60
                LocalDate birthDate = LocalDate.now().minusYears(age).minusDays(rand.nextInt(365));
                Driver driver = new Driver(license, DriverType.PUBLIC, birthDate, LicenseStatus.NORMAL);
                int complaints = rand.nextInt(11); //จำนวนร้องเรียน 0-10
                driver.setComplaintCount(complaints);
                drivers.add(driver);
            }
        }

        //สร้างหมายเลขใบขับขี่ 9 หลัก โดยที่ตัวแรกไม่เป็น 0
        private String generateLicenseNumber(int base) {
            String s = String.valueOf(base);
            while (s.length() < 9) {
                s = "1" + s;
            }
            return s;
        }

        //ค้นหาผู้ขับขี่จากหมายเลขใบขับขี่
        public Driver getDriverByLicense(String licenseNumber) {
            for (Driver d : drivers) {
                if (d.getLicenseNumber().equals(licenseNumber)) {
                    return d;
                }
            }
            return null;
        }

        public List<Driver> getDrivers() {
            return drivers;
        }

        //นับจำนวนผู้ขับขี่ตามประเภท
        public int countByType(DriverType type) {
            int count = 0;
            for (Driver d : drivers) {
                if (d.getDriverType() == type) {
                    count++;
                }
            }
            return count;
        }

        //นับจำนวนผู้ขับขี่ตามสถานะในแต่ละประเภท
        public int countByStatus(LicenseStatus status, DriverType type) {
            int count = 0;
            for (Driver d : drivers) {
                if (d.getStatus() == status && d.getDriverType() == type) {
                    count++;
                }
            }
            return count;
        }
    }
}