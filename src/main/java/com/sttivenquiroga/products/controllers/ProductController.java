package com.sttivenquiroga.products.controllers;

import com.sttivenquiroga.products.dto.ProductDTO;
import com.sttivenquiroga.products.dto.ResponseMessage;
import com.sttivenquiroga.products.mapper.ProductMapper;
import com.sttivenquiroga.products.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private final ProductService productService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("products")
    public ResponseEntity<ResponseMessage> createProduct(@RequestBody ProductDTO product) {
        productService.save(product);
        return ResponseEntity.ok(ResponseMessage.builder().message("Producto creado con exito").build());
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("products/{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Integer productId) {
        ProductDTO product = productMapper.mapProductToProductDTO(productService.getProductById(productId));
        ResponseEntity response;
        if (product != null) {
            response = ResponseEntity.ok(product);
        } else {
            response = ResponseEntity.noContent().build();
        }
        return response;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("products/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer productId, @RequestBody ProductDTO body) {
        ProductDTO updatedProduct = productMapper.mapProductToProductDTO(productService.updateProduct(productId, body));
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.badRequest().body(ResponseMessage.builder().message("No se pudo actualizar el producto").build());
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("products")
    public ResponseEntity<List<ProductDTO>> getProductByCategory(@RequestParam String categoryName) {
        List<ProductDTO> productDTOList;
        if (categoryName.equalsIgnoreCase("all")) {
            productDTOList = productMapper.mapProductListToProductDtoList(productService.getAllProducts());
        } else {
            productDTOList = productMapper.mapProductListToProductDtoList(productService.getProductByCategory(categoryName));
        }
        return ResponseEntity.ok(productDTOList);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("products/{productId}")
    public ResponseEntity<ResponseMessage> deleteProduct(@PathVariable Integer productId) {
        boolean deleted = productService.deleteProductById(productId);
        ResponseEntity response;
        if (deleted) {
            response = ResponseEntity.ok(ResponseMessage.builder().message("Producto eliminado con exito").build());
        } else {
            response = ResponseEntity.badRequest().body(ResponseMessage.builder().message("El producto no pudo ser eliminado").build());
        }
        return response;
    }

}
