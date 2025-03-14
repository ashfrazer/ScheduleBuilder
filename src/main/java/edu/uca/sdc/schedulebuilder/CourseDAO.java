package edu.uca.sdc.schedulebuilder;

import edu.uca.sdc.schedulebuilder.entities.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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
    public Optional<Course> get(int crn_key) {
        // query
        String q = "SELECT * FROM courses WHERE crn_key = ?";
        try {
            // Maps results to course obj
            Course course = jt.queryForObject(q, new Object[]{crn_key}, (rs, rowNum) -> {
                Course c = new Course();
                c.setCrnKey(rs.getInt("crn_key"));
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
                c.setCrnAttributes(rs.getString("crn_attributes"));
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
            course.setCrnKey(rs.getInt("crn_key"));
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
            course.setCrnAttributes(rs.getString("crn_attributes"));
            return course;
        });
    }

    // Inserts course into DB
    @Override
    public int save(Course course) {
        // query
        String q = "INSERT INTO courses (crn_key, subj_code, crse_number, credit_hours, title, instructor_name, semester, year, meet_days1, begin_time1, end_time1, meet_days2, begin_time2, end_time2, crn_attributes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

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
                course.getCrnAttributes()
        );
        //System.out.println("Inserted course w/ CRN: " + course.getCrnKey());
        return result;
    }


    @Override
    public void update(Course course) {

    }

    @Override
    public void delete(int crn_key) {

    }
}
