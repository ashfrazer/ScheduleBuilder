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
- Smooth deployability

Installation requirements:
- [PostgreSQL](https://www.postgresql.org/download/)
- [Node.js](https://nodejs.org/en/download)

Instructions to run Schedule Builder (commands specific for Windows, adjust accordingly to other OS)
1) **IMPORTANT:** If you do not already have PostgreSQL and Node.js installed, you must install them AND *restart your computer*, otherwise, your device will be unable to run this web app (sorry).
2) Clone the repository.
3) Open project in IDE, if you wish. Otherwise, open a Command Prompt window and navigate to the project directory.
4) Open 2 *Command Prompt* windows and navigate to the project directory. Powershell may have issues with the frontend, so it's important to use command prompt.
5) Go to PgAdmin (PostgreSQL app) and navigate to Servers > Databases. Right-click *Databases* and create a new database called *schedulebuilder*. Press *save* and close PgAdmin.
6) The default username for the PostgreSQL connection is *Postgres*, and the default password is *post*. If these are not your current credentials, please modify the *applications.properties* file to match your credentials.
7) In the IDE's terminal or in a Command Prompt window (if not using an IDE), enter the following commands:
    ```bash
    npm install
    npm install -g http-server
    mvnw clean install
    mvnw spring-boot:run
    ```
    The Spring Boot API should now be running. This will create and activate the PostgreSQL database, read in the CSV files, store in course/category information, and activate handlers.
8) In a separate Command Prompt window, run the following commands:
    ```bash
    cd src\main\resources
    node server.js
    ```
    This will run the backend server, which connects to Port 3000.
9) In the final Command Prompt window, run the following commands:
    ```bash
    cd src\main\resources\public
    http-server
    ```
    This will run the frontend, which connects to Port 8080.
 10) Open your browser and navigate to http://localhost:8080/. You should see a webpage with categories and courses listed. (If for any reason this does not display the courses, try connecting to http://localhost:8081/.
 11) To terminate the frontend, backend, and Spring Boot API, press `CTRL+C` in each Command Prompt window until they are terminated.

If you have any issues, feel free to reach out to me via Discord, where I am very active. My username is @ashfraz.
