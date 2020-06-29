package com.akgul.kangroo.pouch.api.controller;

import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.Order;
import com.akgul.kangroo.pouch.entity.User;
import com.akgul.kangroo.pouch.repository.CartRepository;
import com.akgul.kangroo.pouch.repository.OrderRepository;
import com.akgul.kangroo.pouch.service.CartService;
import com.akgul.kangroo.pouch.service.OrderService;
import com.akgul.kangroo.pouch.service.ProductService;
import com.akgul.kangroo.pouch.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
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
public class OrderApiControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    private HttpHeaders headers = new HttpHeaders();
    private TestRestTemplate restTemplate = new TestRestTemplate();

    private String createApiUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldGetOrdersByUser() {
        User user = userService.getUserByUserName("admin");

        Cart cart = cartService.createCart(user, productService.getProductById(1L), 5);
        cartService.saveCart(cart);

        orderService.createOrder(cart, user, 1L);

        headers.setBasicAuth("admin", "admin");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<List<Order>> cartResponse = restTemplate.exchange(createApiUrl("/order"), HttpMethod.GET, httpEntity, new ParameterizedTypeReference<List<Order>>() {});

        Assert.assertEquals(cartResponse.getBody().get(0).getTotalAmount().intValue(), 20);
    }

    @Test
    public void shouldGetOrderByUser() {
        User user = userService.getUserByUserName("customer");
        Cart cart = cartService.createCart(user, productService.getProductById(2L), 5);

        cartService.saveCart(cart);

        Order order = orderService.createOrder(cart, user, 1L);

        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<Order> cartResponse = restTemplate.exchange(createApiUrl("/order/" + order.getId()), HttpMethod.GET, httpEntity, Order.class);

        Assert.assertEquals(cartResponse.getBody().getTotalAmount().intValue(), 25);
    }

    @Test
    public void shouldCreateOrder() {
        orderRepository.deleteAll();
        cartRepository.deleteAll();
        User user = userService.getUserByUserName("customer");

        Cart cart = cartService.createCart(user, productService.getProductById(3L), 5);

        cartService.saveCart(cart);

        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<Order> cartResponse = restTemplate.exchange(createApiUrl("/order/create/10"), HttpMethod.POST, httpEntity, Order.class);
        Assert.assertEquals(cartResponse.getBody().getTotalAmount().intValue(), 30);
    }
}
