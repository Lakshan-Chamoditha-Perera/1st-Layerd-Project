package lk.ijse.studentsmanagement.tblModels;

public class RegistrationExamResultTM {
    String examId;
    String registrationId;
    int mark;
    String result;
    String sub;

    public String getSub() {
        return sub;
    }

    public RegistrationExamResultTM(int mark, String result, String sub) {
        this.mark = mark;
        this.result = result;
        this.sub = sub;
    }


    public RegistrationExamResultTM(String examId, String registrationId, int mark, String result) {
        this.examId = examId;
        this.registrationId = registrationId;
        this.mark = mark;
        this.result = result;
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
        return "RegistrationExamResultTM{" +
                "examId='" + examId + '\'' +
                ", registrationId='" + registrationId + '\'' +
                ", mark=" + mark +
                ", result='" + result + '\'' +
                ", subject='" + sub + '\'' +
                '}';
    }
}
