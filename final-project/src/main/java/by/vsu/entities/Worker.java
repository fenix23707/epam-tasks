package by.vsu.entities;

import java.util.List;

/**
 * Класс Worker хранит необходимую информацию о рабочем .
 *
 * @author Kovzov Vladislav
 */
public class Worker extends User{
    private Specialization specialization;

    private List<WorkPlan> workPlans;

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public List<WorkPlan> getWorkPlans() {
        return workPlans;
    }

    public void setWorkPlans(List<WorkPlan> workPlans) {
        this.workPlans = workPlans;
    }

}
