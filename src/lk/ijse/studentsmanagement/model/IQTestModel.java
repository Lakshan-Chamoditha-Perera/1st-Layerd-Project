package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.IQTest;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class IQTestModel {
    public static ArrayList<IQTest> getIQTestList() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iqTest");
        if (resultSet != null) {

            ArrayList<IQTest> list = new ArrayList<>();

            while (resultSet.next()) {
                list.add(
                        new IQTest(
                                resultSet.getString(1),
                                Date.valueOf(resultSet.getString(2)),
                                Time.valueOf(resultSet.getString(3)),
                                resultSet.getString(4),
                                Double.parseDouble(resultSet.getString(5)))
                );
            }
            return list;
        }
        return null;
    }

    public static IQTest getIQTestDetails(String date) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iqTest WHERE date = ?", date);
        if (resultSet.next()) {
            return new IQTest(
                    resultSet.getString(1),
                    Date.valueOf(resultSet.getString(2)),
                    Time.valueOf(resultSet.getString(3)),
                    resultSet.getString(4),
                    Double.parseDouble(resultSet.getString(5))
            );
        }
        return null;
    }

    public static IQTest getExamID() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT id from iqTest ORDER BY id DESC LIMIT 1");
        if (resultSet.next()) {
            return new IQTest(resultSet.getString(1));
        }
        return null;
    }

    public static boolean addIQTest(IQTest iqTest) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO iqTest VALUES(?,?,?,?,?)",
                iqTest.getId(),
                iqTest.getDate(),
                iqTest.getTime(),
                iqTest.getLab(),
                iqTest.getAmount()
        );
    }

    public static boolean deleteTest(IQTest test) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM iqTest WHERE id = ?", test.getId());
    }

    public static IQTest getExamDetails(IQTest test) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM iqTest WHERE id = ?", test.getId());
        if(resultSet.next()){
            return new IQTest(
                    resultSet.getString(1),
                    Date.valueOf(resultSet.getString(2)),
                    Time.valueOf(resultSet.getString(3)),
                    resultSet.getString(4),
                    Double.parseDouble(resultSet.getString(5))

                    );
        }
        return null;
    }
}
