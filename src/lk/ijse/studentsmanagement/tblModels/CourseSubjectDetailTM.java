package lk.ijse.studentsmanagement.tblModels;

public class CourseSubjectDetailTM {
    String courseId;
    String subjectId;
    String subjectName;


    public CourseSubjectDetailTM(String courseId, String subjectId, String subjectName) {
        this.courseId = courseId;
        this.subjectId = subjectId;
        this.subjectName = subjectName;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    @Override
    public String toString() {
        return "CourseSubjectDetailTM{" +
                "courseId='" + courseId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
