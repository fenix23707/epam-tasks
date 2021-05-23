package by.vsu.dao.mysql;

import by.vsu.dao.DaoException;
import by.vsu.dao.RequestDao;
import by.vsu.entities.Request;
import by.vsu.entities.RequestStatus;
import by.vsu.entities.Tenant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * RequestDaoImpl реализующий интерфейс RequestDao для работы с БД mysql.
 *
 * @author Kovzov Vladislav
 */
public class RequestDaoImpl implements RequestDao {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long create(Request request) throws DaoException {
        String sql = "INSERT INTO `request` (`description`, `desired_day`, `tenant_id`) VALUES(?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, request.getDescription());
            statement.setDate(2, new Date(request.getStartDay().getTime()));
            statement.setLong(3, request.getTenant().getId());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public Request read(Long id) throws DaoException {
        String sql = "SELECT `description`, `desired_day`, `status`, `tenant_id` FROM `request` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Request request = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                request = new Request();
                request.setId(id);
                request.setStartDay(resultSet.getDate("desired_day"));
                request.setDescription(resultSet.getString("description"));
                request.setTenant(new Tenant());
                request.getTenant().setId(resultSet.getLong("tenant_id"));
                request.setStatus(RequestStatus.values()[resultSet.getInt("status")]);
            }
            return request;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<Request> readAllByTenantId(Long tenantId) throws DaoException {
        String sql = "SELECT `id`, `description`, `desired_day`, `status` FROM `request` WHERE `tenant_id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Request> requests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, tenantId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getLong("id"));
                request.setDescription(resultSet.getString("description"));
                request.setStartDay(resultSet.getDate("desired_day"));
                request.setStatus(RequestStatus.values()[resultSet.getInt("status")]);
                request.setTenant(new Tenant());
                request.getTenant().setId(tenantId);
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public List<Request> readAllByRequestStatus(RequestStatus status) throws DaoException {
        String sql = "SELECT `id`, `description`, `desired_day`, `tenant_id` FROM `request` WHERE `status` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Request> requests = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, status.ordinal());
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Request request = new Request();
                request.setId(resultSet.getLong("id"));
                request.setDescription(resultSet.getString("description"));
                request.setStartDay(resultSet.getDate("desired_day"));
                request.setTenant(new Tenant());
                request.getTenant().setId(resultSet.getLong("tenant_id"));
                requests.add(request);
            }
            return requests;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void update(Request request) throws DaoException {
        String sql = "UPDATE `request` SET `description` = ?, `desired_day` = ?, `status` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, request.getDescription());
            statement.setDate(2, new Date(request.getStartDay().getTime()));
            statement.setInt(3, request.getStatus().ordinal());
            statement.setLong(4, request.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void updateStatusById(Long id, RequestStatus status) throws DaoException {
        String sql = "UPDATE `request` SET `status` = ? WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, status.ordinal());
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        String sql = "DELETE FROM `request` WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            return statement.executeUpdate() != 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) { }
        }
    }
}
