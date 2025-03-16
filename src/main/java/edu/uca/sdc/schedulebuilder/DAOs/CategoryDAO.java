package edu.uca.sdc.schedulebuilder.DAOs;

import edu.uca.sdc.schedulebuilder.entities.Category;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CategoryDAO implements DAO<Category> {
    JdbcTemplate jt;

    @Autowired
    public CategoryDAO(JdbcTemplate jt) {
        this.jt = jt;
    }

    // Returns category based on ID
    @Override
    public Optional<Category> get(String id) {
        // Query
        String q = "SELECT * FROM categories WHERE id = ?";
        try {
            // Map results to Category object
            Category category = jt.queryForObject(q, new Object[]{id}, (rs, rowNum) -> {
                Category c = new Category();
                c.setId(rs.getString("id"));
                c.setTitle(rs.getString("title"));
                c.setRequiredHours(rs.getInt("required_hrs"));
                return c;
            });
            return Optional.of(category);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    // Returns all categories
    @Override
    public List<Category> getAll() {
        // Query
        String q = "SELECT * FROM categories";
        // Map results to Category object
        return jt.query(q, (rs, rowNum) -> {
            Category category = new Category();
            category.setId(rs.getString("id"));
            category.setTitle(rs.getString("title"));
            category.setRequiredHours(rs.getInt("required_hrs"));
            return category;
        });
    }

    // Updates a category's remaining required hours
    @Transactional // if update succeeds, changes are committed. Otherwise, rollback. yay, ACID!
    public void updateRequiredHours(String categoryId, int remainingHours) {
        // Query
        String q = "UPDATE categories SET required_hrs = ? WHERE id = ?";
        jt.update(q, remainingHours, categoryId);
    }

    // Inserts category into database
    @Override
    @Transactional
    public int save(Category category) {
        // Query
        String q = "INSERT INTO categories (id, title, required_hrs) VALUES (?, ?, ?)";
        return jt.update(q, category.getId(), category.getTitle(), category.getRequiredHours());
    }

    // Updates categories
    @Override
    public void update(Category category) {
        // Query
        String q = "UPDATE categories SET title = ?, required_hrs = ? WHERE id = ?";
        jt.update(q, category.getTitle(), category.getRequiredHours(), category.getId());
    }

    @Override
    public void delete(String s) {

    }
}