package edu.uca.sdc.schedulebuilder.DAOs;

import edu.uca.sdc.schedulebuilder.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class CourseDAO implements DAO<Course> {
    JdbcTemplate jt;

    @Autowired
    public CourseDAO(JdbcTemplate jt) {
        this.jt = jt;
    }

    // Returns course based on CRN key
    @Override
    public Optional<Course> get(String crn_key) {
        // query
        String q = "SELECT * FROM courses WHERE crn_key = ?";
        try {
            // Maps results to course obj
            Course course = jt.queryForObject(q, new Object[]{crn_key}, (rs, rowNum) -> {
                Course c = new Course();
                c.setCrnKey(rs.getString("crn_key"));
                c.setSubjCode(rs.getString("subj_code"));
                c.setCrseNumber(rs.getString("crse_number"));
                c.setCreditHours(rs.getInt("credit_hours"));
                c.setTitle(rs.getString("title"));
                c.setInstructorName(rs.getString("instructor_name"));
                c.setSemester(rs.getString("semester"));
                c.setYear(rs.getInt("year"));
                c.setMeetDays1(rs.getString("meet_days1"));
                c.setBeginTime1(rs.getString("begin_time1"));
                c.setEndTime1(rs.getString("end_time1"));
                c.setMeetDays2(rs.getString("meet_days2"));
                c.setBeginTime2(rs.getString("begin_time2"));
                c.setEndTime2(rs.getString("end_time2"));
                c.setCategory(rs.getString("category"));
                c.setCompleted(rs.getBoolean("completed"));
                return c;
            });
            // wrap in optional to avoid null pointer
            return Optional.of(course);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Returns all courses
    @Override
    public List<Course> getAll() {
        // query
        String q = "SELECT * FROM courses";

        // queries and maps to Course obj
        return jt.query(q, (rs /*row*/, rowNum) -> {
            Course course = new Course();
            course.setCrnKey(rs.getString("crn_key"));
            course.setSubjCode(rs.getString("subj_code"));
            course.setCrseNumber(rs.getString("crse_number"));
            course.setCreditHours(rs.getInt("credit_hours"));
            course.setTitle(rs.getString("title"));
            course.setInstructorName(rs.getString("instructor_name"));
            course.setSemester(rs.getString("semester"));
            course.setYear(rs.getInt("year"));
            course.setMeetDays1(rs.getString("meet_days1"));
            course.setBeginTime1(rs.getString("begin_time1"));
            course.setEndTime1(rs.getString("end_time1"));
            course.setMeetDays2(rs.getString("meet_days2"));
            course.setBeginTime2(rs.getString("begin_time2"));
            course.setEndTime2(rs.getString("end_time2"));
            course.setCategory(rs.getString("category"));
            course.setCompleted(rs.getBoolean("completed"));
            return course;
        });
    }

    // Gets course by title
    public Course getByTitle(String title) {
        // Query
        String q = "SELECT * FROM courses WHERE title LIKE ?";
        List<Course> courses = jt.query(q, new Object[]{"%" + title + "%"}, (rs, rowNum) -> {
            Course course = new Course();
            course.setSubjCode(rs.getString("subj_code"));
            course.setCrseNumber(rs.getString("crse_number"));
            course.setCreditHours(rs.getInt("credit_hours"));
            course.setTitle(rs.getString("title"));
            course.setCategory(rs.getString("category"));
            course.setCompleted(rs.getBoolean("completed"));
            return course;
        });
        // Only return singular course (general, not individual sections)
        return courses.get(0);
    }

    // Get list of courses by title
    public List<Course> getUniqueCourses() {
        // query
        String q = "SELECT DISTINCT ON (title) * FROM courses ORDER BY title, crn_key";

        // Category IDs and titles
        Map<String, String> categoryNames = Map.of(
                "RM", "Required Major",
                "EL", "Elective",
                "CM", "Core Math",
                "AM", "Additional Math",
                "RS", "Required Science",
                "OR", "Other Required"
        );

        // Return resulting course objects
        return jt.query(q, (rs, rowNum) -> {
            Course course = new Course();
            course.setSubjCode(rs.getString("subj_code"));
            course.setCrseNumber(rs.getString("crse_number"));
            course.setCreditHours(rs.getInt("credit_hours"));
            course.setTitle(rs.getString("title"));

            // Map category abbreviation to full name
            String categoryAbbreviation = rs.getString("category");
            String fullCategoryName = categoryNames.getOrDefault(categoryAbbreviation, "Other");
            course.setCategory(fullCategoryName);
            course.setCompleted(rs.getBoolean("completed"));
            return course;
        });
    }

    // Get courses by category
    public List<Course> getByCategory(String category) {
        // Query
        String query = "SELECT * FROM courses WHERE category = ?";
        return jt.query(query, new Object[]{category}, (rs, rowNum) -> {
            Course course = new Course();
            course.setSubjCode(rs.getString("subj_code"));
            course.setCrseNumber(rs.getString("crse_number"));
            course.setCreditHours(rs.getInt("credit_hours"));
            course.setTitle(rs.getString("title"));
            course.setCategory(rs.getString("category"));
            return course;
        });
    }

    // Get remaining (incomplete) courses
    public List<Course> getRemainingCourses() {
        // Query to get unique courses that are not completed and belong to categories with remaining required hours
        String q = "WITH completed_hours AS (" +
                  "  SELECT c.category, SUM(c.credit_hours) as completed_hrs " +
                  "  FROM courses c " +
                  "  WHERE c.completed = true " +
                  "  GROUP BY c.category" +
                  ") " +
                  "SELECT DISTINCT ON (c.title) c.* " +
                  "FROM courses c " +
                  "JOIN categories cat ON c.category = cat.id " +
                  "LEFT JOIN completed_hours ch ON c.category = ch.category " +
                  "WHERE c.completed = false " +
                  "AND (cat.required_hrs > COALESCE(ch.completed_hrs, 0)) " + // coalesce: returns first non-null
                  "ORDER BY c.title, c.crn_key";
        
        return jt.query(q, (rs, rowNum) -> {
            Course course = new Course();
            course.setSubjCode(rs.getString("subj_code"));
            course.setCrseNumber(rs.getString("crse_number"));
            course.setCreditHours(rs.getInt("credit_hours"));
            course.setTitle(rs.getString("title"));
            course.setCategory(rs.getString("category"));
            course.setCompleted(rs.getBoolean("completed"));
            return course;
        });
    }

    // Inserts course into DB
    @Override
    public int save(Course course) {
        // query
        String q = "INSERT INTO courses (crn_key, subj_code, crse_number, credit_hours, title, instructor_name, " +
                "semester, year, meet_days1, begin_time1, end_time1, meet_days2, begin_time2, end_time2, category," +
                " completed) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // Inserts values from course
        int result = jt.update(q,
                course.getCrnKey(),
                course.getSubjCode(),
                course.getCrseNumber(),
                course.getCreditHours(),
                course.getTitle(),
                course.getInstructorName(),
                course.getSemester(),
                course.getYear(),
                course.getMeetDays1(),
                course.getBeginTime1(),
                course.getEndTime1(),
                course.getMeetDays2(),
                course.getBeginTime2(),
                course.getEndTime2(),
                course.getCategory(),
                course.getCompleted()
        );
        //System.out.println("Inserted course w/ CRN: " + course.getCrnKey());
        return result;
    }

    // Updates course information
    @Override
    public void update(Course course) {
        // query to update the completed status
        String q = "UPDATE courses SET completed = ? WHERE title = ?";

        // execute update
        jt.update(q, course.getCompleted(), course.getTitle());
    }

    @Override
    public void delete(String crn_key) {

    }
}