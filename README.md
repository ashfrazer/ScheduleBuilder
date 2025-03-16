## Welcome to the Schedule Builder! 

This is full-stack web application that is designed to build fast, efficient schedules for students based on their personal time constraints, preferences, and remaining requirements. This is a group project for the course CSCI-3355 Software Design and Construction.

---

### VERSION 0.1.0-*alpha*

**Current** features:
- Individually listed categories for degree program
- Remaining required credit hours in each category
- Interactive courses from which users can toggle completion

**Missing** features:
- Schedule generation
- Prompt for user to select of semesters to make schedules for
- Prompt for user to enter time constraints
- Simple deployability

Installation requirements:
- [Java 23](https://www.oracle.com/java/technologies/downloads/)
- [Maven](https://maven.apache.org/)
- [PostgreSQL](https://www.postgresql.org/download/)
- [Node.js](https://nodejs.org/en/download)

Instructions to run Schedule Builder (commands specific for Windows, adjust accordingly to other OS)
1) Clone the repository.
2) Open project in IDE, if you wish. Otherwise, open a command prompt window and navigate to the project directory.
3) Open 2 *command prompt* windows and navigate to the project directory. Powershell may have issues with the frontend, so it's important to use command prompt.
4) In the IDE's terminal or in a Command Prompt window (if not using an IDE), enter the following commands:
    ```bash
    npm install
    mvn clean install
    mvn spring-boot:run
    ```
    The Spring Boot API should now be running. This will create and activate the PostgreSQL database, read in the CSV files, store in course/category information, and activate handlers.
5) In a separate Command Prompt window, run the following commands:
    ```bash
    cd src\main\resources
    node server.js
    ```
    This will run the backend server, which connects to Port 3000.
6) In the final Command Prompt window, run the following commands:
    ```bash
    cd src\main\resources\public
    http-server
    ```
    This will run the frontend, which connects to Port 8081.
 7) Open your browser and navigate to http://localhost:8081/. You should see a webpage with categories and courses listed.
 8) To terminate the frontend, backend, and Spring Boot API, press `ctrl+c` in each Command Prompt window until they are terminated.
