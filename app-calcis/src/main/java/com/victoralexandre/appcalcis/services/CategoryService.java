package com.victoralexandre.appcalcis.services;

import com.victoralexandre.appcalcis.model.Category;
import com.victoralexandre.appcalcis.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category findFirstByName(String name) {
        Category category = categoryRepository.findFirstByName(name);

        return category;
    }

    public Category instantiateCategory(String name) {
        Category category = new Category(name);

        return category;
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}
