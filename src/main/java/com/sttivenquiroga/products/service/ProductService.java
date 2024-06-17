package com.sttivenquiroga.products.service;

import com.sttivenquiroga.products.dto.ProductDTO;
import com.sttivenquiroga.products.entity.Category;
import com.sttivenquiroga.products.entity.Product;
import com.sttivenquiroga.products.mapper.ProductMapper;
import com.sttivenquiroga.products.repository.CategoryRepository;
import com.sttivenquiroga.products.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private final CacheManager cacheManager;

    @Transactional
    @CachePut(value = "product", key = "#id")
    public Product updateProduct(Integer id, ProductDTO product) {
        Product existProduct = productRepository.findById(id).orElse(null);
        if (existProduct != null) {
            Integer idProduct = existProduct.getId();
            existProduct = productMapper.mapProductDTOToProduct(product);
            existProduct.setId(idProduct);
            Category existCategory = categoryRepository.findByName(existProduct.getCategory().getName()).orElse(null);
            if (existCategory != null) {
                existProduct.setCategory(existCategory);
            } else {
                categoryRepository.save(existProduct.getCategory());
            }
            productRepository.save(existProduct);
        } else {
            return null;
        }
        return existProduct;
    }

    public void save(ProductDTO productDTO) {
        Product product = productMapper.mapProductDTOToProduct(productDTO);
        Category category = categoryRepository.findByName(productDTO.getCategory().getName()).orElse(null);
        if (category != null) {
            product.setCategory(category);
        } else {
            categoryRepository.save(product.getCategory());
        }
        productRepository.save(product);
    }

    @Cacheable(value = "product", key = "#productId")
    public Product getProductById(Integer productId) {
        return productRepository.getProductById(productId).orElse(null);
    }

    @Cacheable(value = "productsByCategory", key = "#categoryName")
    public List<Product> getProductByCategory(String categoryName) {
        return productRepository.getProductsByCategory_Name(categoryName).orElse(Collections.emptyList());
    }
    @Transactional
    public boolean deleteProductById(Integer productId) {
        Product product = productRepository.getProductById(productId).orElse(null);
        if (product != null) {
            Category category = product.getCategory();
            productRepository.delete(product);
            clearCache("product");
            clearCache("productsByCategory");
            if (category.getProducts() != null && category.getProducts().isEmpty()){
                categoryRepository.delete(category);
            }
            return true;
        }
        return false;
    }

    public List<Product> getAllProducts(){
        return productRepository.getAllProducts().orElse(Collections.emptyList());
    }

    public void clearCache(String nameCache){
        Objects.requireNonNull(cacheManager.getCache(nameCache)).clear();
    }
}
