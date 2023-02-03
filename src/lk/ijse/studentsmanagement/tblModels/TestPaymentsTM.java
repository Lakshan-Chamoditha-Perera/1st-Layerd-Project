package lk.ijse.studentsmanagement.tblModels;

import java.sql.Date;

public class TestPaymentsTM {
    String id;
    String studentID;
    Date date;
    String remark;
    double amount;
    String iqTestId;

    public TestPaymentsTM(String id, String studentID, Date date, String remark, double amount, String iqTestId) {
        this.id = id;
        this.studentID = studentID;
        this.date = date;
        this.remark = remark;
        this.amount = amount;
        this.iqTestId = iqTestId;
    }

    public TestPaymentsTM(String id, String studentID, Date date, String remark, double amount) {
        this.id = id;
        this.studentID = studentID;
        this.date = date;
        this.remark = remark;
        this.amount = amount;
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

    public String getIqTestId() {
        return iqTestId;
    }

    @Override
    public String toString() {
        return "TestPaymentsTM{" +
                "id='" + id + '\'' +
                ", studentID='" + studentID + '\'' +
                ", date=" + date +
                ", remark='" + remark + '\'' +
                ", amount=" + amount +
                ", iqTestId='" + iqTestId + '\'' +
                '}';
    }
}
