package com.uade.tpo.marketplace.service;

import java.util.ArrayList;

import com.uade.tpo.marketplace.entity.Categories;
import com.uade.tpo.marketplace.repository.CategoryRepository;

public class CategoryService {
   
    public ArrayList<Categories> getCategories() {
        CategoryRepository categoryRepository = new CategoryRepository();
        return categoryRepository.getCategories();
    }
 
    public Categories getCategoryById(int categoryId) {
        CategoryRepository categoryRepository = new CategoryRepository();
        return categoryRepository.getCategoryById(categoryId);
    }
   
    public String createCategory(String entity) {
        CategoryRepository categoryRepository = new CategoryRepository();
        return categoryRepository.createCategory(entity);
        
    }
}
