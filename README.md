# 💻 Java-Desktop-Task-Management-System

A robust, object-oriented task management application built in Java, featuring a graphical user interface (GUI) developed with **Swing**. The system allows for managing employees and assigning both simple and complex tasks, with built-in data persistence and analytical features.

---

## ✨ Features

This application showcases several core concepts of Object-Oriented Programming (OOP) and Java development:

### **Task Management Logic**

* **Employee Management:** Add, view, and manage employee records.
* **Polymorphic Tasks (Composite Pattern):**
    * **SimpleTask:** Represents a task with a clear start and end hour, allowing for easy duration calculation.
    * **ComplexTask:** Functions as a composite task that can contain a list of other `SimpleTask` objects. Its total duration is the sum of its sub-tasks.
* **Task Assignment:** Assign tasks to specific employees for clear workload distribution.
* **Dynamic Operations:** Functionality to add, delete, and modify tasks and employee assignments.

### **Data Analysis & Utility**

* **Workload Analysis:** Utility functions to filter and sort employees based on their total assigned work duration (in hours).
* **Status Tracking:** Calculate and display the number of "Completed" and "Uncompleted" tasks per employee.

### **Technical Implementation**

* **Graphical User Interface (GUI):** An interactive desktop application built using the Java Swing library (`TaskManagementInterface.java`).
* **Data Persistence:** Utilizes Java Serialization (`SerializationUtil.java`) to save the entire state of the `TaskManagement` system to a file (`predefinedData.ser`), ensuring data is preserved between sessions.
* **Separate Business Logic:** Clean separation between the core business logic (`TaskManagement.java`, `Utility.java`) and the user interface.

---

## 🛠️ Technologies Used

* **Language:** Java
* **GUI Library:** Java Swing
* **Core Concepts:** Object-Oriented Programming (OOP), Inheritance, Polymorphism, Java Streams, Serialization, Composite Design Pattern.

---

* **Lidia Tomus**
    * Student at the Technical University of Cluj-Napoca (UTCN)

---

## 🧑‍💻 Author

* **Lidia Tomus**
    * Student at the Technical University of Cluj-Napoca (UTCN)
