package com.lidia_assignement1.business;

import com.lidia_assignement1.model.ComplexTask;
import com.lidia_assignement1.model.Employee;
import com.lidia_assignement1.model.SimpleTask;
import com.lidia_assignement1.model.Task;
import com.lidia_assignement1.serialization.SerializationUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class TaskManagement implements Serializable {
    private Map<Employee, List<Task>> tasksMap;
    private List<Task> allTasks;

    public TaskManagement() {
        this.tasksMap = new HashMap<>();
        this.allTasks = new ArrayList<>();
    }

    // assigns a task to an employee by adding the task to the list of tasks of the employee, by  using the id of the employee and the id of the task, called in the interface to assign a task to an employee
    public void assignTaskToEmployee(int employeeId, int taskId) {
        Employee employee = findEmployeeById(employeeId);
        Task task = findTaskById(taskId);
        if (employee != null && task != null) {
            tasksMap.computeIfAbsent(employee, k -> new ArrayList<>()).add(task);
        }
        try {
            saveData("predefinedData.ser"); // Save the data after deletion
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // calculates the total duration of the tasks of an employee that have the status "Completed"
    public int calculateEmployeeWorkDuration(int employeeId) {
        Employee employee = findEmployeeById(employeeId);
        if (employee != null) {
            return tasksMap.getOrDefault(employee, new ArrayList<>())
                    .stream().filter(task -> "Completed".equals(task.getStatusTask()))
                    .mapToInt(Task::estimateDuration).sum();
        }
        return 0;
    }

    // modifies the status of a task of an employee by changing it from "Completed" to "Uncompleted" and vice versa
    public void modifyTaskStatus(int idEmployee, int idTask) {
        Employee employee = findEmployeeById(idEmployee);
        if (employee != null) {
            List<Task> tasks = tasksMap.get(employee);
            if (tasks != null) {
                for (Task task : tasks) {
                    if (task.getIdTask() == idTask) {
                        String currentStatus = task.getStatusTask();
                        task.setStatusTask(currentStatus.equals("Completed") ? "Uncompleted" : "Completed");
                        try {
                            saveData("predefinedData.ser"); // Save the data after deletion
                        } catch (IOException e) {
                            e.fillInStackTrace();                        }
                        break;
                    }
                }
            }
        }
    }

    // returns a list of all the employees
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(tasksMap.keySet());
    }

    // returns a list of all the tasks
    public List<Task> getAllTasks() {
        return allTasks;
    }

    // returns a list of all the data of the employees, it is used for the JTable
    public List<Object[]> getAllEmployeesData() {
        List<Object[]> data = new ArrayList<>();
        for (Employee employee : getAllEmployees()) {
            data.add(new Object[]{employee.getIdEmployee(), employee.getName()});
        }
        return data;
    }

    // returns a list of all the data of the tasks, it is used for the JTable
    public List<Object[]> getAllTasksData() {
        List<Object[]> data = new ArrayList<>();
        for (Task task : getAllTasks()) {
            if (task instanceof SimpleTask simpleTask) {
                data.add(new Object[]{simpleTask.getIdTask(), simpleTask.getStatusTask(), simpleTask.getStartHour(), simpleTask.getEndHour(), "Simple"});
            } else {
                data.add(new Object[]{task.getIdTask(), task.getStatusTask(), "-", "-", "Complex"});
            }
        }
        return data;
    }

    // returns a list of all the data of the tasks of an employee, it is used for the JTable
    public List<Object[]> getEmployeeTasksData() {
        List<Object[]> data = new ArrayList<>();
        for (Map.Entry<Employee, List<Task>> entry : tasksMap.entrySet()) {
            Employee employee = entry.getKey();
            for (Task task : entry.getValue()) {
                data.add(new Object[]{employee.getName(), task.getIdTask(), task.getStatusTask()});
            }
        }
        return data;
    }

    // adds a task to the list of all tasks
    public void addTask(Task task) {
        allTasks.add(task);
        try {
            saveData("predefinedData.ser"); // Save the data after deletion
        } catch (IOException e) {
            e.fillInStackTrace();        }
    }

    // adds a simple task to the list of all tasks, called in the interface to add a simple task
    public void addSimpleTask(int id, String status, int startHour, int endHour) {
        SimpleTask task = new SimpleTask(id, status, startHour, endHour);
        addTask(task);
        try {
            saveData("predefinedData.ser"); // Save the data after deletion
        } catch (IOException e) {
            e.fillInStackTrace();        }
    }

    // adds a complex task to the list of all tasks, it is made of a list of simple tasks, called in the interface to add a complex task
    public void addComplexTask(int id, String status, List<Integer> simpleTaskIds) {
        ComplexTask complexTask = new ComplexTask(id, status);
        for (Integer taskId : simpleTaskIds) {
            Task task = findTaskById(taskId);
            if (task instanceof SimpleTask) {
                complexTask.addTask(task);
            }
        }
        addTask(complexTask); // Add the complex task to the allTasks list
        try {
            saveData("predefinedData.ser"); // Save the data after deletion
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // adds a complex task to the list of all tasks, it is a standalone complex task, called in the interface to add a complex task
    public void addComplexTask(int id, String status) {
        ComplexTask complexTask = new ComplexTask(id, status);
        addTask(complexTask);
        try {
            saveData("predefinedData.ser"); // Save the data after deletion
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // adds an employee to the list of all employees
    public void addEmployee(String name) {
        Employee employee = new Employee(getAllEmployees().size() + 1, name);
        tasksMap.putIfAbsent(employee, new ArrayList<>());
        try {
            saveData("predefinedData.ser"); // Save the data after deletion
        } catch (IOException e) {
            e.fillInStackTrace();
        }
    }

    // returns the employee with the given id, used for the assignTaskToEmployee method and for the calculateEmployeeWorkDuration method
    public Employee findEmployeeById(int id) {
        return tasksMap.keySet().stream().filter(e -> e.getIdEmployee() == id).findFirst().orElse(null);
    }

    // returns the task with the given id, used for the assignTaskToEmployee method and for the modifyTaskStatus method
    public Task findTaskById(int id) {
        return allTasks.stream().filter(t -> t.getIdTask() == id).findFirst().orElse(null);
    }

    // returns the data of the simple tasks, used in the TaskManagementInterface to display the simple tasks
    public List<Object[]> getAllSimpleTasksData() {
        List<Object[]> simpleTasksData = new ArrayList<>();
        for (Task task : allTasks) {
            if (task instanceof SimpleTask simpleTask) {
                simpleTasksData.add(new Object[]{simpleTask.getIdTask(), simpleTask.getStatusTask(), simpleTask.getStartHour(), simpleTask.getEndHour()});
            }
        }
        return simpleTasksData;
    }

    // returns the id of an employee by using the name of the employee, used in the TaskManagementInterface to assign a task to an employee
    public int getEmployeeIdByName(String name) {
        return tasksMap.keySet().stream()
                .filter(e -> e.getName().equals(name))
                .findFirst()
                .map(Employee::getIdEmployee)
                .orElse(-1);
    }

    // returns the TaskMap, it is used in the utility window to calculate and display the number of tasks of the employees
    public Map<Employee, List<Task>> getTasksMap() {
        return tasksMap;
    }

    // return filtered and sorted employees data, it is used in the utility window to calculate and display the number of tasks of the employees
    public List<Object[]> getFilteredAndSortedEmployeesData() {
        List<Object[]> data = new ArrayList<>();
        List<Employee> filteredEmployees = Utility.filterAndSortEmployeesByWorkDuration(tasksMap);
        for (Employee employee : filteredEmployees) {
            int workDuration = calculateEmployeeWorkDuration(employee.getIdEmployee());
            data.add(new Object[]{employee.getName(), workDuration});
        }
        return data;
    }

    // deletes a task from a complex task, used in the TaskManagementInterface to delete a subtask from a complex task
    public void deleteTaskFromComplexTask(int complexTaskId, int taskId) {
        Task task = findTaskById(complexTaskId);
        if (task instanceof ComplexTask) {
            Task subTask = findTaskById(taskId);
            if (subTask != null) {
                ((ComplexTask) task).deleteTask(subTask);
                allTasks.remove(subTask); // Remove the sub-task from the allTasks list
                // Remove the sub-task from the tasksMap
                for (List<Task> tasks : tasksMap.values()) {
                    tasks.remove(subTask);
                }
                System.out.println("Sub-task removed: " + subTask);
            } else {
                System.out.println("Sub-task not found: " + taskId);
            }

            try {
                saveData("predefinedData.ser"); // Save the data after deletion
                System.out.println("Data saved successfully.");
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        } else {
            System.out.println("Complex task not found: " + complexTaskId);
        }
    }

    // saves the data to a file
    public void saveData(String fileName) throws IOException {
        SerializationUtil.serialize(this, fileName);
    }

    // loads the data from a file
    public static TaskManagement loadData(String fileName) throws IOException, ClassNotFoundException {
        return (TaskManagement) SerializationUtil.deserialize(fileName);
    }

}