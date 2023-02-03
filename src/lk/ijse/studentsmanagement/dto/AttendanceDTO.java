package lk.ijse.studentsmanagement.dto;
import lk.ijse.studentsmanagement.entity.SuperEntity;

import java.sql.Date;

public class AttendanceDTO implements SuperDTO {
    String registrationId;
    Date date;
    String batchId;
    String status;

    public AttendanceDTO(String registrationId, Date date, String batchId, String status) {
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
