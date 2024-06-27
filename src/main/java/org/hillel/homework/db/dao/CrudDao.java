package org.hillel.homework.db.dao;

import org.hillel.homework.entity.AppEntity;
import org.hillel.homework.exception.DuplicateException;
import org.hillel.homework.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface CrudDao<T> extends Dao {
    boolean create(AppEntity object, boolean isCheckDuplicate) throws SQLException, DuplicateException;
    default boolean create(AppEntity object) throws SQLException, DuplicateException {
        return create(object, true);
    }
    T getById(long id) throws SQLException, NotFoundException;
    boolean update(long id, T object) throws SQLException, NotFoundException;
    void delete(long id) throws SQLException, NotFoundException;
    List<T> fetchAll() throws SQLException;
}
