package com.sttivenquiroga.products.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sttivenquiroga.products.dto.CategoryDTO;
import com.sttivenquiroga.products.dto.ProductDTO;
import com.sttivenquiroga.products.entity.Category;
import com.sttivenquiroga.products.entity.Product;
import com.sttivenquiroga.products.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
public class ProductControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final String commonUrl = "/products/v1/products";

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void createProduct() throws Exception {
        ProductDTO productRequest = getProductDTO();
        String productJason = objectMapper.writeValueAsString(productRequest);
        mockMvc.perform(post(commonUrl)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJason))
                .andExpect(status().isOk());
    }

    @Test
    public void getProductById() throws Exception {
        Product product = getProduct();
        when(productService.getProductById(1)).thenReturn(product);
        mockMvc.perform(get(commonUrl + "/{productId}", 1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(product.getId()))
                .andExpect(jsonPath("$.status").value(product.getStatus()))
                .andExpect(jsonPath("$.category.id").value(product.getCategory().getId()))
                .andExpect(jsonPath("$.category.name").value(product.getCategory().getName()))
                .andExpect(jsonPath("$.name").value(product.getName()))
                .andExpect(jsonPath("$.quantity").value(product.getQuantity()));
    }

    @Test
    public void updateProductSuccess() throws Exception {
        ProductDTO productDTO = getProductDTO();
        Product productSaved = getProduct();
        String productJson = objectMapper.writeValueAsString(productDTO);
        when(productService.updateProduct(1, productDTO)).thenReturn(productSaved);
        mockMvc.perform(put(commonUrl + "/{productId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productSaved.getId()))
                .andExpect(jsonPath("$.status").value(productSaved.getStatus()))
                .andExpect(jsonPath("$.category.id").value(productSaved.getCategory().getId()))
                .andExpect(jsonPath("$.category.name").value(productSaved.getCategory().getName()))
                .andExpect(jsonPath("$.name").value(productSaved.getName()))
                .andExpect(jsonPath("$.quantity").value(productSaved.getQuantity()));
    }

    @Test
    public void updateProductError() throws Exception {
        ProductDTO productDTO = getProductDTO();
        String productJson = objectMapper.writeValueAsString(productDTO);
        when(productService.updateProduct(1, productDTO)).thenReturn(null);
        mockMvc.perform(put(commonUrl + "/{productId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(productJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getProductByCategory() throws Exception {
        List<Product> responseDb = getProductList(3);
        when(productService.getProductByCategory("vehiculo")).thenReturn(responseDb);
        mockMvc.perform(get(commonUrl)
                        .queryParam("categoryName", "vehiculo")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteProduct() throws Exception {
        mockMvc.perform(delete(commonUrl+"/{productId}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
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
                        .id(2).name("Vehiculo").build())
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

}
