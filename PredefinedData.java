package com.lidia_assignement1;

import com.lidia_assignement1.business.TaskManagement;
import com.lidia_assignement1.model.SimpleTask;

import java.io.IOException;
import java.util.Arrays;

public class PredefinedData {
    public static void main(String[] args) {
        TaskManagement taskManagement = new TaskManagement();

        // Add Employees
        taskManagement.addEmployee("Alice");
        taskManagement.addEmployee("Bob");
        taskManagement.addEmployee("Charlie");
        taskManagement.addEmployee("David");
        taskManagement.addEmployee("Eve");

        // Add Simple Tasks
        SimpleTask task1 = new SimpleTask(101, "In Progress", 9, 12);
        SimpleTask task2 = new SimpleTask(102, "Completed", 14, 16);
        SimpleTask task3 = new SimpleTask(103, "Pending", 10, 13);
        SimpleTask task4 = new SimpleTask(104, "In Progress", 11, 15);
        SimpleTask task5 = new SimpleTask(105, "Completed", 13, 17);

        taskManagement.addSimpleTask(task1.getIdTask(), task1.getStatusTask(), task1.getStartHour(), task1.getEndHour());
        taskManagement.addSimpleTask(task2.getIdTask(), task2.getStatusTask(), task2.getStartHour(), task2.getEndHour());
        taskManagement.addSimpleTask(task3.getIdTask(), task3.getStatusTask(), task3.getStartHour(), task3.getEndHour());
        taskManagement.addSimpleTask(task4.getIdTask(), task4.getStatusTask(), task4.getStartHour(), task4.getEndHour());
        taskManagement.addSimpleTask(task5.getIdTask(), task5.getStatusTask(), task5.getStartHour(), task5.getEndHour());

        // Add Complex Task made of Simple Tasks
        taskManagement.addComplexTask(200, "Pending", Arrays.asList(101, 102));

        // Add standalone Complex Task
        taskManagement.addComplexTask(201, "In Progress");

        // Assign tasks to employees
        taskManagement.assignTaskToEmployee(1, 101);
        taskManagement.assignTaskToEmployee(2, 102);
        taskManagement.assignTaskToEmployee(1, 200);
        taskManagement.assignTaskToEmployee(3, 201);

        // Save predefined data
        try {
            taskManagement.saveData("predefinedData.ser");
            System.out.println("Predefined data saved successfully.");
        } catch (IOException e) {
            e.fillInStackTrace();        }
    }
}