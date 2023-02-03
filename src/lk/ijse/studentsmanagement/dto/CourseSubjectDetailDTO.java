package lk.ijse.studentsmanagement.dto;

import lk.ijse.studentsmanagement.entity.SuperEntity;

public class CourseSubjectDetailDTO  implements SuperDTO{
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

    public CourseSubjectDetailDTO(String courseId, String subjectId) {
        this.courseId = courseId;
        this.subjectId = subjectId;
    }

}
