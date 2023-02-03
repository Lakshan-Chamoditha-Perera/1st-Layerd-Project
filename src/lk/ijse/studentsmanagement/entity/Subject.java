package lk.ijse.studentsmanagement.entity;

import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Subject implements SuperEntity{
    String id;
    String name;
    String hours;

    public Subject(String id, String name, String hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
    }

    public Subject(String id) {
        this.id = id;
    }

    public Subject() {
    }

    public static Subject getLastSubjectID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id from subject ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            return new Subject(resultSet.getString(1));
        }
        return null;

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
