package lk.ijse.studentsmanagement.entity;

import lk.ijse.studentsmanagement.dto.InquiryDTO;
import lk.ijse.studentsmanagement.dto.PaymentDTO;

import java.sql.Date;

public class Registration implements SuperEntity{
    String registrationId;
    String nic;
    String batchId;
    String courseId;
    String gardianId;
    String name;
    String address;
    String city;
    String postalCode;
    String mobile;
    String email;
    Date dob;

    String gender;
    String school;
    String higherEDU;
    String status;

    PaymentDTO payment;
    InquiryDTO inquiry;

    public Registration(String registrationId) {
        this.registrationId = registrationId;
    }

    public void setGardianId(String gardianId) {
        this.gardianId = gardianId;
    }


    public Registration(String registrationId, String name, String address, String city, String postalCode, String mobile, String email, Date dob, String school) {
        this.registrationId = registrationId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.mobile = mobile;
        this.email = email;
        this.dob = dob;
        this.school = school;
    }

    public Registration() {
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPaymentDTO(PaymentDTO payment) {
        this.payment = payment;
    }

    public Registration(String registrationId, String batchId, String name, String status) {
        this.registrationId = registrationId;
        this.batchId = batchId;
        this.name = name;
        this.status = status;
    }

    public Registration(String registrationId, String nic, String batchId, String courseId, String gardianId, String name, String address, String city, String postalCode, String mobile, String email, Date dob, String gender, String school, String higherEDU, String status) {
        this.registrationId = registrationId;
        this.nic = nic;
        this.batchId = batchId;
        this.courseId = courseId;
        this.gardianId = gardianId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.mobile = mobile;
        this.email = email;
        this.dob = dob;
        this.gender = gender;
        this.school = school;
        this.higherEDU = higherEDU;
        this.status = status;
    }

    public Registration(String registrationId, String name, String mobile, String email, String status) {
        this.registrationId = registrationId;
        this.name = name;
        this.mobile = mobile;
        this.email = email;
        this.status = status;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getNic() {
        return nic;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getGardianId() {
        return gardianId;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public Date getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getSchool() {
        return school;
    }

    public String getHigherEDU() {
        return higherEDU;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return registrationId + ',' + nic + ',' + batchId + ',' + courseId + ',' + gardianId + ',' + name +
                ',' + address + ',' + city + ',' + postalCode + ',' + mobile + ',' + email + ',' + dob + ',' + gender + ',' + school + ',' +
                higherEDU + ',' + status;
    }
}
