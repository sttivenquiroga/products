package com.sttivenquiroga.products.service;

import com.sttivenquiroga.products.dto.CategoryDTO;
import com.sttivenquiroga.products.dto.ProductDTO;
import com.sttivenquiroga.products.entity.Category;
import com.sttivenquiroga.products.entity.Product;
import com.sttivenquiroga.products.repository.CategoryRepository;
import com.sttivenquiroga.products.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;


@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CacheManager cacheManager;

    @BeforeEach
    public void setUp() {
        ReflectionTestUtils.setField(productService, "cacheManager", cacheManager);
    }

    @Test
    public void updateProduct() {
        Product product = getProduct();
        ProductDTO productDTO = getProductDTO();
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        productService.updateProduct(1, productDTO);
    }

    @Test
    public void updateProductExistingCategory() {
        Product product = getProduct();
        Category category = getCategory();
        ProductDTO productDTO = getProductDTO();
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(product));
        Mockito.when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(category));
        productService.updateProduct(1, productDTO);
    }

    @Test
    public void updateProductError() {
        ProductDTO productDTO = getProductDTO();
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        assertNull(productService.updateProduct(1, productDTO));
    }

    @Test
    public void saveProduct() {
        Mockito.when(categoryRepository.findByName(anyString())).thenReturn(Optional.of(getCategory()));
        productService.save(getProductDTO());
    }

    @Test
    public void saveProductEmptyCategory() {
        Mockito.when(categoryRepository.findByName(anyString())).thenReturn(Optional.empty());
        productService.save(getProductDTO());
    }

    @Test
    public void getProductById() {
        Product product = getProduct();
        Mockito.when(productRepository.getProductById(anyInt())).thenReturn(Optional.of(product));
        Product expected = productService.getProductById(1);
        assertValidations(product, expected);
    }

    @Test
    public void getProductByCategory() {
        List<Product> product = getProductList(3);
        Mockito.when(productRepository.getProductsByCategory_Name(anyString())).thenReturn(Optional.of(product));
        List<Product> expected = productService.getProductByCategory("cualquiera");
        for (int i = 0; i < expected.size(); i++) {
            assertValidations(product.get(i), expected.get(i));
        }
    }

    @Test
    public void getAllProducts() {
        List<Product> product = getProductList(3);
        Mockito.when(productRepository.getAllProducts()).thenReturn(Optional.of(product));
        List<Product> expected = productService.getAllProducts();
        for (int i = 0; i < expected.size(); i++) {
            assertValidations(product.get(i), expected.get(i));
        }
    }

    @Test
    public void deleteProductSuccessful() {
        Cache cache = mock(Cache.class);
        Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);
        Mockito.when(productRepository.getProductById(1)).thenReturn(Optional.of(getProduct()));
        productService.deleteProductById(1);
    }

    @Test
    public void deleteProductAndCacheSuccessful() {
        Cache cache = mock(Cache.class);
        Mockito.when(cacheManager.getCache(anyString())).thenReturn(cache);
        Product product = getProduct();
        product.getCategory().getProducts().add(getProduct());
        Mockito.when(productRepository.getProductById(1)).thenReturn(Optional.of(product));
        productService.deleteProductById(1);
    }

    private static ProductDTO getProductDTO() {
        return ProductDTO.builder().id(1).status("1")
                .category(CategoryDTO.builder()
                        .id(2).name("Vehiculo").build())
                .name("Moto")
                .quantity(2)
                .build();
    }

    private static Product getProduct() {
        return Product.builder().id(1).status("1")
                .category(Category.builder()
                        .id(2).name("Vehiculo").products(new HashSet<>()).build())
                .name("Moto")
                .quantity(2)
                .build();
    }

    private static List<Product> getProductList(int length) {
        List<Product> productList = new ArrayList<>();
        for (int i = 1; i <= length; i++) {
            Product product = Product.builder().id(i).status(String.valueOf(i))
                    .category(Category.builder()
                            .id(i).name("Vehiculo" + i).build())
                    .name("Moto")
                    .quantity(i)
                    .build();
            productList.add(product);
        }
        return productList;
    }

    private static Category getCategory() {
        return Category.builder().id(1).name("Moto").build();
    }

    private static void assertValidations(Product expected, Product received) {
        Assertions.assertEquals(expected.getId(), received.getId());
        Assertions.assertEquals(expected.getQuantity(), received.getQuantity());
        Assertions.assertEquals(expected.getName(), received.getName());
        Assertions.assertEquals(expected.getCategory().getId(), received.getCategory().getId());
        Assertions.assertEquals(expected.getCategory().getName(), received.getCategory().getName());
        Assertions.assertEquals(expected.getStatus(), received.getStatus());
    }
}
