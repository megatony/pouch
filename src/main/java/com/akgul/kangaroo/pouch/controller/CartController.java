package com.akgul.kangaroo.pouch.controller;


import com.akgul.kangaroo.pouch.entity.User;
import com.akgul.kangaroo.pouch.entity.Cart;
import com.akgul.kangaroo.pouch.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class CartController {

    @Autowired
    private CartRepository repository;

    public Cart getCartByUser(User user) {
        return repository.getByUserAndDeleted(user, false);
    }

    public Cart saveCart(Cart cart) { return repository.save(cart);}
}
