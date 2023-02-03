package lk.ijse.studentsmanagement.dto;

import lk.ijse.studentsmanagement.entity.SuperEntity;

public class InquiryDTO implements SuperDTO {
    private String studentID;
    private String name ;
    private String city;
    private String email;
    private String mobile;
    private String date ;
    private String gender;
    private String status ;

    private TestPaymentDTO testPayment;

    public InquiryDTO(String studentID, String name, String city, String email, String mobile, String date, String gender, String status, TestPaymentDTO testPayment) {
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
    public TestPaymentDTO getTestPayment() {
        return testPayment;
    }
    public InquiryDTO(String studentID) {
        this.studentID = studentID;
    }
    public InquiryDTO(String studentID, String email) {
        this.studentID = studentID;
        this.email = email;
    }
    public InquiryDTO(String studentID, String name, String city, String email, String mobile, String gender) {
        this.studentID = studentID;
        this.name = name;
        this.city = city;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
    }
    public InquiryDTO(String studentID, String name, String city, String email, String mobile, String date, String gender, String status) {
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