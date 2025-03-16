package edu.uca.sdc.schedulebuilder.services;

import edu.uca.sdc.schedulebuilder.DAOs.CategoryDAO;
import edu.uca.sdc.schedulebuilder.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    private final CategoryDAO categoryDAO;

    @Autowired
    public CategoryService(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public void insertCategories() {
        Category[] categories = {
                new Category("RM", "Required Major", 42),
                new Category("EL", "Elective", 12),
                new Category("CM", "Core Math", 13),
                new Category("AM", "Additional Math", 3),
                new Category("RS", "Required Science", 4),
                new Category("OR", "Other Required", 4)
        };

        for (Category category : categories) {
            categoryDAO.save(category);
        }
    }
}

