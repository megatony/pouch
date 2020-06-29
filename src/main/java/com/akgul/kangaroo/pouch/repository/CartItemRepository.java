package com.akgul.kangaroo.pouch.repository;

import com.akgul.kangaroo.pouch.entity.CartItem;
import com.akgul.kangaroo.pouch.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndDeleted(Long cartItemId, boolean deleted);
    List<CartItem> findByProductAndDeleted(Product product, boolean deleted);
}
