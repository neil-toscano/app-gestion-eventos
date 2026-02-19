package es.neil.api.service;

import es.neil.api.domain.Category;
import es.neil.api.dto.category.CategoryDto;
import es.neil.api.exception.ResourceNotFoundException;
import es.neil.api.repository.ICategoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements ICategoryService {
    private final ICategoryRepository categoryRepository;


    @Override
    @Transactional(readOnly = true)
    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con id: " + id));
    }

    @Override
    @Transactional()
    public Category save(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("El nombre ya existe");
        }
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional()
    public Category update(Long id, Category category) {
        Category existingCategory = this.findById(id);
        existingCategory.setName(category.getName());
        existingCategory.setDescription(category.getDescription());

        return categoryRepository.save(existingCategory);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("No existe el id para borrar");
        }
        categoryRepository.deleteById(id);
    }
}
