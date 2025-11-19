package com.lidia_assignement1.model;

import java.util.ArrayList;
import java.util.List;

public class ComplexTask extends Task {
    private List<Task> subTasks;

    public ComplexTask(int idTask, String statusTask) {
        super(idTask, statusTask);
        this.subTasks = new ArrayList<>();
    }

    public void addTask(Task task) {
        subTasks.add(task);
    }

    public void deleteTask(Task task) {
        subTasks.remove(task);
    }

    public List<Task> getSubTasks() {
        return subTasks;
    }

    @Override
    public int estimateDuration() {
        return subTasks.stream().mapToInt(Task::estimateDuration).sum();
    }

    @Override
    public String toString() {
        return "ComplexTask{" +
                "idTask=" + getIdTask() +
                ", statusTask='" + getStatusTask() + '\'' +
                ", subTasks=" + subTasks +
                '}';
    }
}