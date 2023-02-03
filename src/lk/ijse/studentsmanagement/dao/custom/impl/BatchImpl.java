package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.BatchDAO;
import lk.ijse.studentsmanagement.entity.Batch;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BatchImpl implements BatchDAO {
    private final Connection connection;

    public BatchImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Batch save(Batch entity) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("INSERT INTO batch VALUES(?,?,?,?,?,?)",
                entity.getId(),
                entity.getBatchNo(),
                entity.getCourseId(),
                entity.getFee(),
                entity.getStarting_date(),
                entity.getMaxStdCount()
        )) {
            return entity;
        }
        return null;
    }

    @Override
    public Batch update(Batch entity) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("UPDATE batch SET fee = ?, start_date = ?, maximum_std_count = ? WHERE batchID = ?",
                entity.getFee(),
                entity.getStarting_date(),
                entity.getMaxStdCount(),
                entity.getId()
        )) {
            return entity;
        }
        throw new RuntimeException();
    }

    @Override
    public Batch delete(Batch entity) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("DELETE FROM batch WHERE batchID = ?", entity.getId())) {
            return entity;
        }
        return null;
    }

    @Override
    public Batch view(Batch entity) throws SQLException, ClassNotFoundException, RuntimeException {
        return null;
    }

    @Override
    public Batch getLastOngoingBatch(String courseName) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM batch WHERE courseId = '" + courseName + "' ORDER BY batchID DESC LIMIT 1");
        if (resultSet.next()) {
            return new Batch(
                    resultSet.getString(1),
                    Integer.parseInt(resultSet.getString(2)),
                    resultSet.getString(3),
                    Double.parseDouble(resultSet.getString(4)),
                    Date.valueOf(resultSet.getString(5)),
                    Integer.parseInt(resultSet.getString(6))
            );
        }
        throw new RuntimeException("getLastOngoingBatch null");
    }

    @Override
    public List<Batch> getBatchIDListByCourseID(String courseName) throws SQLException, ClassNotFoundException, RuntimeException {
        List<Batch> list = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT batchId FROM batch WHERE courseId = ?", courseName);
        if (!resultSet.wasNull()) {
            while (resultSet.next()) {
                list.add(new Batch(resultSet.getString(1)));
            }
            return list;
        }
        throw new RuntimeException("getBatchIDListByCourseID null");
    }

    @Override
    public List<Batch> getBatchesByCourseId(String course) throws SQLException, ClassNotFoundException, RuntimeException {
        List<Batch> list = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM batch where courseId = ?", course);
            while (resultSet.next()) {
                list.add(
                        new Batch(
                                resultSet.getString(1),
                                Integer.parseInt(resultSet.getString(2)),
                                resultSet.getString(3),
                                Double.parseDouble(resultSet.getString(4)),
                                Date.valueOf(resultSet.getString(5)),
                                Integer.parseInt(resultSet.getString(6))
                        ));
            }
            return list;
    }

    @Override
    public List<Batch> getAllBatchIds() throws SQLException, ClassNotFoundException, RuntimeException {
        List<Batch> list = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT batchId FROM batch ");
            while (resultSet.next()) {
                list.add(new Batch(resultSet.getString(1)));
            }
            return list;
    }

    @Override
    public Batch getLastBatchNo(String course) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT batch_no from batch WHERE courseId = ? ORDER BY batch_no DESC LIMIT 1", course);
            if (resultSet.next()) {
                return new Batch(Integer.parseInt(resultSet.getString(1)));
            }
        throw new RuntimeException("getLastBatchNo null");
    }

    @Override
    public List<Batch> getAllBatchDetails() throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM batch");
        List<Batch> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(
                        new Batch(
                                resultSet.getString(1),
                                Integer.parseInt(resultSet.getString(2)),
                                resultSet.getString(3),
                                Double.parseDouble(resultSet.getString(4)),
                                Date.valueOf(resultSet.getString(5)),
                                Integer.parseInt(resultSet.getString(6))
                        ));
            }
            return list;
    }
    @Override
    public Batch getCourseID(String value) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT batchID,courseId FROM batch WHERE batchID = ?", value);
        if (!resultSet.wasNull()) {
            if (resultSet.next()) {
                return new Batch(
                        resultSet.getString(1),
                        resultSet.getString(2)

                );
            }
        }
        throw new RuntimeException("getCourseID null");
    }

}
