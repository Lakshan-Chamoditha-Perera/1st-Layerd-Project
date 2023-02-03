package lk.ijse.studentsmanagement.tblModels;

import com.jfoenix.controls.JFXButton;
public class InquiryTM {
    private String studentID;
    private String name ;
    private String city;
    private String email;
    private String mobile;
    private String date ;
    private String gender;
    private String status ;
    private JFXButton button;


    public InquiryTM(String studentID, String name, String city, String email, String mobile, String date, String gender, String status) {
        this.studentID = studentID;
        this.name = name;
        this.city = city;
        this.email = email;
        this.mobile = mobile;
        this.date = date;
        this.gender = gender;
        this.status = status;
       // this.button = button;
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
}
