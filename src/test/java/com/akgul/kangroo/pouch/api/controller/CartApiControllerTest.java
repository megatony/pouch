package com.akgul.kangroo.pouch.api.controller;


import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.CartItem;
import com.akgul.kangroo.pouch.entity.Product;
import com.akgul.kangroo.pouch.entity.User;
import com.akgul.kangroo.pouch.service.CartService;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@EnableAutoConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CartApiControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    private HttpHeaders headers = new HttpHeaders();
    private TestRestTemplate restTemplate = new TestRestTemplate();

    private String createApiUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void shouldUpdateProductQuantity() {
        User user = userService.getUserByUserName("admin");
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        Product product = productService.getProductById(1L);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);

        cart.setCartItems(cartItems);
        cart.setUser(user);
        cartService.saveCart(cart);

        headers.setBasicAuth("admin", "admin");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<Cart> cartResponse = restTemplate.exchange(createApiUrl("/cart/update/1/" + cart.getCartItems().get(0).getId() + "/5"), HttpMethod.PUT, httpEntity, Cart.class);

        Assert.assertEquals(cartResponse.getBody().getTotalAmount().intValue(), 20);
    }

    @Test
    public void shouldAddProductToCart() {
        User user = userService.getUserByUserName("admin");
        Cart cart = cartService.getCartByUser(user);

        cartService.deleteCart(cart);

        headers.setBasicAuth("admin", "admin");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<Cart> cartResponse = restTemplate.exchange(createApiUrl("/cart/add/1/1"), HttpMethod.POST, httpEntity, Cart.class);
        Assert.assertEquals(cartResponse.getBody().getTotalAmount().intValue(), 4);
    }

    @Test
    public void shouldGetCart() {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();

        CartItem cartItem = new CartItem();
        cartItem.setProduct(productService.getProductById(1L));
        cartItem.setQuantity(1);
        cartItems.add(cartItem);

        cart.setCartItems(cartItems);

        cartService.saveCart(cart);

        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<Cart> cartResponse = restTemplate.exchange(createApiUrl("/cart"), HttpMethod.GET, httpEntity, Cart.class);

        Assert.assertNotNull(cartResponse.getBody());
    }

    @Test
    public void shouldGetCartItem() {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();

        CartItem cartItem = new CartItem();
        cartItem.setProduct( productService.getProductById(1L));

        cartItem.setQuantity(1);
        cartItems.add(cartItem);

        cart.setCartItems(cartItems);

        cartService.saveCart(cart);

        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<CartItem> cartResponse = restTemplate.exchange(createApiUrl("/cart/" + cart.getCartItems().get(0).getId()), HttpMethod.GET, httpEntity, CartItem.class);

        Assert.assertNotNull(cartResponse.getBody());
    }

    @Test
    public void shouldDeleteCartItem() {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        Product product1 = productService.getProductById(3L);
        Product product2 = productService.getProductById(4L);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product1);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);

        CartItem cartItem2 = new CartItem();
        cartItem2.setProduct(product2);
        cartItem2.setQuantity(1);
        cartItems.add(cartItem2);

        cart.setCartItems(cartItems);
        User user = userService.getUserByUserName("customer");
        cart.setUser(user);
        cartService.saveCart(cart);

        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<Cart> cartResponse = restTemplate.exchange(createApiUrl("/cart/delete/item/" + cart.getCartItems().get(0).getId()), HttpMethod.DELETE, httpEntity, Cart.class);

        Assert.assertTrue(cartResponse.getBody().getCartItems().size() == 0);
    }

    @Test
    public void shouldDeleteCart() {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        Product product1 = productService.getProductById(1L);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product1);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);

        cart.setCartItems(cartItems);
        User user = userService.getUserByUserName("customer");
        cart.setUser(user);
        cartService.saveCart(cart);

        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);
        ResponseEntity<Cart> cartResponse = restTemplate.exchange(createApiUrl("/cart/delete"), HttpMethod.DELETE, httpEntity, Cart.class);

        Assert.assertEquals(cartResponse.getBody().getCartItems().size(), 0);
    }

    @Test
    public void shouldDeleteProductFromCart() {
        Cart cart = new Cart();
        List<CartItem> cartItems = new ArrayList<>();
        Product product1 = productService.getProductById(1L);

        CartItem cartItem = new CartItem();
        cartItem.setProduct(product1);
        cartItem.setQuantity(1);
        cartItems.add(cartItem);

        cart.setCartItems(cartItems);
        User user = userService.getUserByUserName("customer");
        cart.setUser(user);
        cartService.saveCart(cart);

        headers.setBasicAuth("customer", "customer");
        HttpEntity<?> httpEntity = new HttpEntity<>("", headers);

        ResponseEntity<Cart> cartResponse = restTemplate.exchange(createApiUrl("/cart/delete/" + cart.getCartItems().get(0).getId() + "/1"), HttpMethod.POST, httpEntity, Cart.class);
        Assert.assertEquals(cartResponse.getBody().getTotalAmount().toString(), "0");

    }

}

