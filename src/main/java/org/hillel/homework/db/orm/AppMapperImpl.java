package org.hillel.homework.db.orm;

import org.hillel.homework.entity.AppEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AppMapperImpl implements AppMapper {
    @Override
    public AppEntity map(ResultSet rs) throws SQLException {
        if (rs == null) {
            throw new NullPointerException();
        }
        return new AppEntity(
                rs.getString("name"),
                rs.getDate("release_date"),
                rs.getDouble("rating"),
                rs.getDouble("cost"),
                rs.getString("description"),
                rs.getTimestamp("creation_date"),
                rs.getString("type"),
                rs.getLong("id")
        );
    }

    @Override
    public List<AppEntity> mapList(ResultSet rs) throws SQLException {
        if (rs == null) {
            throw new NullPointerException();
        }
        List<AppEntity> result = new ArrayList<>();
        while (rs.next()) {
            result.add(map(rs));
        }
        return result;
    }
}
