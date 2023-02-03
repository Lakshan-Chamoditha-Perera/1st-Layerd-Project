package lk.ijse.studentsmanagement.entity;

import java.sql.Date;
import java.sql.Time;

public class IQTest implements SuperEntity{
    String id;
    Date date;
    Time time;
    String lab;
    double amount;

    public IQTest(String id, Date date, Time time, String lab,  double amount) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.lab = lab;
        this.amount= amount;
    }

    public IQTest(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
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
}
