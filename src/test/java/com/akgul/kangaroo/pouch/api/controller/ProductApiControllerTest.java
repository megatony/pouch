package com.akgul.kangaroo.pouch.api.controller;

import com.akgul.kangaroo.pouch.entity.Product;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductApiControllerTest {
    @LocalServerPort
    private int port;

    private HttpHeaders headers = new HttpHeaders();
    private TestRestTemplate restTemplate = new TestRestTemplate();

    private String createApiUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldGetProductList() {
        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<List<Product>> cartResponse = restTemplate.exchange(createApiUrl("/product"), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Product>>() {
        });
        Assert.assertEquals(cartResponse.getBody().get(0).getProductName(), "Product 0");
    }

    @Test
    public void shouldGetProductById() {
        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<Product> cartResponse = restTemplate.exchange(createApiUrl("/product/2"), HttpMethod.GET, httpEntity, Product.class);
        Assert.assertEquals(cartResponse.getBody().getProductName(), "Product 1");
    }
}