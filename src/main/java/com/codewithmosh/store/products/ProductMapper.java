package com.codewithmosh.store.products;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "category.id", target = "categoryId")
    ProductDto toDto(Product product);

    ProductSimpleDto toSimpleDto(Product product);

    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductDtoForCreateUpdate productDtoForCreateUpdate);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    void update(ProductDtoForCreateUpdate productDtoForCreateUpdate, @MappingTarget Product product);
}
