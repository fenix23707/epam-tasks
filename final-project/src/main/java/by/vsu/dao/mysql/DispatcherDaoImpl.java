package by.vsu.dao.mysql;

import by.vsu.dao.DaoException;
import by.vsu.dao.DispatcherDao;
import by.vsu.entities.Dispatcher;

import java.sql.*;

/**
 * DispatcherDaoImpl реализует интерфейс DispatcherDao для работы с БД mysql.
 *
 * @author Kovzov Vladislav
 */
public class DispatcherDaoImpl implements DispatcherDao {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long create(Dispatcher dispatcher) throws DaoException {
        String sql = "INSERT INTO `dispatcher` (`id`) VALUES (?)";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, dispatcher.getId());
            statement.executeUpdate();

            return dispatcher.getId();
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
    public Dispatcher read(Long id) throws DaoException {
        String sql = "SELECT * FROM `dispatcher` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Dispatcher dispatcher = null;
            if (resultSet.next()) {
                dispatcher = new Dispatcher();
                dispatcher.setId(id);
            }
            return dispatcher;
        } catch (SQLException ex) {
            throw new DaoException(ex);
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
    public boolean isDispatcherCreatedWorkPlan(Long id) throws DaoException {
        String sql = "SELECT COUNT(*) AS `count` FROM(SELECT `id` FROM `work_plan` WHERE `dispatcher_id` = ? LIMIT 1) AS wp";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
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
            } catch (Exception e) {
            }
            try {
                statement.close();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void update(Dispatcher dispatcher) throws DaoException {
        //В данный момент нечего обновлять
       /* String sql = "UPDATE `dispatcher` SET  WHERE `id` = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1,dispatcher.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            try {
                statement.close();
            } catch (Exception e) {}
        }*/
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
