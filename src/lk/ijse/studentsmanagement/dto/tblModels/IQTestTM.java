package lk.ijse.studentsmanagement.dto.tblModels;

import java.sql.Date;
import java.sql.Time;

public class IQTestTM  {
    public IQTestTM() {
    }

    public IQTestTM(String id, Date date, Time time, String lab, double amount) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.lab = lab;
        this.amount = amount;
    }

    String id;
    Date date;
    Time time;
    String lab;
    double amount;

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public void setLab(String lab) {
        this.lab = lab;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Time getTime() {
        return time;
    }

    public String getLab() {
        return lab;
    }

    public double getAmount() {
        return amount;
    }
}
