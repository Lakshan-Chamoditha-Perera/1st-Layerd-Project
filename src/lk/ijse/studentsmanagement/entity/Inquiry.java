package lk.ijse.studentsmanagement.entity;

public class Inquiry implements SuperEntity{
    private String studentID;
    private String name ;
    private String city;
    private String email;
    private String mobile;
    private String date ;
    private String gender;
    private String status ;

    private TestPayment testPayment;

    public Inquiry(String studentID, String name, String city, String email, String mobile, String date, String gender, String status, TestPayment testPayment) {
        this.studentID = studentID;
        this.name = name;
        this.city = city;
        this.email = email;
        this.mobile = mobile;
        this.date = date;
        this.gender = gender;
        this.status = status;
        this.testPayment = testPayment;
    }
    public String getStudentID() {
        return studentID;
    }
    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }
    public String getEmail() {
        return email;
    }
    public String getMobile() {
        return mobile;
    }
    public String getDate() {
        return date;
    }
    public String getGender() {
        return gender;
    }
    public String getStatus() {
        return status;
    }
    public TestPayment getTestPayment() {
        return testPayment;
    }

    public Inquiry(String studentID) {
        this.studentID = studentID;
    }
    public Inquiry(String studentID, String email) {
        this.studentID = studentID;
        this.email = email;
    }
    public Inquiry(String studentID, String name, String city, String email, String mobile, String gender) {
        this.studentID = studentID;
        this.name = name;
        this.city = city;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
    }
    public Inquiry(String studentID, String name, String city, String email, String mobile, String date, String gender, String status) {
        this.studentID = studentID;
        this.name = name;
        this.city = city;
        this.email = email;
        this.mobile = mobile;
        this.date = date;
        this.gender = gender;
        this.status = status;
    }
    @Override
    public String toString() {
        return "Inquiry{" +
                "studentID='" + studentID + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", date='" + date + '\'' +
                ", gender='" + gender + '\'' +
                ", status='" + status + '\'' +
                ", testPayment=" + testPayment +
                '}';
    }
}