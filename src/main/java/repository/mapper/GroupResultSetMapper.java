package repository.mapper;

import model.group.Group;
import model.group.ResponseGroup;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class GroupResultSetMapper {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PARENT_ID = "parent_id";

    private GroupResultSetMapper(){}

    public static ResponseGroup mapToPojo(ResultSet resultSet) throws SQLException {
        return mapToPojo(resultSet, null);
    }

    public static ResponseGroup mapToPojo(ResultSet resultSet, String alias) throws SQLException {
        alias = alias == null ? "" : alias + "_";
        int id = resultSet.getInt(alias + COLUMN_ID);
        String name = resultSet.getString(alias + COLUMN_NAME);
        int parentId = resultSet.getInt(alias + COLUMN_PARENT_ID);
        return new ResponseGroup(id, name,parentId);
    }
}
