package lk.ijse.studentsmanagement.dto;

import lk.ijse.studentsmanagement.entity.InquiryIQTestDetail;

import java.sql.Date;

public class TestPaymentDTO implements SuperDTO {
    String id;
    String studentID;
    Date date;
    String remark = "test payment";
    double amount;
    String iqTestId;

    public String getIqTestId() {
        return iqTestId;
    }

    InquiryIQTestDetailDTO inquiryIQTestDetailDTO;

    public TestPaymentDTO(String id, String studentID, Date date, String remark, double amount, String iqTestId) {
        this.id = id;
        this.studentID = studentID;
        this.date = date;
        this.remark = remark;
        this.amount = amount;
        this.iqTestId = iqTestId;
    }

    public TestPaymentDTO(String id, String studentID, Date date, String remark, double amount) {
        this.id = id;
        this.studentID = studentID;
        this.date = date;
        this.remark = remark;
        this.amount = amount;
    }

    public InquiryIQTestDetailDTO getInquiryIQTestDetailDTO() {
        return inquiryIQTestDetailDTO;
    }

    public TestPaymentDTO(String id, String studentID, Date date, String remark, double amount, String iqTestId, InquiryIQTestDetailDTO inquiryIQTestDetailDTO) {
        this.id = id;
        this.studentID = studentID;
        this.date = date;
        this.remark = remark;
        this.amount = amount;
        this.iqTestId = iqTestId;
        this.inquiryIQTestDetailDTO = inquiryIQTestDetailDTO;
    }

    public String getId() {
        return id;
    }

    public String getStudentID() {
        return studentID;
    }

    public Date getDate() {
        return date;
    }

    public String getRemark() {
        return remark;
    }

    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "TestPayment{" +
                "id='" + id + '\'' +
                ", studentID='" + studentID + '\'' +
                ", date='" + date + '\'' +
                ", remark='" + remark + '\'' +
                ", amount=" + amount +
                ", inquiryIQTestDetailDTO=" + inquiryIQTestDetailDTO +
                '}';
    }
}
