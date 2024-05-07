package com.victoralexandre.appcalcis.controllers;

import com.victoralexandre.appcalcis.model.Category;
import com.victoralexandre.appcalcis.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CategoryController {

    @Autowired
    private CategoryRepository  categoryRepository;

    @GetMapping("/categories/create")
    public ModelAndView pageCreateCategory() {
        ModelAndView mv = new ModelAndView("createCategory.html");

        return mv;
    }

    @PostMapping("/categories/create")
    public ModelAndView registerCategory(@RequestParam String name) {

        Category category = new Category(name);

        categoryRepository.save(category);

        return new ModelAndView("redirect:/products/create");
    }

}
