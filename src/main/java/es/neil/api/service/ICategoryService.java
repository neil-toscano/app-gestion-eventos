package es.neil.api.service;

import es.neil.api.domain.Category;

import java.util.List;

public interface ICategoryService {
    Category findById(Long id);
    Category save(Category category);
    List<Category> findAll();
    Category update(Long id, Category category);
    void deleteById(Long id);

}
