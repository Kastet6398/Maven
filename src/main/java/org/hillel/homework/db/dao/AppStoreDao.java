package org.hillel.homework.db.dao;

import org.hillel.homework.entity.AppEntity;
import org.hillel.homework.exception.NotFoundException;

import java.sql.SQLException;
import java.util.List;

public interface AppStoreDao extends CrudDao<AppEntity> {
    AppEntity getByName(String name) throws SQLException, NotFoundException;

    List<AppEntity> filterByPrice(double cost) throws SQLException;

    List<AppEntity> filterByType(String type) throws SQLException;

    List<AppEntity> sortByCreationDate() throws SQLException;
}
