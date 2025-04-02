package com.uade.tpo.marketplace.controllers;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.marketplace.entity.Categories;
//import com.uade.tpo.marketplace.entity.Categories;
import com.uade.tpo.marketplace.service.CategoryService;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping ("categories") //localhost:8080/categories
public class CategoriesController {
    @GetMapping //todas las categorias (GET)
    public ArrayList<Categories> getCategories() {
        //Categories category = new Categories();
        //category.getDescription();
        CategoryService categoryService = new CategoryService();
        return categoryService.getCategories();//este metodo de resouesta es del CategoryService.java
    }
    @GetMapping("/{categoryId}")//localhost:8080/categories/id (GET)
    public Categories getCategoryById(@PathVariable int categoryId) {
        CategoryService categoryService = new CategoryService();
        return categoryService.getCategoryById(categoryId);
    }
    @PostMapping//localhost:8080/categories (POST)
    public String createCategory(@RequestBody String categoryId) {
        CategoryService categoryService = new CategoryService();
        return categoryService.createCategory(categoryId);
    }
       
}
