package com.akgul.kangaroo.pouch.service;

import com.akgul.kangaroo.pouch.controller.ProductController;
import com.akgul.kangaroo.pouch.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductController productController;

    @Test
    public void shouldGetProductById() {
        Product product = new Product();
        product.setPrice(BigDecimal.ONE);
        product.setId(1L);

        Mockito.when(productService.getProductById(1L)).thenReturn(product);

        Assert.assertEquals(product, productService.getProductById(1L));
    }

    @Test
    public void shouldGetProducts() {
        Product product1 = new Product();
        product1.setPrice(BigDecimal.ONE);
        product1.setId(1L);

        Product product2 = new Product();
        product2.setPrice(BigDecimal.TEN);
        product2.setId(2L);

        List<Product> products = Arrays.asList(product1, product2);

        Mockito.when(productController.getProducts()).thenReturn(products);

        Assert.assertEquals(products, productService.getProducts());
        Assert.assertEquals(false, productService.getProducts().get(0).isDeleted());

    }
}
