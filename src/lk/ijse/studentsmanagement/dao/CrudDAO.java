package lk.ijse.studentsmanagement.dao;

import lk.ijse.studentsmanagement.entity.SuperEntity;

import java.io.Serializable;
import java.sql.SQLException;

public interface CrudDAO <T extends SuperEntity, TM extends Serializable> extends SuperDAO{
   T save (T entity) throws SQLException, ClassNotFoundException,RuntimeException;
   T update (T entity) throws SQLException, ClassNotFoundException,RuntimeException;
   T delete (T entity)throws SQLException, ClassNotFoundException,RuntimeException;
   T view (T entity) throws SQLException, ClassNotFoundException, RuntimeException;

}
