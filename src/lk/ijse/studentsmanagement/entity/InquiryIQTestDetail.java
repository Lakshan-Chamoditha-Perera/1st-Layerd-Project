package lk.ijse.studentsmanagement.entity;

public class InquiryIQTestDetail implements SuperEntity{
    String studentId;
    String testId;
    String result;
    String name;

    public InquiryIQTestDetail(String studentId, String testId, String result, String name) {
        this.studentId = studentId;
        this.testId = testId;
        this.result = result;
        this.name = name;
    }

    public InquiryIQTestDetail(String studentId, String testId, String result) {
        this.studentId = studentId;
        this.testId = testId;
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getTestId() {
        return testId;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "InquiryIQTestDetail{" +
                "studentId='" + studentId + '\'' +
                ", testId='" + testId + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
}
