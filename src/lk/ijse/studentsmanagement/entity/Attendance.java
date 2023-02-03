package lk.ijse.studentsmanagement.entity;
import java.sql.Date;

public class Attendance implements SuperEntity{
    String registrationId;
    Date date;
    String batchId;
    String status;

    public Attendance(String registrationId, Date date, String batchId, String status) {
        this.registrationId = registrationId;
        this.date = date;
        this.batchId = batchId;
        this.status = status;
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

    @Override
    public String toString() {
        return "Attendance{" +
                "registrationId='" + registrationId + '\'' +
                ", date=" + date +
                ", batchId='" + batchId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
