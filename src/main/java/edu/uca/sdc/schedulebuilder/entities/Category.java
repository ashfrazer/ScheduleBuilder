package edu.uca.sdc.schedulebuilder.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
@Table(name="categories")
public class Category {
    @Id
    private String id;

    @Column(name = "title", length=25)
    private String title;

    @Column(name = "required_hrs")
    private int requiredHours;

    public Category(String id, String name, int requiredHours) {
        this.id = id;
        this.title = name;
        this.requiredHours = requiredHours;
    }

    public Category() {}

    @JsonProperty("id")
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    @JsonProperty("title")
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    @JsonProperty("required_hrs")
    public int getRequiredHours() { return requiredHours; }
    public void setRequiredHours(int requiredHours) { this.requiredHours = requiredHours; }
}
