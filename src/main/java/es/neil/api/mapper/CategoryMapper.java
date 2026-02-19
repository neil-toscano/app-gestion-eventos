package es.neil.api.mapper;

import es.neil.api.domain.Category;
import es.neil.api.dto.category.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDto toDto(Category category);
    Category toEntity(CategoryDto categoryDto);

}
