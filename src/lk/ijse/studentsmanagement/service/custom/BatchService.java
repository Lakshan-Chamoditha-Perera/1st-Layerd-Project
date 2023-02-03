package lk.ijse.studentsmanagement.service.custom;

import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.entity.Batch;
import lk.ijse.studentsmanagement.service.SuperService;
import lk.ijse.studentsmanagement.tblModels.BatchTM;

import java.sql.SQLException;
import java.util.List;

public interface BatchService extends SuperService <BatchDTO>{
    BatchDTO getLastOngoingBatch(String value) throws SQLException, ClassNotFoundException;

    List<BatchDTO> getBatches(String course) throws SQLException, ClassNotFoundException;

    BatchDTO getLastBatchNo(String course) throws SQLException, ClassNotFoundException;
}
