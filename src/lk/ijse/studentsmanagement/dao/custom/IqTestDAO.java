package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.IQTest;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public interface IqTestDAO extends CrudDAO <IQTest,String>{
    List<IQTest> getList () throws SQLException, ClassNotFoundException;
    IQTest getExamByDate(Date date) throws SQLException, ClassNotFoundException;
    IQTest getExamId() throws SQLException, ClassNotFoundException;


    IQTest getExamDetails(IQTest iqTest) throws SQLException, ClassNotFoundException;
}
