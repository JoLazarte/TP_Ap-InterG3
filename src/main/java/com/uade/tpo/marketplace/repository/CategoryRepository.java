package com.uade.tpo.marketplace.repository;

import java.util.ArrayList;
import java.util.Arrays;
import com.uade.tpo.marketplace.entity.Categories;

public class CategoryRepository {//creamos una base de datos en memoria
    public ArrayList<Categories> categories = new ArrayList<Categories>(
        Arrays.asList(Categories.builder().description("electronica").id(1).build(),
                      Categories.builder().description("cocina").id(2).build(),
                      Categories.builder().description("limpieza").id(3).build()
        ));

        public ArrayList<Categories> getCategories(){
            return this.categories;
        }
     
        public Categories getCategoryById(int categoryId){
            return null;
        }
       
        public String createCategory(String entity){
            return null;
        }
}

