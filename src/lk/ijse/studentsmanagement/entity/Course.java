package lk.ijse.studentsmanagement.entity;

public class Course implements SuperEntity{
    String id;
    String name;
    String duration;

    public Course(String id, String name, String duration) {
        this.id = id;
        this.name = name;
        this.duration = duration;
    }

    public Course(String id) {
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
