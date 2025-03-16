package edu.uca.sdc.schedulebuilder;

import edu.uca.sdc.schedulebuilder.DAOs.CourseDAO;
import edu.uca.sdc.schedulebuilder.entities.Course;
import edu.uca.sdc.schedulebuilder.services.CategoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class CSVReader {

    private final CourseDAO courseDAO;
    private final CategoryService categoryService;

    @Autowired
    public CSVReader(CourseDAO courseDAO, CategoryService categoryService) {
        this.courseDAO = courseDAO;
        this.categoryService = categoryService;
    }

    @PostConstruct
    public void processCSV() {
        // Insert categories into CategoryDAO
        categoryService.insertCategories();

        // folder path
        String csvFolderPath = "src/main/resources/CSV/";

        // Folder with CSV files
        File csvFolder = new File(csvFolderPath);

        // Array of files
        File[] files = csvFolder.listFiles();

        // Save CSV entries to DB
        for (File file : files) {
            try {
                saveToDB(file.getAbsolutePath());
            } catch (IOException e) {
                System.out.println("Error reading file: " + e.getMessage());
            }
        }
    }

    // Save entries to DB
    public void saveToDB(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        int lineCount = 0;

        // Skip header
        reader.readLine();

        // line by line
        while ((line = reader.readLine()) != null) {
            lineCount++;

            // Split by commas, but include empty fields
            String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

            if (values.length == 15) {
                Course course = new Course();
                try {
                    // Store values in Course obj, but enter placeholders for empty vals
                    course.setCrnKey((values[0].isEmpty() ? "0" : values[0])); // CRN Key
                    course.setSubjCode(values[1].isEmpty() ? "" : values[1]); // Subject Code
                    course.setCrseNumber(values[2].isEmpty() ? "" : values[2]); // Course Num
                    course.setCreditHours(Integer.parseInt(values[3].isEmpty() ? "0" : values[3])); // Credit hrs
                    course.setSemester(values[4].isEmpty() ? "" : values[4]); // Semester
                    course.setYear(Integer.parseInt(values[5].isEmpty() ? "0" : values[5])); // Year
                    course.setTitle(values[6].isEmpty() ? "" : values[6]); // Course Title
                    course.setInstructorName(values[7].isEmpty() ? "" : values[7]); // Instructor Name
                    course.setMeetDays1(values[8].isEmpty() ? "" : values[8]); // Meeting Days1
                    course.setBeginTime1(values[9].isEmpty() ? "" : values[9]); // Begin Time1
                    course.setEndTime1(values[10].isEmpty() ? "" : values[10]); // End Time1
                    course.setMeetDays2(values[11].isEmpty() ? "" : values[11]); // Meeting Days2
                    course.setBeginTime2(values[12].isEmpty() ? "" : values[12]); // Begin Time2
                    course.setEndTime2(values[13].isEmpty() ? "" : values[13]); // End Time2
                    course.setCategory(values[14].isEmpty() ? "" : values[14]); // Categories
                    course.setCompleted(false); // Completion status

                    // Save course to DB
                    courseDAO.save(course);
                } catch (Exception e) {
                    System.out.println("Error processing line " + lineCount + ": " + e.getMessage());
                }
            } else {
                System.out.println("Skipping invalid line at line number " + lineCount);
            }
        }
        reader.close();
    }
}
