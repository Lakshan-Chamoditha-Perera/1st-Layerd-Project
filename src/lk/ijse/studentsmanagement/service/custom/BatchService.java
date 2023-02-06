package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface BatchService extends SuperService <BatchDTO>{
    BatchDTO getLastOngoingBatch(String value) throws SQLException, ClassNotFoundException;

    List<BatchDTO> getBatches(String course) throws SQLException, ClassNotFoundException;

    BatchDTO getLastBatchNo(String course) throws SQLException, ClassNotFoundException;

    List<BatchDTO> getAllBatchID() throws SQLException, ClassNotFoundException;

    BatchDTO getCourseID(String value) throws SQLException, ClassNotFoundException;

    List<BatchDTO> getAll() throws SQLException, ClassNotFoundException;

    BatchDTO delete(BatchDTO batchDTO) throws SQLException, ClassNotFoundException,RuntimeException;

    BatchDTO updateBatchDetail(BatchDTO batchDTO) throws SQLException, ClassNotFoundException,RuntimeException;
}
