package lk.ijse.studentsmanagement.tblModels;

import java.sql.Date;

public class AttendanceTM {
    String registrationId;
    Date date;
    String batchId;
    String status;

    @Override
    public String toString() {
        return "AttendanceTM{" +
                "registrationId='" + registrationId + '\'' +
                ", date=" + date +
                ", batchId='" + batchId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public Date getDate() {
        return date;
    }

    public String getBatchId() {
        return batchId;
    }

    public String getStatus() {
        return status;
    }

    public AttendanceTM(String registrationId, Date date, String batchId, String status) {
        this.registrationId = registrationId;
        this.date = date;
        this.batchId = batchId;
        this.status = status;
    }
}
