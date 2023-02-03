package lk.ijse.studentsmanagement.entity;

public class CourseSubjectDetail implements SuperEntity {
    String courseId;
    String subjectId;


    @Override
    public String toString() {
        return "CourseSubjectDetail{" +
                "courseId='" + courseId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                '}';
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public CourseSubjectDetail(String courseId, String subjectId) {
        this.courseId = courseId;
        this.subjectId = subjectId;
    }

}
