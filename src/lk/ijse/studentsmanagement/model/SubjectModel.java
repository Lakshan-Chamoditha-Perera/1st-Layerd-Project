package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.Subject;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SubjectModel {

    public static ArrayList<Subject> getIDs() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id FROM subject ");
        if(resultSet!=null){
            ArrayList<Subject> arrayList = new ArrayList<>();
            while (resultSet.next()) {
                arrayList.add(new Subject(resultSet.getString(1)));
            }
            return arrayList;
        }
        return null;
    }

    public static String getSubjectName(Subject subject) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT name FROM subject WHERE id = ?", subject.getId());
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public static ArrayList<Subject> getSubjectList() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM subject");
        if (resultSet != null) {
            ArrayList<Subject> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(
                  new Subject(
                          resultSet.getString(1),
                          resultSet.getString(2),
                          resultSet.getString(3)
                  )
                );
            }
            return list;
        }
        return null;
    }

    public static boolean addSubject(Subject subject) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO subject VALUES(?,?,?)",
                subject.getId(),
                subject.getName(),
                subject.getHours()
                );
    }

    public static boolean deleteSubject(Subject subject) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM subject WHERE id = ?",
                subject.getId()
        );
    }
}
