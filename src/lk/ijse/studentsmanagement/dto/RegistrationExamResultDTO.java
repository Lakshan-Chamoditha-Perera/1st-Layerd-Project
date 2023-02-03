package lk.ijse.studentsmanagement.dto;

public class RegistrationExamResultDTO implements SuperDTO{
    String examId;
    String registrationId;
    int mark;
    String result;

    String subject;

    public String getSubject() {
        return subject;
    }

    public RegistrationExamResultDTO(int mark, String result, String subject) {
        this.mark = mark;
        this.result = result;
        this.subject = subject;
    }

    public RegistrationExamResultDTO(String examId, String registrationId, int mark, String result) {
        this.examId = examId;
        this.registrationId = registrationId;
        this.mark = mark;
        this.result = result;
    }

    public RegistrationExamResultDTO(String examId, String registrationId) {
        this.examId = examId;
        this.registrationId = registrationId;
    }

    public String getExamId() {
        return examId;
    }

    public String getRegistrationId() {
        return registrationId;
    }

    public int getMark() {
        return mark;
    }

    public String getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "RegistrationExamResultDTO{" +
                "examId='" + examId + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", mark=" + mark +
                ", result='" + result + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
