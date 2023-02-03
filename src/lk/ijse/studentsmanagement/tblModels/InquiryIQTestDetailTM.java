package lk.ijse.studentsmanagement.tblModels;

public class InquiryIQTestDetailTM {
    String studentId;
    String testId;
    String result;
    String name;

    public InquiryIQTestDetailTM(String studentId, String testId, String result, String name) {
        this.studentId = studentId;
        this.testId = testId;
        this.result = result;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public InquiryIQTestDetailTM(String studentId, String testId, String result) {
        this.studentId = studentId;
        this.testId = testId;
        this.result = result;
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
        return "InquiryIQTestDetailTM{" +
                "studentId='" + studentId + '\'' +
                ", testId='" + testId + '\'' +
                ", result='" + result + '\'' +
                '}';
    }
    //   // public InquiryIQTestDetailTM(String studentId, String testId, String result) {
//        super(studentId, testId, result);
//    }
}
