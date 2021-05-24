package by.vsu.dao.mysql;

import by.vsu.dao.DaoException;
import by.vsu.dao.UserDao;
import by.vsu.entities.Role;
import by.vsu.entities.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * UserDaoImpl реализующий интерфейс UserDao для работы с БД mysql.
 *
 * @author Kovzov Vladislav
 */
public class UserDaoImpl implements UserDao {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long create(User user) throws DaoException {
        String sql = "INSERT INTO `user` (`login`, `password`, `name`, `role`) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setInt(4, user.getRole().ordinal());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            return resultSet.getLong(1);
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
    public User read(Long id) throws DaoException {
        String sql = "SELECT `login`, `name`, `role`, `is_active` FROM `user` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            User user = null;
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("name"));
                user.setRole(Role.values()[resultSet.getInt("role")]);
                user.setActive(resultSet.getBoolean("is_active"));
            }

            return user;
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
    public List<User> readAll() throws DaoException {
        return readAll(true);
    }

    @Override
    public List<User> readAll(boolean active) throws DaoException {
        String sql = "SELECT `id`, `login`, `password`, `name`, `role`  FROM `user` WHERE `is_active` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, active);
            resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setRole(Role.values()[resultSet.getInt("role")]);
                user.setActive(active);
                users.add(user);
            }
            return users;
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
    public User readByLoginAndPassword(String login, String password) throws DaoException {
        return readByLoginAndPassword(login, password, true);
    }

    @Override
    public User readByLoginAndPassword(String login, String password, boolean active) throws DaoException {
        String sql = "SELECT `id`, `role`, `name` FROM `user` WHERE `login` = ? and `password` = ? and `is_active` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            User user = null;
            statement = connection.prepareStatement(sql);
            statement.setString(1, login);
            statement.setString(2, password);
            statement.setBoolean(3, active);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user.setRole(Role.values()[resultSet.getInt("role")]);
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLogin(login);
                user.setPassword(password);
                user.setActive(active);
            }
            return user;
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
    public User readByIdAndRole(Long id, Role role) throws DaoException {
        String sql = "SELECT `login`, `password`, `name`, `is_active` FROM `user` WHERE `id` = ? and `role` = ? ";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.setInt(2, role.ordinal());
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setRole(role);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setActive(resultSet.getBoolean("is_active"));
            }
            return user;
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
    public User readByIdAndRole(Long id, Role role, boolean active) throws DaoException {
        String sql = "SELECT `login`, `password`, `name`FROM `user` WHERE `id` = ? and `role` = ? and `is_active` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            statement.setInt(2, role.ordinal());
            statement.setBoolean(3, active);
            resultSet = statement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = new User();
                user.setId(id);
                user.setRole(role);
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setName(resultSet.getString("name"));
                user.setActive(active);
            }
            return user;
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

    //TODO: протестировать
    @Override
    public boolean isLoginAlreadyExist(String login) throws DaoException {
        String sql = "SELECT COUNT(*) AS `count` FROM `user` WHERE `login` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setString(1, login);
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

    @Override
    public void update(User user) throws DaoException {
        String sql = "UPDATE `user` SET `login` = ?, `password` = ?, `name` = ?, `role` = ?, `is_active` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getLogin());
            statement.setString(2, user.getPassword());
            statement.setString(3, user.getName());
            statement.setInt(4, user.getRole().ordinal());
            statement.setBoolean(5, user.isActive());
            statement.setLong(6, user.getId());
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public void updateActiveStatus(Long id, boolean active) throws DaoException {
        String sql = "UPDATE `user` SET `is_active` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, active);
            statement.setLong(2, id);
            statement.executeUpdate();
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        String sql = "DELETE FROM `user` WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        } catch (SQLException ex) {
            throw new DaoException(ex);
        }
    }
}
