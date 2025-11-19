package com.lidia_assignement1.model;

public class SimpleTask extends Task {
    private int startHour;
    private int endHour;

    public SimpleTask(int idTask, String statusTask, int startHour, int endHour) {
        super(idTask, statusTask);
        this.startHour = startHour;
        this.endHour = endHour;
    }

    @Override
    public int estimateDuration() {
        return endHour - startHour;
    }

    @Override
    public String toString() {
        return "SimpleTask{" +
                "idTask=" + getIdTask() +
                ", statusTask='" + getStatusTask() + '\'' +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}