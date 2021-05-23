package by.vsu.dao.mysql;

import by.vsu.dao.DaoException;
import by.vsu.dao.WorkPlanDao;
import by.vsu.entities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WorkPlanDaoImpl implements WorkPlanDao {
    private Connection connection;

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public long create(WorkPlan workPlan) throws DaoException {
        String sql = "INSERT INTO `work_plan` (`end_day`, `work_scope`, `request_id`, `dispatcher_id`) VALUES( ?, ?, ?, ?)";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, new Date(workPlan.getEndDay().getTime()));
            statement.setInt(2, workPlan.getWorkScope());
            statement.setLong(3, workPlan.getRequest().getId());
            statement.setLong(4, workPlan.getDispatcher().getId());

            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            resultSet.next();
            Long id = resultSet.getLong(1);
            workPlan.setId(id);
            createWorkersForWorkPlan(workPlan);
            return id;
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

    private void createWorkersForWorkPlan(WorkPlan workPlan) throws DaoException {
        String sql = "INSERT INTO `work_plan_vs_worker` (`work_plan_id`, `worker_id`) VALUES(?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            List<Worker> workers = workPlan.getWorkers();
            statement.setLong(1, workPlan.getId());
            for (Worker worker : workers) {
                statement.setLong(2, worker.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<WorkPlan> readAllByDispatcherId(Long dispatcherId) throws DaoException {
        String sql = "SELECT `id`, `end_day`, `work_scope`, `request_id` FROM `work_plan` WHERE `dispatcher_id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<WorkPlan> workPlans = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, dispatcherId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                WorkPlan workPlan = new WorkPlan();
                workPlan.setId(resultSet.getLong("id"));
                workPlan.setEndDay(resultSet.getDate("end_day"));
                workPlan.setWorkScope(resultSet.getInt("work_scope"));
                workPlan.setRequest(new Request());
                workPlan.getRequest().setId(resultSet.getLong("request_id"));
                workPlan.setDispatcher(new Dispatcher());
                workPlan.getDispatcher().setId(dispatcherId);
                workPlan.setWorkers(readWorkersByWorkPlanId(workPlan.getId()));
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
    public WorkPlan read(Long id) throws DaoException {
        String sql = "SELECT `end_day`, `work_scope`, `request_id`, `dispatcher_id`   FROM `work_plan` WHERE `id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            WorkPlan workPlan = null;
            statement = connection.prepareStatement(sql);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                workPlan = new WorkPlan();
                workPlan.setId(id);
                workPlan.setEndDay(resultSet.getDate("end_day"));
                workPlan.setWorkScope(resultSet.getInt("work_scope"));

                workPlan.setRequest(new Request());
                workPlan.getRequest().setId(resultSet.getLong("request_id"));

                workPlan.setDispatcher(new Dispatcher());
                workPlan.getDispatcher().setId(resultSet.getLong("dispatcher_id"));

                workPlan.setWorkers(readWorkersByWorkPlanId(workPlan.getId()));
            }
            return workPlan;
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
    public List<Worker> readWorkersByWorkPlanId(Long workPlanId) throws DaoException {
        String sql = "SELECT `worker_id` FROM `work_plan_vs_worker` WHERE `work_plan_id` = ?";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Worker> workers = new ArrayList<>();
        try {
            statement = connection.prepareStatement(sql);
            statement.setLong(1, workPlanId);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Worker worker = new Worker();
                worker.setId(resultSet.getLong("worker_id"));
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
    public void update(WorkPlan workPlan) throws DaoException {
        String sql = "UPDATE `work_plan` SET `end_day` = ?, `work_scope` = ? WHERE `id` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDate(1, new Date(workPlan.getEndDay().getTime()));
            statement.setInt(2, workPlan.getWorkScope());
            statement.setLong(3, workPlan.getId());
            statement.executeUpdate();
            updateWorkersForWorkPlan(workPlan);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void updateWorkersForWorkPlan(WorkPlan workPlan) throws DaoException {
        List<Worker> workersBd = readWorkersByWorkPlanId(workPlan.getId());
        List<Worker> workersWorkPlan = workPlan.getWorkers();

        /*удаление лишних рабочих из work_plan_vs_worker*/
        for (Worker worker : workersBd) {
            if (!workersWorkPlan.contains(worker)) {
                deleteFromWorkPlanVsWorker(workPlan.getId(), worker.getId());
            }
        }

        /*добавление недостующих в work_plan_vs_worker*/
        for (Worker worker : workersWorkPlan) {
            if (!workersBd.contains(worker)) {
                insertIntoWorkPlanVsWorker(workPlan.getId(), worker.getId());
            }
        }
    }

    private void insertIntoWorkPlanVsWorker(Long workPlanId, Long workerId) throws DaoException {
        String sql = "INSERT INTO `work_plan_vs_worker` (`work_plan_id`,`worker_id`) VALUeS (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setLong(1, workPlanId);
            statement.setLong(2, workerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private void deleteFromWorkPlanVsWorker(Long workPlanId, Long workerId) throws DaoException{
        String sql = "DELETE FROM `work_plan_vs_worker` WHERE `work_plan_id` = ? and`worker_id`= ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setLong(1, workPlanId);
            statement.setLong(2, workerId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
