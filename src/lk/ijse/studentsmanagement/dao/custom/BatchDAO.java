package lk.ijse.studentsmanagement.dao.custom;

import lk.ijse.studentsmanagement.dao.CrudDAO;
import lk.ijse.studentsmanagement.entity.Batch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface BatchDAO  extends CrudDAO <Batch,String>{
    Batch getLastOngoingBatch(String courseName) throws SQLException, ClassNotFoundException;
    List<Batch> getBatchIDListByCourseID(String courseName) throws SQLException, ClassNotFoundException;
    List<Batch> getBatchesByCourseId(String course) throws SQLException, ClassNotFoundException;
    List<Batch> getAllBatchIds() throws SQLException, ClassNotFoundException;
    Batch getLastBatchNo(String course) throws SQLException, ClassNotFoundException;
    List<Batch> getAllBatchDetails() throws SQLException, ClassNotFoundException;
    Batch getCourseID(String value) throws SQLException, ClassNotFoundException;
}
