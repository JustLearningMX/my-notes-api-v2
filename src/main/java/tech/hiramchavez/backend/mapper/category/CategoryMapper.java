package tech.hiramchavez.backend.mapper.category;

import org.mapstruct.*;
import tech.hiramchavez.backend.dto.category.CategoryBodyReqDto;
import tech.hiramchavez.backend.dto.category.CategoryBodyResDto;
import tech.hiramchavez.backend.model.Category;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    Category categoryBodyReqDtoToCategory(CategoryBodyReqDto categoryBodyReqDto);

    CategoryBodyReqDto categoryToCategoryBodyReqDto(Category category);

    CategoryBodyResDto categoryToCategoryBodyResDto(Category category);

    Category toEntity(CategoryBodyResDto categoryBodyResDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryBodyResDto categoryBodyResDto, @MappingTarget Category category);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryBodyReqDto categoryBodyReqDto, @MappingTarget Category category);
}
