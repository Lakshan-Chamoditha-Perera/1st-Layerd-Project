package lk.ijse.studentsmanagement.dto;

import lk.ijse.studentsmanagement.entity.SuperEntity;

public class CourseDTO implements SuperDTO {
    String id;
    String name;
    String duration;

    public CourseDTO(String id, String name, String duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public CourseDTO(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDuration() {
        return duration;
    }

    @Override
    public String toString() {
        return "Course{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", duration='" + duration + '\'' +
                '}';


    }
}
