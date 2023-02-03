package lk.ijse.studentsmanagement.service.custom.impl;

import lk.ijse.studentsmanagement.dao.DAOFactory;
import lk.ijse.studentsmanagement.dao.DaoTypes;
import lk.ijse.studentsmanagement.dao.custom.BatchDAO;
import lk.ijse.studentsmanagement.db.DBconnection;
import lk.ijse.studentsmanagement.dto.BatchDTO;
import lk.ijse.studentsmanagement.entity.Batch;
import lk.ijse.studentsmanagement.service.custom.BatchService;
import lk.ijse.studentsmanagement.service.exception.DuplicateException;
import lk.ijse.studentsmanagement.service.util.Converter;
import lk.ijse.studentsmanagement.service.util.Types;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import static lk.ijse.studentsmanagement.service.util.Types.BatchType1;
import static lk.ijse.studentsmanagement.service.util.Types.BatchType2;

public class BatchServiceImpl implements BatchService {
    final Converter converter;
    private final BatchDAO batchDAO;

    public BatchServiceImpl() throws RuntimeException {
        try {
            Connection connection = DBconnection.getInstance().getConnection();
            batchDAO = DAOFactory.getInstance().getDAO(connection, DaoTypes.BATCH);
            converter = new Converter();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public BatchDTO save(BatchDTO dto) throws SQLException, ClassNotFoundException, DuplicateException {
        return null;
    }

    @Override
    public BatchDTO getLastOngoingBatch(String value) throws SQLException, ClassNotFoundException, RuntimeException {
        Batch lastOngoingBatch = batchDAO.getLastOngoingBatch(value);
        return (lastOngoingBatch != null) ? (converter.toBatchDTO(lastOngoingBatch,Types.BatchType1)) : null;
    }

    @Override
    public List<BatchDTO> getBatches(String course) throws SQLException, ClassNotFoundException, RuntimeException {
        List<Batch> batchesByCourseId = batchDAO.getBatchesByCourseId(course);
        if (batchesByCourseId.size() > 0) {
            return batchesByCourseId.stream().map(batch -> converter.toBatchDTO(batch, Types.BatchType1)).collect(Collectors.toList());
        }
        throw new RuntimeException("Empty batch list...");
    }

    @Override
    public BatchDTO getLastBatchNo(String course) throws SQLException, ClassNotFoundException,RuntimeException {
        Batch lastBatchNo = batchDAO.getLastBatchNo(course);
        converter.toBatchDTO(lastBatchNo,BatchType2);
        System.out.println(lastBatchNo.getBatchNo());
      //  return converter.toBatchDTO(,BatchType2);
       // return new BatchDTO(1);
    }
}
