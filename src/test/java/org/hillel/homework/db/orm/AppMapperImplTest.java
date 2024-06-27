package org.hillel.homework.db.orm;

import com.mockrunner.mock.jdbc.MockResultSet;
import org.hillel.homework.entity.AppEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppMapperImplTest {
    private AppMapper orm;
    private static final AppEntity object = new AppEntity(
            "name",
            Date.valueOf("1111-11-11"),
            4.5,
            9.99,
            "description",
            new Timestamp(System.currentTimeMillis()),
            "type",
            1
    );

    @BeforeEach
    public void setUp() {
        orm = new AppMapperImpl();
    }

    @Test
    public void mapList_validResultSet_validObject() {
        try (MockResultSet rs = new MockResultSet("")) {
            rs.addColumn("name", new String[]{object.getName()});
            rs.addColumn("release_date", new Date[]{object.getReleaseDate()});
            rs.addColumn("rating", new Double[]{object.getRating()});
            rs.addColumn("cost", new Double[]{object.getCost()});
            rs.addColumn("description", new String[]{object.getDescription()});
            rs.addColumn("creation_date", new Timestamp[]{object.getCreationDate()});
            rs.addColumn("type", new String[]{object.getType()});
            rs.addColumn("id", new Long[]{object.getId()});

            assertEquals(List.of(object), orm.mapList(rs));
        } catch (SQLException e) {
            fail(e);
        }
    }

    @Test
    public void mapList_nullResultSet_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> orm.mapList(null));
    }

    @Test
    public void mapList_columnNotExists_SQLExceptionThrown() {
        try (MockResultSet rs = new MockResultSet("")) {
            rs.addColumn("release_date", new Date[]{object.getReleaseDate()});
            rs.addColumn("rating", new Double[]{object.getRating()});
            rs.addColumn("cost", new Double[]{object.getCost()});
            rs.addColumn("description", new String[]{object.getDescription()});
            rs.addColumn("creation_date", new Timestamp[]{object.getCreationDate()});
            rs.addColumn("type", new String[]{object.getType()});
            rs.addColumn("id", new Long[]{object.getId()});

            assertThrows(SQLException.class, () -> orm.mapList(rs));
        } catch (SQLException e) {
            fail(e);
        }
    }

    @Test
    public void map_validResultSet_validObject() {
        try (MockResultSet rs = new MockResultSet("")) {
            rs.addColumn("name", new String[]{object.getName()});
            rs.addColumn("release_date", new Date[]{object.getReleaseDate()});
            rs.addColumn("rating", new Double[]{object.getRating()});
            rs.addColumn("cost", new Double[]{object.getCost()});
            rs.addColumn("description", new String[]{object.getDescription()});
            rs.addColumn("creation_date", new Timestamp[]{object.getCreationDate()});
            rs.addColumn("type", new String[]{object.getType()});
            rs.addColumn("id", new Long[]{object.getId()});
            rs.next();

            assertEquals(object, orm.map(rs));
        } catch (SQLException e) {
            fail(e);
        }
    }

    @Test
    public void map_nullResultSet_NullPointerExceptionThrown() {
        assertThrows(NullPointerException.class, () -> orm.map(null));
    }

    @Test
    public void map_columnNotExists_SQLExceptionThrown() {
        try (MockResultSet rs = new MockResultSet("")) {
            rs.addColumn("release_date", new Date[]{object.getReleaseDate()});
            rs.addColumn("rating", new Double[]{object.getRating()});
            rs.addColumn("cost", new Double[]{object.getCost()});
            rs.addColumn("description", new String[]{object.getDescription()});
            rs.addColumn("creation_date", new Timestamp[]{object.getCreationDate()});
            rs.addColumn("type", new String[]{object.getType()});
            rs.addColumn("id", new Long[]{object.getId()});
            rs.next();

            assertThrows(SQLException.class, () -> orm.map(rs));
        } catch (SQLException e) {
            fail(e);
        }
    }
}