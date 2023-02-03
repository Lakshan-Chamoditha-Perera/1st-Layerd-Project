package lk.ijse.studentsmanagement.tblModels;

public class SubjectTM{
    String id;
    String name;
    String hours;

    @Override
    public String toString() {
        return "SubjectTM{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", hours='" + hours + '\'' +
                '}';
    }

    public String getId() {return id;}

    public String getName() {return name;}

    public String getHours() {return hours;}

    public SubjectTM(String id, String name, String hours) {
        this.id = id;
        this.name = name;
        this.hours = hours;
    }
}
