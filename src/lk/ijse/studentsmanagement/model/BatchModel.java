package lk.ijse.studentsmanagement.model;

import lk.ijse.studentsmanagement.entity.Batch;
import lk.ijse.studentsmanagement.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
public class BatchModel {
    public static Batch getLastOngoingBatch(String courseName) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("select * from batch where courseId = '" + courseName + "' order by batchID DESC LIMIT 1");
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
        return null;
    }

    public static ArrayList<Batch> getBatchIDListByCourseId(String courseName) throws SQLException, ClassNotFoundException {
        ArrayList<Batch> list = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT batchId FROM batch where courseId = ?", courseName);
        while (resultSet.next()) {
            list.add(new Batch(resultSet.getString(1)));
        }
        return list;
    }

    public static ArrayList<Batch> getBatches(String course) throws SQLException, ClassNotFoundException {
        ArrayList<Batch> list = new ArrayList<>();
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

    public static ArrayList<Batch> getBatches() throws SQLException, ClassNotFoundException {
        ArrayList<Batch> list = new ArrayList<>();
        if(list!=null){
            ResultSet resultSet = CrudUtil.execute("SELECT batchId FROM batch ");
            while (resultSet.next()) {
                list.add(new Batch(resultSet.getString(1)));
            }
            return list;
        }
        return null;
    }

    public static Batch getLastBatchNo(String course) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT batch_no from batch WHERE courseId = ? ORDER BY batch_no DESC LIMIT 1", course);
        if(resultSet.next()){
            return new Batch(Integer.parseInt(resultSet.getString(1)));
        }
        return null;
    }

    public static boolean addNewBatch(Batch batch) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO batch VALUES(?,?,?,?,?,?)",
                batch.getId(),
                batch.getBatchNo(),
                batch.getCourseId(),
                batch.getFee(),
                batch.getStarting_date(),
                batch.getMaxStdCount()
                );

    }

    public static ArrayList<Batch> getAllBAtches() throws SQLException, ClassNotFoundException {
       ResultSet resultSet = CrudUtil.execute("SELECT * FROM batch");
       ArrayList<Batch> list = new ArrayList<>();
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

    public static Batch getCourseID(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT batchID,courseId FROM batch WHERE batchID = ?", value);
        if(resultSet.next()){
            return new Batch(
                    resultSet.getString(1),
                    resultSet.getString(2)

            );
        }
        return null;
    }

    public static boolean updateBatchDetails(Batch batch) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE batch SET fee = ?, start_date = ?, maximum_std_count = ? WHERE batchID = ?",
                batch.getFee(),
                batch.getStarting_date(),
                batch.getMaxStdCount(),
                batch.getId()
        );
    }

    public static boolean deleteBatch(Batch batch) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM batch WHERE batchID = ?", batch.getId());
    }

}
