package com.akgul.kangaroo.pouch.api.controller;

import com.akgul.kangaroo.pouch.api.model.*;
import com.akgul.kangaroo.pouch.entity.Cart;
import com.akgul.kangaroo.pouch.entity.CartItem;
import com.akgul.kangaroo.pouch.entity.Product;
import com.akgul.kangaroo.pouch.entity.User;
import com.akgul.kangaroo.pouch.service.CartItemService;
import com.akgul.kangaroo.pouch.service.CartService;
import com.akgul.kangaroo.pouch.service.ProductService;
import com.akgul.kangaroo.pouch.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@PreAuthorize("hasRole('CUSTOMER') or hasRole('ADMIN')")
@RestController
@RequestMapping("/cart")
public class CartApiController {
    private Logger LOGGER = LoggerFactory.getLogger(CartApiController.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public @ResponseBody
    PouchApiResponse getCart(Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user)) {
            return new NotFoundPouchApiResponse("User not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            LOGGER.debug("Cart is null for user " + authentication.getName() + ", new cart created by default.");
            cart = new Cart();
            cart.setUser(user);
            return new CartApiResponse(cartService.saveCart(cart));
        }

        return new CartApiResponse(cart);
    }

    @RequestMapping(value = "/item/{cartItemId}", method = RequestMethod.GET)
    public @ResponseBody PouchApiResponse getCartItemById(Authentication authentication, @PathVariable(value = "cartItemId") Long cartItemId) {
        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user)) {
            return new NotFoundPouchApiResponse("User not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new NotFoundPouchApiResponse("No active cart found for user. Please start with adding a product to your cart.");
        }

        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new NotFoundPouchApiResponse("Cart item " + cartItemId + " not found");
        }

        return new CartItemApiResponse(cartItem);
    }

    @RequestMapping(value = "/add/{productId}/{purchaseCount}", method = RequestMethod.POST)
    public @ResponseBody PouchApiResponse addProductToCart(Authentication authentication, @PathVariable(value = "productId") Long productId, @PathVariable(value = "purchaseCount") int purchaseCount) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new NotFoundPouchApiResponse("Product id " + productId + " not found.");
        }

        if (purchaseCount <= 0) {
            return new ConflictPouchApiResponse("Purchase count must be bigger than 0.");
        }

        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user)) {
            return new NotFoundPouchApiResponse("User not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new CartApiResponse(cartService.createCart(user, product, purchaseCount));
        }

        CartItem cartItem = cartService.getCartItemOfProductFromCart(cart, product);

        if (ObjectUtils.isEmpty(cartItem)) {
            cart.addCartItem(cartItemService.createCartItem(cart, product, purchaseCount));
            LOGGER.debug("A new cart item created for user " + authentication.getName());
            cartService.saveCart(cart);
            return new CartApiResponse(cart);
        }

        cartItem.increaseQuantity(purchaseCount);

        return new CartApiResponse(cartService.saveCart(cart));
    }

    @RequestMapping(value = "/update/{productId}/{purchaseCount}", method = RequestMethod.PUT)
    public @ResponseBody PouchApiResponse updateProductQuantity(Authentication authentication, @PathVariable(value = "productId") Long productId, @PathVariable(value = "purchaseCount") int purchaseCount) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new NotFoundPouchApiResponse("Product id " + productId + " not found.");
        }

        if (purchaseCount <= 0) {
            return new ConflictPouchApiResponse("Purchase count must be bigger than 0.");
        }

        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user)) {
            return new NotFoundPouchApiResponse("User not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new NotFoundPouchApiResponse("No active cart found for user. Please start with adding a product to your cart.");
        }

        CartItem cartItem = cartService.getCartItemOfProductFromCart(cart, product);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new NotFoundPouchApiResponse("Cart item not found for product id " + productId);
        }

        cartItem.setQuantity(purchaseCount);

        return new CartApiResponse(cartService.saveCart(cart));
    }

    @RequestMapping(value = "/delete/item/{cartItemId}", method = RequestMethod.DELETE)
    public @ResponseBody PouchApiResponse deleteCartItem(Authentication authentication, @PathVariable(value = "cartItemId") Long cartItemId) {
        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user)) {
            return new NotFoundPouchApiResponse("User not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new NotFoundPouchApiResponse("No active cart found for user. Please start with adding a product to your cart.");
        }

        CartItem cartItem = cartItemService.getCartItemById(cartItemId);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new NotFoundPouchApiResponse("Cart item id " + cartItemId + " not found");
        }

        cartItem.setDeleted(true);
        cartItemService.saveCartItem(cartItem);
        cart.getCartItems().remove(cartItem);
        cartService.saveCart(cart);

        CartApiResponse apiResponse = new CartApiResponse(cartItem.getCart());
        String deleteMessage = "Cart item " + cartItemId + " is deleted.";
        LOGGER.debug(deleteMessage);
        apiResponse.setMessage(deleteMessage);
        return apiResponse;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public @ResponseBody PouchApiResponse deleteCart(Authentication authentication) {
        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user)) {
            return new NotFoundPouchApiResponse("User not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new NotFoundPouchApiResponse("There is no active cart for user. Start with adding a product.");
        }

        cart.setDeleted(true);
        cartItemService.deleteCartItems(cart.getCartItems());
        CartApiResponse apiResponse = new CartApiResponse(cartService.saveCart(cart));

        String cartDeleteMessage = "Cart " + cart.getId() + " is deleted.";
        LOGGER.debug(cartDeleteMessage);
        apiResponse.setMessage(cartDeleteMessage);

        return apiResponse;
    }

    @RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE)
    public @ResponseBody PouchApiResponse deleteProductFromCart(Authentication authentication, @PathVariable(value = "productId") Long productId) {
        Product product = productService.getProductById(productId);

        if (ObjectUtils.isEmpty(product)) {
            return new NotFoundPouchApiResponse("Product " + productId + " not found.");
        }

        User user = userService.getUserByUserName(authentication.getName());

        if (ObjectUtils.isEmpty(user)) {
            return new NotFoundPouchApiResponse("User not found.");
        }

        Cart cart = cartService.getCartByUser(user);

        if (ObjectUtils.isEmpty(cart)) {
            return new NotFoundPouchApiResponse("Cart is not found for this user.");
        }

        CartItem cartItem = cartService.getCartItemOfProductFromCart(cart, product);

        if (ObjectUtils.isEmpty(cartItem)) {
            return new NotFoundPouchApiResponse("Cart item not found for this product on this users cart.");
        }

        cartItem.setDeleted(true);

        if (cart.getCartItems().size() == 1) {
            LOGGER.debug("More than one product required for a cart, cart will be deleted.");
            cart.setDeleted(true);
        }

        cartItemService.saveCartItem(cartItem);
        cart.getCartItems().remove(cartItem);

        return new CartApiResponse(cartService.saveCart(cart));
    }
}
