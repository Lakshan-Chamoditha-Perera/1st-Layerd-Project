package lk.ijse.studentsmanagement.dto.tblModels;

import java.sql.Date;

public class BatchTM  {
    String id;
    int batchNo;
    String courseId;
    double fee;
    Date starting_date;
    int maxStdCount;

    public BatchTM(String id, int batchNo, String courseId, double fee, Date starting_date, int maxStdCount) {
        this.id = id;
        this.batchNo = batchNo;
        this.courseId = courseId;
        this.fee = fee;
        this.starting_date = starting_date;
        this.maxStdCount = maxStdCount;
    }
    public BatchTM(String id, int batchNo, double fee, Date starting_date, int maxStdCount) {
        this.id = id;
        this.batchNo = batchNo;
        this.fee = fee;
        this.starting_date = starting_date;
        this.maxStdCount = maxStdCount;
    }

    @Override
    public String toString() {
        return "BatchTM{" +
                "id='" + id + '\'' +
                ", batchNo=" + batchNo +
                ", courseId='" + courseId + '\'' +
                ", fee=" + fee +
                ", starting_date=" + starting_date +
                ", maxStdCount=" + maxStdCount +
                '}';
    }

    public String getId() {
        return id;
    }

    public int getBatchNo() {
        return batchNo;
    }

    public String getCourseId() {
        return courseId;
    }

    public double getFee() {
        return fee;
    }

    public Date getStarting_date() {
        return starting_date;
    }

    public int getMaxStdCount() {
        return maxStdCount;
    }
}
