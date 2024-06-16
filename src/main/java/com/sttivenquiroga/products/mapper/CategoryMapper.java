package com.sttivenquiroga.products.mapper;

import com.sttivenquiroga.products.dto.CategoryDTO;
import com.sttivenquiroga.products.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {

    Category mapCategoryToCategoryDTO(CategoryDTO categoryDTO);

    CategoryDTO mapCategoryDTOToCategory(Category category);
}
