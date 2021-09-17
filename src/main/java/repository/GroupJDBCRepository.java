package repository;

import database.DatabaseConnection;
import model.group.Group;
import model.group.RequestGroup;
import model.group.ResponseGroup;
import repository.mapper.GroupResultSetMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GroupJDBCRepository  {

    public Optional<List<ResponseGroup>> findAll(){
        List<ResponseGroup> groups=new ArrayList<>();
        ResponseGroup group;
        try (Connection connection = DatabaseConnection.initializeConnection()) {

            String query = "select * from groups ";
            PreparedStatement statement = connection.prepareStatement(query);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    group = GroupResultSetMapper.mapToPojo(resultSet);
                    groups.add(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups.isEmpty() ? Optional.empty() : Optional.of(groups);
    }



    public Optional<ResponseGroup> findById(int id) {
        ResponseGroup group = null;
        try (Connection connection = DatabaseConnection.initializeConnection()) {

            String query = "select * from groups where id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            // set parameters
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    group = GroupResultSetMapper.mapToPojo(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return group == null ? Optional.empty() : Optional.of(group);
    }

    public Optional<List<ResponseGroup>> findByParent(int id) {
        List<ResponseGroup> groups = new ArrayList<>();
        try (Connection connection = DatabaseConnection.initializeConnection()) {

            String query = "select * from groups where parent_id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            // set parameters
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                ResponseGroup group;
                while (resultSet.next()) {
                    group=GroupResultSetMapper.mapToPojo(resultSet);
                    groups.add(group);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groups .isEmpty() ? Optional.empty() : Optional.of(groups);
    }

    public void create(RequestGroup group) {
        try (Connection connection = DatabaseConnection.initializeConnection()) {

            String query = "insert into groups(name ,parent_id) values (?,?)";
            PreparedStatement statement = connection.prepareStatement(query);
            // set parameters
            statement.setString(1, group.getName());
            if (group.getParentId()==0){
                statement.setNull(2,group.getParentId());
            }else{
                statement.setInt(2, group.getParentId());
            }

            // execute the query and get ResultSet
            int rows = statement.executeUpdate();
//            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
//                if (generatedKeys.next()) {
//                    group.setId(generatedKeys.getInt(1));
//                } else {
//                    throw new SQLException("Creating user failed, no ID obtained.");
//                }
//            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void update(ResponseGroup group, Integer id) {
        try (Connection connection = DatabaseConnection.initializeConnection()) {

            String query = "update groups set name =?,parent_id=? where id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            // set parameters
            statement.setString(1, group.getName());
            statement.setInt(2, group.getParentId());
            statement.setInt(3, id);
            // execute the query and get ResultSet
            int rows = statement.executeUpdate();
           if (rows<1){
               throw new SQLException("Update is failed");
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(Integer id) {
        try (Connection connection = DatabaseConnection.initializeConnection()) {

            String query = "delete from groups where id=?";
            PreparedStatement statement = connection.prepareStatement(query);
            // set parameters
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
