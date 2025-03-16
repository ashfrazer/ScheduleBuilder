package edu.uca.sdc.schedulebuilder;

import edu.uca.sdc.schedulebuilder.DAOs.*;
import edu.uca.sdc.schedulebuilder.entities.Category;
import edu.uca.sdc.schedulebuilder.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import edu.uca.sdc.schedulebuilder.DAOs.CourseDAO;

import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class Controller {
    private CourseDAO courseDAO;
    private CategoryDAO categoryDAO;

    private int totalRequiredHours = 78;
    private int completedHours = 0;

    @Autowired
    public Controller(CourseDAO courseDAO, CategoryDAO categoryDAO) {
        this.courseDAO = courseDAO;
        this.categoryDAO = categoryDAO;
    }

    // GET: all sections of courses (not unique)
    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseDAO.getAll();
    }

    // GET: all unique courses
    @GetMapping("/")
    public List<Course> getUniqueCourses() {
        return courseDAO.getUniqueCourses();
    }

//    // GET: course by CRN
//    @GetMapping("/{crn_key}")
//    public ResponseEntity<Course> getCourseByCrn(@PathVariable String crn_key) {
//        Optional<Course> course = courseDAO.get(crn_key);
//
//        if (course.isPresent()) {
//            return ResponseEntity.ok(course.get());
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    // GET: course by title (singular, general)
    @GetMapping("/{title}")
    public ResponseEntity<Course> getCourseByTitle(@PathVariable String title) {
        Course course = courseDAO.getByTitle(title);
            return ResponseEntity.ok(course);
    }

    // PUT: toggle course as complete
    @PutMapping("/toggle-completion/{title}")
    public ResponseEntity<String> toggleCompletion(@PathVariable String title) {
        Course course = courseDAO.getByTitle(title);

        int hoursUpdated = 0;

        // Toggle completion status
        course.setCompleted(!course.getCompleted());

        // if complete, add number of hours to updated hours
        if (course.getCompleted()) {
            hoursUpdated += course.getCreditHours();
        // otherwise, subtract number of hours from updated hours
        } else {
            hoursUpdated -= course.getCreditHours();
        }

        // Update course's completion status
        courseDAO.update(course);

//        debugging
//        System.out.println("course completion toggled!");

        // Add/subtract number of hours from (in)/completed course
        completedHours += hoursUpdated;

        // Update the category total hours (each category has its own unique req. credits)
        updateCategoryRequiredHours();

        // Calc remaining hours (total)
        int remainingHours = totalRequiredHours - completedHours;

        return ResponseEntity.ok("Course completion status updated! Remaining hours: " + remainingHours);
    }

    // updates category's remaining required hours
    private void updateCategoryRequiredHours() {
        // Get all categories
        List<Category> categories = categoryDAO.getAll();

        // Update each category's remaining hours
        for (Category category : categories) {
            int totalCategoryRequiredHours = category.getRequiredHours();
            int totalCategoryCompletedHours = 0;

            // Get courses in category
            List<Course> categoryCourses = courseDAO.getByCategory(category.getTitle());

            // Add each completed course's credits to total category hours
            for (Course course : categoryCourses) {
                if (course.getCompleted()) {
                    totalCategoryCompletedHours += course.getCreditHours();
                }
            }

            // Calc remaining hours
            int remainingCategoryHours = Math.max(0, totalCategoryRequiredHours - totalCategoryCompletedHours);

            // Update category's required hours
            categoryDAO.updateRequiredHours(category.getId(), remainingCategoryHours);
        }
    }

    // GET: all categories
    @GetMapping("/categories")
    public List<Category> getAllCategories() {
        return categoryDAO.getAll();
    }
}
