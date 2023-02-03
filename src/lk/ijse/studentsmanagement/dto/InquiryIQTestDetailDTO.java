package lk.ijse.studentsmanagement.dto;

public class InquiryIQTestDetailDTO implements SuperDTO{
    private String studentId;
    private String testId;
    private String result;
    private String name;

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InquiryIQTestDetailDTO(String studentId, String testId, String result, String name) {
        this.studentId = studentId;
        this.testId = testId;
        this.result = result;
        this.name = name;
    }
    public InquiryIQTestDetailDTO(String studentId, String testId, String result) {
        this.studentId = studentId;
        this.testId = testId;
        this.result = result;
    }
}
