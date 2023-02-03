package lk.ijse.studentsmanagement.dto;

import lk.ijse.studentsmanagement.entity.SuperEntity;

import java.sql.Date;
import java.sql.Time;

public class IQTestDTO implements SuperDTO {
    String id;
    Date date;
    Time time;
    String lab;
    double amount;

    public IQTestDTO(String id, Date date, Time time, String lab, double amount) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.lab = lab;
        this.amount= amount;
    }

    public IQTestDTO(String id) {
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
