package org.hillel.homework.db.dao;

import lombok.Getter;
import org.hillel.homework.db.orm.AppMapper;
import org.hillel.homework.db.orm.AppMapperImpl;
import org.hillel.homework.entity.AppEntity;
import org.hillel.homework.exception.DuplicateException;
import org.hillel.homework.exception.NotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AppStoreDaoImpl implements AppStoreDao {
    @Getter
    private final AppMapper orm = new AppMapperImpl();
    private final Connection connection;
    public static final String CREATE = """
            INSERT INTO Games (name, release_date, rating, cost, description, type)
            VALUES (?,?,?,?,?,?);
            """;

    public static final String GET_BY_ID = """
            SELECT *
            FROM Games
            WHERE id =?
            LIMIT 1
            """;

    public static final String GET_BY_NAME = """
            SELECT *
            FROM Games
            WHERE name =?
            LIMIT 1
            """;

    public static final String UPDATE = """
            UPDATE Games
            SET name =?, release_date =?, rating =?, cost =?, description =?, type =?
            WHERE id =?;
            """;

    public static final String DELETE = """
            DELETE FROM Games
            WHERE id =?;
            """;

    public static final String LIST = """
            SELECT *
            FROM Games
            """;

    public static final String LIST_SORT_BY_CREATION_DATE = """
            SELECT *
            FROM Games
            ORDER BY creation_date
            """;

    public static final String LIST_FILTER_BY_COST = """
            SELECT *
            FROM Games
            WHERE cost =?
            """;

    public static final String LIST_FILTER_BY_TYPE = """
            SELECT *
            FROM Games
            WHERE type =?
            """;

    public static final String CREATE_TABLE = """
            CREATE TABLE IF NOT EXISTS Games(
                name TEXT NOT NULL,
                release_date DATE NOT NULL,
                rating DOUBLE PRECISION NOT NULL,
                cost DOUBLE PRECISION NOT NULL,
                description TEXT NOT NULL,
                creation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                id SERIAL PRIMARY KEY,
                type TEXT NOT NULL
            );
            """;

    public AppStoreDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean create(AppEntity object, boolean isCheckDuplicate) throws SQLException, DuplicateException {
        if (object == null) {
            throw new NullPointerException();
        }

        try {
            if (!isCheckDuplicate) {
                throw new NotFoundException();
            }
            getByName(object.getName());
        } catch (NotFoundException e) {
            PreparedStatement stmt = connection.prepareStatement(CREATE);
            stmt.setString(1, object.getName());
            stmt.setDate(2, object.getReleaseDate());
            stmt.setDouble(3, object.getRating());
            stmt.setDouble(4, object.getCost());
            stmt.setString(5, object.getDescription());
            stmt.setString(6, object.getType());

            return stmt.executeUpdate() == 1;
        }
        throw new DuplicateException();
    }

    @Override
    public AppEntity getById(long id) throws SQLException, NotFoundException {
        if (id < 0) {
            throw new NotFoundException();
        }

        PreparedStatement stmt = connection.prepareStatement(GET_BY_ID);
        stmt.setLong(1, id);

        return orm.map(stmt.executeQuery());
    }

    @Override
    public boolean update(long id, AppEntity object) throws SQLException, NotFoundException {
        if (object == null) {
            throw new NullPointerException();
        }

        if (id < 0) {
            throw new NotFoundException();
        }

        PreparedStatement stmt = connection.prepareStatement(UPDATE);
        stmt.setString(1, object.getName());
        stmt.setDate(2, object.getReleaseDate());
        stmt.setDouble(3, object.getRating());
        stmt.setDouble(4, object.getCost());
        stmt.setString(5, object.getDescription());
        stmt.setLong(6, id);

        return stmt.executeUpdate() == 1;
    }

    @Override
    public void delete(long id) throws SQLException, NotFoundException {
        if (id < 0) {
            throw new NotFoundException();
        }

        PreparedStatement stmt = connection.prepareStatement(DELETE);
        stmt.setDouble(1, id);
        if (stmt.executeUpdate() == 0) {
            throw new NotFoundException();
        }
    }

    @Override
    public List<AppEntity> fetchAll() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery(LIST);

        return orm.mapList(rs);
    }

    @Override
    public boolean createTable() throws SQLException {
        return connection.createStatement().executeUpdate(CREATE_TABLE) == 1;
    }

    @Override
    public AppEntity getByName(String name) throws SQLException, NotFoundException {
        PreparedStatement stmt = connection.prepareStatement(GET_BY_NAME);
        stmt.setString(1, name);

        ResultSet rs = stmt.executeQuery();

        if (!rs.next()) {
            throw new NotFoundException();
        }

        return orm.map(rs);
    }

    @Override
    public List<AppEntity> filterByPrice(double cost) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(LIST_FILTER_BY_COST);
        stmt.setDouble(1, cost);

        return orm.mapList(stmt.executeQuery());
    }

    @Override
    public List<AppEntity> filterByType(String type) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement(LIST_FILTER_BY_TYPE);
        stmt.setString(1, type);

        return orm.mapList(stmt.executeQuery());
    }

    @Override
    public List<AppEntity> sortByCreationDate() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery(LIST_SORT_BY_CREATION_DATE);
        return orm.mapList(rs);
    }
}
