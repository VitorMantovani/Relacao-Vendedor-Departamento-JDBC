package br.com.fiap.dao.impl;

import br.com.fiap.dao.SellerDao;
import br.com.fiap.db.DB;
import br.com.fiap.db.DbException;
import br.com.fiap.entities.Department;
import br.com.fiap.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(
                    "INSERT INTO seller "
                    + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                    + "VALUES "
                    + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, obj.getName());
            statement.setString(2, obj.getEmail());
            statement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
            statement.setDouble(4, obj.getBaseSalary());
            statement.setInt(5, obj.getDepartment().getId());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet resultSet = statement.getGeneratedKeys();
                if(resultSet.next()) {
                    int id = resultSet.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(resultSet);
            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
        }
    }

    @Override
    public void update(Seller obj) {
            PreparedStatement preparedStatement = null;
             try {
                 preparedStatement = connection.prepareStatement(
                         "UPDATE seller "
                         + "SET Name = ?, Email = ?, BirthDate = ?,BaseSalary = ?,DepartmentId = ? "
                         +"WHERE Id = ?"
                 );

                 preparedStatement.setString(1, obj.getName());
                 preparedStatement.setString(2, obj.getEmail());
                 preparedStatement.setDate(3, new java.sql.Date(obj.getBirthDate().getTime()));
                 preparedStatement.setDouble(4, obj.getBaseSalary());
                 preparedStatement.setInt(5, obj.getDepartment().getId());
                 preparedStatement.setInt(6, obj.getId());

                 preparedStatement.executeUpdate();
             } catch (SQLException e) {
                 throw new DbException(e.getMessage());
             } finally {
                 DB.closeStatement(preparedStatement);
             }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM seller "
                    +"WHERE id = ?"
            );

            preparedStatement.setInt(1, id);

           int rows =  preparedStatement.executeUpdate();

           if (rows == 0) {
               throw new DbException("Id does not exist!");
           }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(preparedStatement);
        }
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE seller.Id = ?"
            );

            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Department department = instantiateDepartment(resultSet);
                Seller seller = instantiateSeller(resultSet, department);
                return seller;
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    private Seller instantiateSeller(ResultSet resultSet, Department department) throws SQLException {
        Seller seller =new Seller();
        seller.setId(resultSet.getInt("Id"));
        seller.setName(resultSet.getString("Name"));
        seller.setEmail(resultSet.getString("Email"));
        seller.setBaseSalary(resultSet.getDouble("BaseSalary"));
        seller.setBirthDate(resultSet.getDate("BirthDate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instantiateDepartment(ResultSet resultSet) throws SQLException{
        Department department =new Department();
        department.setId(resultSet.getInt("DepartmentId"));
        department.setName(resultSet.getString("DepName"));
        return department;
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name"
            );

            resultSet = statement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()) {

                Department dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(resultSet, dep);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }

    @Override
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                    + "FROM seller INNER JOIN department "
                    + "ON seller.DepartmentId = department.Id "
                    + "WHERE DepartmentId = ? "
                    + "ORDER BY Name"
            );

            statement.setInt(1, department.getId());
            resultSet = statement.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (resultSet.next()) {

                Department dep = map.get(resultSet.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(resultSet);
                    map.put(resultSet.getInt("DepartmentId"), dep);
                }
                Seller seller = instantiateSeller(resultSet, dep);
                list.add(seller);
            }
            return list;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(statement);
            DB.closeResultSet(resultSet);
        }
    }
}
