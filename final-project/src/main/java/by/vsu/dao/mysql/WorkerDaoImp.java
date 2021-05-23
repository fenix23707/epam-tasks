package by.vsu.dao.mysql;

import by.vsu.dao.DaoException;
import by.vsu.dao.WorkerDao;
import by.vsu.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkerDaoImp implements WorkerDao {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long create(Worker worker) throws DaoException {
        String sql = "INSERT INTO `worker` (`id`, `specialization`) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, worker.getId());
            statement.setInt(2, worker.getSpecialization().ordinal());
            statement.executeUpdate();
            return worker.getId();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Worker read(Long id) throws DaoException {
        String sql = "SELECT `specialization` FROM `worker` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            Worker worker = null;
            if (resultSet.next()) {
                worker = new Worker();
                worker.setId(id);
                worker.setRole(Role.WORKER);
                worker.setSpecialization(Specialization.values()[
                        resultSet.getInt("specialization")]);
                worker.setWorkPlans(readWorkPlansByWorkerId(id));
            }
            return worker;
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
    public List<Worker> readAll() throws DaoException {
        String sql = "SELECT `id`, `specialization` FROM `worker`";
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            List<Worker> workers = new ArrayList<>();
            while (resultSet.next()) {
                Worker worker = new Worker();
                worker.setId(resultSet.getLong("id"));
                worker.setSpecialization(Specialization.values()[
                        resultSet.getInt("specialization")]);
                worker.setRole(Role.WORKER);
                worker.setWorkPlans(readWorkPlansByWorkerId(worker.getId()));
                workers.add(worker);
            }
            return workers;
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
    public List<WorkPlan> readWorkPlansByWorkerId(Long workerId) throws DaoException {
        String sql = "SELECT `work_plan_id` FROM `work_plan_vs_worker` WHERE `worker_id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<WorkPlan> workPlans = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, workerId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                WorkPlan workPlan = new WorkPlan();
                workPlan.setId(resultSet.getLong("work_plan_id"));
                workPlans.add(workPlan);
            }
            return workPlans;
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
    public void update(Worker worker) throws DaoException {
        String sql = "UPDATE `worker` SET `specialization` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, worker.getSpecialization().ordinal());
            statement.setLong(2, worker.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isWorkerExistInWorkPlan(Long id) throws DaoException {
        String sql = "SELECT COUNT(*) AS `count` FROM(SELECT `work_plan_id` FROM `work_plan_vs_worker`" +
                " WHERE `worker_id` = ? LIMIT 1) AS wpvw";
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
}
