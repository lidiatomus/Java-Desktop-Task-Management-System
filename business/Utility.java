package com.lidia_assignement1.business;

import com.lidia_assignement1.model.Employee;
import com.lidia_assignement1.model.Task;

import java.util.*;
import java.util.stream.Collectors;

public class Utility {

    public static List<Employee> filterAndSortEmployeesByWorkDuration(Map<Employee, List<Task>> tasksMap) {
        return tasksMap.entrySet().stream()
                .filter(entry -> entry.getValue().stream().mapToInt(Task::estimateDuration).sum() > 4)
                .sorted(Comparator.comparingInt(entry -> entry.getValue().stream().mapToInt(Task::estimateDuration).sum()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static Map<String, Map<String, Integer>> calculateTaskStatusCounts(Map<Employee, List<Task>> tasksMap) {
        Map<String, Map<String, Integer>> result = new HashMap<>();

        for (Map.Entry<Employee, List<Task>> entry : tasksMap.entrySet()) {
            Map<String, Integer> statusCounts = new HashMap<>();
            for (Task task : entry.getValue()) {
                String status = task.getStatusTask().equals("Completed") ? "Completed" : "Uncompleted";
                statusCounts.put(status, statusCounts.getOrDefault(status, 0) + 1);
            }
            result.put(entry.getKey().getName(), statusCounts);
        }

        return result;
    }
}