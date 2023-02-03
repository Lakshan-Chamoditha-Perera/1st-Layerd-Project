package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.Registration;
import lk.ijse.studentsmanagement.entity.RegistrationExamResult;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RegistrationExamResultModel {

    public static boolean addResult(RegistrationExamResult registrationExamResult) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO registration_exam_results VALUES(?,?,?,?)",
                registrationExamResult.getExamId(),
                registrationExamResult.getRegistrationId(),
                registrationExamResult.getMark(),
                registrationExamResult.getResult()
        );
    }

    public static ArrayList<RegistrationExamResult> getResults(String examId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM registration_exam_results WHERE exam_id = ?", examId);
        if (resultSet != null) {
            ArrayList<RegistrationExamResult> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(
                        new RegistrationExamResult(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                Integer.parseInt(resultSet.getString(3)),
                                resultSet.getString(4)
                        )
                );
            }
            return list;
        }
        return null;
    }

    public static boolean updateResult(RegistrationExamResult registrationExamResult) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE registration_exam_results SET marks =?, result=? WHERE exam_id=? AND registration_id =?",
                registrationExamResult.getMark(),
                registrationExamResult.getResult(),
                registrationExamResult.getExamId(),
                registrationExamResult.getRegistrationId()
        );
    }

    public static boolean deleteResult(RegistrationExamResult registrationExamResult) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM registration_exam_results WHERE exam_id=? AND registration_id =?",
                registrationExamResult.getExamId(),
                registrationExamResult.getRegistrationId()
        );
    }

    public static ArrayList<RegistrationExamResult> getTranscript(Registration registration) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute(
                "SELECT registration_exam_results.marks," +
                "subject.name, " +
                "registration_exam_results.result " +
                "FROM registration_exam_results " +
                "INNER JOIN exam ON registration_exam_results.exam_id = exam.id " +
                "INNER JOIN subject ON exam.subjectID = subject.id " +
                "WHERE registration_id = ?",registration.getRegistrationId());
        ArrayList<RegistrationExamResult> list = new ArrayList<>();
        while(resultSet.next()){
            list.add(
              new RegistrationExamResult(
                      Integer.parseInt(resultSet.getString(1)),
                      resultSet.getString(3),
                      resultSet.getString(2)
                      )
            );
        }
        return list;
    }
}
