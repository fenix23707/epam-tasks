package by.vsu.dao.mysql;

import by.vsu.dao.DaoException;
import by.vsu.dao.TenantDao;
import by.vsu.entities.Tenant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * TenantDaoImpl реализующий интерфейс TenantDao для работы с БД mysql.
 *
 * @author Kovzov Vladislav
 */
public class TenantDaoImpl implements TenantDao {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long create(Tenant tenant) throws DaoException {
        String sql = "INSERT INTO `tenant` (`id`, `address`, `apartment_number`) VALUES (?, ?, ?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,tenant.getId());
            statement.setString(2,tenant.getAddress());
            statement.setInt(3,tenant.getApartmentNumber());
            statement.executeUpdate();
            return tenant.getId();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (SQLException e) { }
        }
    }

    @Override
    public Tenant read(Long id) throws DaoException {
        String sql = "SELECT `address`, `apartment_number` FROM `tenant` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            Tenant tenant = null;
            if (resultSet.next()) {
                tenant = new Tenant();
                tenant.setId(id);
                tenant.setAddress(resultSet.getString("address"));
                tenant.setApartmentNumber(resultSet.getInt("apartment_number"));
            }
            return tenant;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) { }
            try {
                statement.close();
            } catch (Exception e) { }
        }
    }

    @Override
    public List<Tenant> readAll() throws DaoException {
        String sql = "SELECT `id`, `address`, `apartment_number` FROM `tenant`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<Tenant> tenants = new ArrayList<>();
            while (resultSet.next()) {
                Tenant tenant = new Tenant();
                tenant.setId(resultSet.getLong("id"));
                tenant.setAddress(resultSet.getString("address"));
                tenant.setApartmentNumber(resultSet.getInt("apartment_number"));
                tenants.add(tenant);
            }
            return tenants;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e ) {}
            try {
                statement.close();
            } catch (Exception e) {}
        }
    }

    @Override
    public void update(Tenant tenant) throws DaoException {
        String sql = "UPDATE `tenant` SET `address` = ?, `apartment_number` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1,tenant.getAddress());
            statement.setInt(2,tenant.getApartmentNumber());
            statement.setLong(3,tenant.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {}
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        String sql = "DELETE FROM `tenant` WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) { }
        }
    }

    @Override
    public boolean isTenantCreatedRequest(Long id) throws DaoException {
        String sql = "SELECT COUNT(*) AS `count` FROM(SELECT `id` FROM `request` WHERE `tenant_id` = ? LIMIT 1) AS req";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            resultSet = statement.executeQuery();
            boolean result = true;
            if (resultSet.next()) {
                result = resultSet.getBoolean("count");
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e ) {}
            try {
                statement.close();
            } catch (Exception e) {}
        }
    }
}
