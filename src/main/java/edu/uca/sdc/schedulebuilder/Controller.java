package edu.uca.sdc.schedulebuilder;

import edu.uca.sdc.schedulebuilder.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class Controller {
    private CourseDAO courseDAO;

    @Autowired
    public Controller(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }

    // POST
//    @PostMapping("/add")
//    public ResponseEntity<String> addCourse(@RequestBody Course course) {
//        System.out.println("Received course with CRN: " + course.getCrnKey());
//        courseDAO.save(course);
//        return ResponseEntity.ok("Course added successfully!");
//    }


    // GET
    @GetMapping("/{crn_key}")
    public ResponseEntity<Course> getCourseByCrn(@PathVariable int crn_key) {
        Optional<Course> course = courseDAO.get(crn_key);

        if (course.isPresent()) {
            return ResponseEntity.ok(course.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public String defaultEndpoint() {
        return "Welcome to the Schedule Builder!";
    }
}
