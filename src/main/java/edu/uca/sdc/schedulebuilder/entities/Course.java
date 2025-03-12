package edu.uca.sdc.schedulebuilder.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="courses")
public class Course {
    @Id
    private int crn_key;

    @Column(name = "subj_code", length=4)
    private String subj_code; // (ex. CSCI)

    @Column(name = "crse_number", length=4)
    private String crse_number;

    @Column(name = "credit_hours", length=2)
    private int credit_hours;

    @Column(name = "title", length=40)
    private String title;

    @Column(name = "instructor_name", length=30)
    private String instructor_name;

    @Column(name = "semester", length=6)
    private String semester;

    @Column(name = "year", length=4)
    private int year;

    @Column(name = "meet_days1", length=10)
    private String meet_days1;

    @Column(name = "begin_time1", length=5)
    private String begin_time1;

    @Column(name = "end_time1", length=5)
    private String end_time1;

    @Column(name = "meet_days2", length=10)
    private String meet_days2;

    @Column(name = "begin_time2", length=5)
    private String begin_time2;

    @Column(name = "end_time2", length=5)
    private String end_time2;

    @Column(name = "crn_attributes", length=40)
    private String crn_attributes;

    public Course() {}

    public Course(int crn_key, String subj_code, String crse_number, int credit_hours, String title, String instructor_name, String semester, int year,
                  String meet_days1, String begin_time1, String end_time1, String meet_days2, String begin_time2,
                  String end_time2, String crn_attributes) {
        this.crn_key = crn_key;
        this.subj_code = subj_code;
        this.crse_number = crse_number;
        this.credit_hours = credit_hours;
        this.title = title;
        this.instructor_name = instructor_name;
        this.semester = semester;
        this.year = year;
        this.meet_days1 = meet_days1;
        this.begin_time1 = begin_time1;
        this.end_time1 = end_time1;
        this.meet_days2 = meet_days2;
        this.begin_time2 = begin_time2;
        this.end_time2 = end_time2;
        this.crn_attributes = crn_attributes;
    }

    @JsonProperty("crn_key")
    public int getCrnKey() { return crn_key; }
    public void setCrnKey(int crn_key) { this.crn_key = crn_key; }

    @JsonProperty("subj_code")
    public String getSubjCode() { return subj_code; }
    public void setSubjCode(String subj_code) { this.subj_code = subj_code; }

    @JsonProperty("crse_number")
    public String getCrseNumber() { return crse_number; }
    public void setCrseNumber(String crse_number) { this.crse_number = crse_number; }

    @JsonProperty("credit_hours")
    public int getCreditHours() { return credit_hours; }
    public void setCreditHours(int credit_hours) { this.credit_hours = credit_hours; }

    @JsonProperty("title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @JsonProperty("instructor_name")
    public String getInstructorName() { return instructor_name; }
    public void setInstructorName(String instructor_name) { this.instructor_name = instructor_name; }

    @JsonProperty("semester")
    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    @JsonProperty("year")
    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    @JsonProperty("meet_days1")
    public String getMeetDays1() { return meet_days1; }
    public void setMeetDays1(String meet_days1) { this.meet_days1 = meet_days1; }

    @JsonProperty("meet_days2")
    public String getMeetDays2() { return meet_days2; }
    public void setMeetDays2(String meet_days2) { this.meet_days2 = meet_days2; }

    @JsonProperty("begin_time1")
    public String getBeginTime1() { return begin_time1; }
    public void setBeginTime1(String begin_time1) { this.begin_time1 = begin_time1; }

    @JsonProperty("end_time1")
    public String getEndTime1() { return end_time1; }
    public void setEndTime1(String end_time1) { this.end_time1 = end_time1; }

    @JsonProperty("begin_time2")
    public String getBeginTime2() { return begin_time2; }
    public void setBeginTime2(String begin_time2) { this.begin_time2 = begin_time2; }

    @JsonProperty("end_time2")
    public String getEndTime2() { return end_time2; }
    public void setEndTime2(String end_time2) { this.end_time2 = end_time2; }

    @JsonProperty("crn_attributes")
    public String getCrnAttributes() { return crn_attributes; }
    public void setCrnAttributes(String crn_attributes) { this.crn_attributes = crn_attributes; }
}