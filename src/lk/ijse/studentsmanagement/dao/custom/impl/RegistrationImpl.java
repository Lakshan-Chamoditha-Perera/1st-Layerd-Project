package lk.ijse.studentsmanagement.dao.custom.impl;

import lk.ijse.studentsmanagement.dao.custom.RegistrationDAO;
import lk.ijse.studentsmanagement.dao.exception.NotImplementedException;
import lk.ijse.studentsmanagement.dao.util.CrudUtil;
import lk.ijse.studentsmanagement.entity.Registration;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RegistrationImpl implements RegistrationDAO {
    private final Connection connection;

    public RegistrationImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Registration save(Registration registration) throws SQLException, ClassNotFoundException {
        if (CrudUtil.execute("INSERT INTO registration VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)",
                registration.getRegistrationId(),
                registration.getNic(),
                registration.getBatchId(),
                registration.getCourseId(),
                registration.getGardianId(),
                registration.getName(),
                registration.getAddress(),
                registration.getCity(),
                registration.getPostalCode(),
                registration.getMobile(),
                registration.getEmail(),
                registration.getDob(),
                registration.getGender(),
                registration.getSchool(),
                registration.getHigherEDU(),
                registration.getStatus()
        )) return registration;
        throw new RuntimeException();
    }

    @Override
    public Registration update(Registration entity) throws SQLException, ClassNotFoundException, RuntimeException {
        if (CrudUtil.execute("UPDATE registration SET full_name = ?, address = ?, city = ?, postal_code = ?, mobile_number = ?, dob = ?, school = ? WHERE registration_id = ?",
                entity.getName(),
                entity.getAddress(),
                entity.getCity(),
                entity.getPostalCode(),
                entity.getMobile(),
                entity.getDob(),
                entity.getSchool(),
                entity.getRegistrationId()
        )) return entity;
        throw new RuntimeException("Registration not updated Successfully");
    }

    @Override
    public Registration delete(Registration entity) throws SQLException, ClassNotFoundException, RuntimeException {
        throw new NotImplementedException("delete function in dao is not implemented");
    }

    @Override
    public Registration view(Registration entity) throws SQLException, ClassNotFoundException, RuntimeException {
        ResultSet resultSet = CrudUtil.execute("SELECT * from registration WHERE registration_id = ?", entity.getRegistrationId());
        if (resultSet.next()) {
            return new Registration(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8),
                    resultSet.getString(9),
                    resultSet.getString(10),
                    resultSet.getString(11),
                    Date.valueOf(resultSet.getString(12)),
                    resultSet.getString(13),
                    resultSet.getString(14),
                    resultSet.getString(15),
                    resultSet.getString(16)

            );
        }
        return null;
    }

    @Override
    public String getLastRegistrationID() throws SQLException, ClassNotFoundException {
        ResultSet result = CrudUtil.execute("SELECT registration_id FROM registration ORDER BY registration_id DESC LIMIT 1");
        if (result.next()) {
            return result.getString(1);
        }
        return null;
    }

    @Override
    public List<Registration> getCourseBatchList(String courseID, String batchID) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT registration_id,full_name,mobile_number,email,status FROM registration where courseID = ? AND batchID = ?", courseID, batchID);
        List<Registration> arrayList = new ArrayList<>();
        while (resultSet.next()) {
            arrayList.add(
                    new Registration(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4),
                            resultSet.getString(5)
                    )
            );
        }
        return arrayList;
    }

    @Override
    public String getRegistrationEmail(String text) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT email from registration where registration_id = ?", text);
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public List<String> getRegistrationEmailList(String text) throws SQLException, ClassNotFoundException {
        List<String> emailList = new ArrayList<>();
        ResultSet resultSet = CrudUtil.execute("SELECT email from registration where batchID = ?", text);
        if (resultSet.next()) {
            emailList.add(resultSet.getString(1));
        }
        return emailList;
    }

    @Override
    public List<Registration> loadBatchRegistrations(String value) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = CrudUtil.execute("SELECT registration_id, batchID, full_name, status from registration WHERE batchID = ?", value);
        List<Registration> list = new ArrayList<>();
        while (resultSet.next()) {
            list.add(
                    new Registration(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getString(4)
                    )
            );
        }
        return list;
    }
}
