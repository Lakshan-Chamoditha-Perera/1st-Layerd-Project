package lk.ijse.studentsmanagement.dto;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SubjectDTO implements SuperDTO {
    String id;
    String name;
    String hours;

    public SubjectDTO(String id, String name, String hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
    }

    public SubjectDTO(String id) {
        this.id = id;
    }

    public SubjectDTO() {
    }



    @Override
    public String toString() {
        return "Subject{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hours='" + hours + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getHours() {
        return hours;
    }
}
