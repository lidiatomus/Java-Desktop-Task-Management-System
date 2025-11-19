package com.lidia_assignement1.ui;

import com.lidia_assignement1.business.TaskManagement;
import com.lidia_assignement1.business.Utility;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TaskManagementInterface extends JFrame {
    private TaskManagement taskManagement;

    public TaskManagementInterface() {
        try {
            taskManagement = TaskManagement.loadData("predefinedData.ser"); // Load predefined data from file
        } catch (IOException | ClassNotFoundException e) {
            taskManagement = new TaskManagement();
        }

        setTitle("Task Management");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create panels
        JPanel inputPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        // Employee input fields
        JPanel employeePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JTextField employeeNameField = new JTextField(10);
        JButton addEmployeeButton = new JButton("Add Employee");
        addEmployeeButton.addActionListener(e -> {
            String name = employeeNameField.getText();
            if (!name.isEmpty()) {
                taskManagement.addEmployee(name);
                employeeNameField.setText("");
                JOptionPane.showMessageDialog(this, "Employee added successfully!");
            }
        });
        employeePanel.add(new JLabel("Employee Name:"));
        employeePanel.add(employeeNameField);
        employeePanel.add(addEmployeeButton);

        // Simple Task input fields
        JPanel simpleTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JTextField simpleTaskIdField = new JTextField(5);
        JTextField simpleTaskStatusField = new JTextField(10);
        JTextField simpleTaskStartHourField = new JTextField(5);
        JTextField simpleTaskEndHourField = new JTextField(5);
        JButton addSimpleTaskButton = new JButton("Add Simple Task");
        addSimpleTaskButton.addActionListener(e -> {
            try {
                int id = Integer.parseInt(simpleTaskIdField.getText());
                String status = simpleTaskStatusField.getText();
                int startHour = Integer.parseInt(simpleTaskStartHourField.getText());
                int endHour = Integer.parseInt(simpleTaskEndHourField.getText());
                taskManagement.addSimpleTask(id, status, startHour, endHour);
                simpleTaskIdField.setText("");
                simpleTaskStatusField.setText("");
                simpleTaskStartHourField.setText("");
                simpleTaskEndHourField.setText("");
                JOptionPane.showMessageDialog(this, "Simple Task added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid integer values for start and end hours.");
            }
        });
        simpleTaskPanel.add(new JLabel("Simple Task ID:"));
        simpleTaskPanel.add(simpleTaskIdField);
        simpleTaskPanel.add(new JLabel("Status:"));
        simpleTaskPanel.add(simpleTaskStatusField);
        simpleTaskPanel.add(new JLabel("Start Hour:"));
        simpleTaskPanel.add(simpleTaskStartHourField);
        simpleTaskPanel.add(new JLabel("End Hour:"));
        simpleTaskPanel.add(simpleTaskEndHourField);
        simpleTaskPanel.add(addSimpleTaskButton);

        // Complex Task input fields
        JPanel complexTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JTextField complexTaskIdField = new JTextField(5);
        JTextField complexTaskStatusField = new JTextField(10);
        JButton addComplexTaskButton = new JButton("Add Complex Task");
        addComplexTaskButton.addActionListener(e -> {
            int id = Integer.parseInt(complexTaskIdField.getText());
            String status = complexTaskStatusField.getText();
            int option = JOptionPane.showConfirmDialog(this, "Is this complex task made of simple tasks?", "Complex Task Type", JOptionPane.YES_NO_OPTION);
            if (option == JOptionPane.YES_OPTION) {
                List<Integer> selectedTaskIds = selectSimpleTaskIds(); // a list of selected simple task IDs for the complex task
                taskManagement.addComplexTask(id, status, selectedTaskIds);
            } else {
                taskManagement.addComplexTask(id, status);
            }
            complexTaskIdField.setText("");
            complexTaskStatusField.setText("");
            JOptionPane.showMessageDialog(this, "Complex Task added successfully!");
        });
        complexTaskPanel.add(new JLabel("Complex Task ID:"));
        complexTaskPanel.add(complexTaskIdField);
        complexTaskPanel.add(new JLabel("Status:"));
        complexTaskPanel.add(complexTaskStatusField);
        complexTaskPanel.add(addComplexTaskButton);

        // Assign task to employee
        JPanel assignTaskPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JTextField employeeIdField = new JTextField(5);
        JTextField assignTaskIdField = new JTextField(5);
        JButton assignTaskButton = new JButton("Assign Task");
        assignTaskButton.addActionListener(e -> {
            int employeeId = Integer.parseInt(employeeIdField.getText());
            int taskId = Integer.parseInt(assignTaskIdField.getText());
            taskManagement.assignTaskToEmployee(employeeId, taskId);
            employeeIdField.setText("");
            assignTaskIdField.setText("");
            JOptionPane.showMessageDialog(this, "Task assigned successfully!");
        });
        assignTaskPanel.add(new JLabel("Employee ID:"));
        assignTaskPanel.add(employeeIdField);
        assignTaskPanel.add(new JLabel("Task ID:"));
        assignTaskPanel.add(assignTaskIdField);
        assignTaskPanel.add(assignTaskButton);

        // Save and Load buttons
        JButton saveButton = new JButton("Save Data");
        saveButton.addActionListener(e -> {
            try {
                taskManagement.saveData("predefinedData.ser");
                JOptionPane.showMessageDialog(this, "Data saved successfully!");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error saving data: " + ex.getMessage());
            }
        });

        JButton loadButton = new JButton("Load Data");
        loadButton.addActionListener(e -> {
            try {
                taskManagement = TaskManagement.loadData("predefinedData.ser");
                JOptionPane.showMessageDialog(this, "Data loaded successfully!");
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());
            }
        });

        // Buttons to open separate windows for viewing data
        JButton viewEmployeesButton = new JButton("Employees Table");
        viewEmployeesButton.addActionListener(e -> showEmployeesTable());

        JButton viewTasksButton = new JButton("Tasks Table");
        viewTasksButton.addActionListener(e -> showTasksTable());

        JButton viewEmployeeTasksButton = new JButton("Employees and Their Tasks");
        viewEmployeeTasksButton.addActionListener(e -> showEmployeeTasksTable());

        JButton utilityButton = new JButton("Utility");
        utilityButton.addActionListener(e -> showUtilityWindow());

        // Add components to panels
        inputPanel.add(employeePanel);
        inputPanel.add(simpleTaskPanel);
        inputPanel.add(complexTaskPanel);
        inputPanel.add(assignTaskPanel);

        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(viewEmployeesButton);
        buttonPanel.add(viewTasksButton);
        buttonPanel.add(viewEmployeeTasksButton);
        buttonPanel.add(utilityButton);

        // Add panels to frame
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /// Helper methods to show dialog boxes for selecting simple tasks and employees, and to show tables

    /// Helper method to select simple tasks for a complex task
   /* Returns a list of selected simple task IDs
      Returns an empty list if the user cancels the selection
      The dialog box shows a list of simple tasks with their IDs, statuses, start hours, and end hours
      The user can select tasks
    */
    private List<Integer> selectSimpleTaskIds() {
        List<Object[]> simpleTasks = taskManagement.getAllSimpleTasksData(); // Get all simple tasks data
        DefaultListModel<String> listModel = new DefaultListModel<>(); // Create a list model for the JList
        for (Object[] task : simpleTasks) {
            listModel.addElement("ID: " + task[0] + ", Status: " + task[1] + ", Start: " + task[2] + ", End: " + task[3]);
        } // Add simple tasks to the list model
        JList<String> taskList = new JList<>(listModel); // Create a JList with the list model as the data source
        taskList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        int result = JOptionPane.showConfirmDialog(this, new JScrollPane(taskList), "Select Simple Tasks", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            List<String> selectedTasks = taskList.getSelectedValuesList();
            List<Integer> selectedTaskIds = new ArrayList<>();
            for (String task : selectedTasks) {
                selectedTaskIds.add(Integer.parseInt(task.split(",")[0].split(":")[1].trim()));
            }
            return selectedTaskIds; // Return the list of selected simple task IDs
        }
        return new ArrayList<>(); // Return an empty list if the user cancels the selection
    }

    /// Helper methods to show tables: Employees, Tasks, Employees and Their Tasks

    /// Show a table with employees and their work durations
    /*
    *  The table has three columns: Employee ID, Employee Name, Work Duration
    *  The work duration is calculated by the TaskManagement object
    *  The table is displayed in a new JFrame
    * */
    private void showEmployeesTable() {
        String[] columnNames = {"Employee ID", "Employee Name", "Work Duration"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        List<Object[]> employeesData = taskManagement.getAllEmployeesData(); // Get all employees data from TaskManagement
        for (Object[] rowData : employeesData) {
            int employeeId = (int) rowData[0];
            int workDuration = taskManagement.calculateEmployeeWorkDuration(employeeId);
            tableModel.addRow(new Object[]{rowData[0], rowData[1], workDuration});
        }

        JFrame tableFrame = new JFrame("Employees Table");
        tableFrame.setSize(600, 400);
        tableFrame.add(new JScrollPane(table));
        tableFrame.setVisible(true);
    }

    /// Show a table with tasks
    /*
    * The table has six columns: Task ID, Task Status, Start Hour, End Hour, Task Type, Delete
    * The data is obtained from the TaskManagement object and displayed in the table
    **/
    private void showTasksTable() {
        String[] columnNames = {"Task ID", "Task Status", "Start Hour", "End Hour", "Task Type", "Delete"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);

        List<Object[]> tasksData = taskManagement.getAllTasksData(); // Get all tasks data from TaskManagement
        for (Object[] rowData : tasksData) {
            tableModel.addRow(rowData);
        }

        // Add a mouse listener to handle deletion
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (col == 5) { // "Delete" column
                    int taskId = (int) tableModel.getValueAt(row, 0);
                    int complexTaskId = (int) tableModel.getValueAt(row, 0); // Adjust this if needed
                    taskManagement.deleteTaskFromComplexTask(complexTaskId, taskId);
                    tableModel.removeRow(row);

                }
            }
        });

        JFrame tableFrame = new JFrame("Tasks Table");
        tableFrame.setSize(600, 400);
        tableFrame.add(new JScrollPane(table));
        tableFrame.setVisible(true);
    }

    /// Show a table with employees and their tasks
    /*
    * The table has three columns: Employee Name, Task ID, Task Status
    * The data is obtained from the TaskManagement object and displayed in the table
    * The status column is editable, and changes are reflected in the TaskManagement object
     */
    private void showEmployeeTasksTable() {
        String[] columnNames = {"Employee Name", "Task ID", "Task Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 2; // Make the status column editable
            }
        };
        JTable table = new JTable(tableModel);

        List<Object[]> employeeTasksData = taskManagement.getEmployeeTasksData(); // Get employee tasks data from TaskManagement
        for (Object[] rowData : employeeTasksData) {
            tableModel.addRow(rowData); // Add employee tasks to the table
        }

        // Set up the status column with a JComboBox
        TableColumn statusColumn = table.getColumnModel().getColumn(2);
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Completed", "Uncompleted"});
        statusColumn.setCellEditor(new DefaultCellEditor(comboBox));

        // Add a listener to update the task status in the TaskManagement object
        table.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 2) {
                String employeeName = (String) table.getValueAt(row, 0); // Get the employee name
                int taskId = (int) table.getValueAt(row, 1);
                int employeeId = taskManagement.getEmployeeIdByName(employeeName); // Get the employee ID from the name
                if (employeeId != -1) {
                    taskManagement.modifyTaskStatus(employeeId, taskId); // Modify the task status in the TaskManagement object
                }
            }
        });

        JFrame tableFrame = new JFrame("Employees and Their Tasks");
        tableFrame.setSize(600, 400);
        tableFrame.add(new JScrollPane(table));
        tableFrame.setVisible(true);
    }

    /// Show a utility window with two tables: Filtered and sorted employees, Task status counts
    /*
    * The first table shows the filtered and sorted employees with their work durations
    * The second table shows the task status counts for each employee
    * The data is obtained from the TaskManagement object and displayed in the tables
     */
    private void showUtilityWindow() {
        JFrame utilityFrame = new JFrame("Utility");
        utilityFrame.setSize(800, 600);
        utilityFrame.setLayout(new GridLayout(2, 1));

        // Filtered and sorted employees table
        String[] employeeColumnNames = {"Employee Name", "Work Duration"};
        DefaultTableModel employeeTableModel = new DefaultTableModel(employeeColumnNames, 0);
        JTable employeeTable = new JTable(employeeTableModel);

        List<Object[]> filteredEmployeesData = taskManagement.getFilteredAndSortedEmployeesData(); // Get filtered and sorted employees data
        for (Object[] rowData : filteredEmployeesData) {
            employeeTableModel.addRow(rowData); // Add filtered and sorted employees to the table
        }

        // Task status counts table
        String[] statusColumnNames = {"Employee Name", "Completed Tasks", "Uncompleted Tasks"};
        DefaultTableModel statusTableModel = new DefaultTableModel(statusColumnNames, 0);
        JTable statusTable = new JTable(statusTableModel);

        Map<String, Map<String, Integer>> taskStatusCounts = Utility.calculateTaskStatusCounts(taskManagement.getTasksMap()); // Calculate task status counts
        for (Map.Entry<String, Map<String, Integer>> entry : taskStatusCounts.entrySet()) {
            String employeeName = entry.getKey();
            int completedTasks = entry.getValue().getOrDefault("Completed", 0);
            int uncompletedTasks = entry.getValue().getOrDefault("Uncompleted", 0);
            statusTableModel.addRow(new Object[]{employeeName, completedTasks, uncompletedTasks});
        }

        utilityFrame.add(new JScrollPane(employeeTable));
        utilityFrame.add(new JScrollPane(statusTable));
        utilityFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TaskManagementInterface frame = new TaskManagementInterface();
            frame.setVisible(true);
        });
    }
}