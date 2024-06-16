package com.sttivenquiroga.products.mapper;

import com.sttivenquiroga.products.dto.ProductDTO;
import com.sttivenquiroga.products.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO mapProductToProductDTO(Product product);

    @Mapping(target = "category.id", ignore = true)
    Product mapProductDTOToProduct(ProductDTO productDTO);

    List<ProductDTO> mapProductListToProductDtoList(List<Product> products);
}
