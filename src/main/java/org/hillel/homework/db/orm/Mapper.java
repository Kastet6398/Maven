package org.hillel.homework.db.orm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface Mapper<T> {
    T map(ResultSet rs) throws SQLException;
    List<T> mapList(ResultSet rs) throws SQLException;
}
