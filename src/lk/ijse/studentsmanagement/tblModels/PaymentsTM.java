package lk.ijse.studentsmanagement.tblModels;

import java.sql.Date;

public class PaymentsTM {
    String id;
    String registrationId;
    String type;
    String remark;
    double amount;
    Date date;

    public String getId() {
        return id;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public String getType() {
        return type;
    }

    public String getRemark() {
        return remark;
    }

    public double getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public PaymentsTM(String id, String type, String remark, double amount, Date date) {
        this.id = id;
        this.type = type;
        this.remark = remark;
        this.amount = amount;
        this.date = date;
    }

    public PaymentsTM(String id, String registrationId, String type, String remark, double amount, Date date) {
        this.id = id;
        this.registrationId = registrationId;
        this.type = type;
        this.remark = remark;
        this.amount = amount;
        this.date = date;
    }
}
