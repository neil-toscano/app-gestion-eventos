package es.neil.api.rest_controller;

import es.neil.api.domain.Category;
import es.neil.api.dto.category.CategoryDto;
import es.neil.api.mapper.CategoryMapper;
import es.neil.api.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final ICategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAll() {
        List<Category> categories = categoryService.findAll();
        List<CategoryDto> dtos = categories.stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        return ResponseEntity.ok(categoryMapper.toDto(category));
    }

    @PostMapping
    public ResponseEntity<CategoryDto> create(@Valid  @RequestBody CategoryDto categoryDto) {
        Category category = categoryMapper.toEntity(categoryDto);
        Category savedCategory = categoryService.save(category);
        return new ResponseEntity<>(categoryMapper.toDto(savedCategory), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> update(@PathVariable Long id, @RequestBody CategoryDto categoryDto) {
        Category categoryData = categoryMapper.toEntity(categoryDto);
        Category updatedCategory = categoryService.update(id, categoryData);
        return ResponseEntity.ok(categoryMapper.toDto(updatedCategory));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
