package com.akgul.kangroo.pouch.repository;

import com.akgul.kangroo.pouch.entity.CartItem;
import com.akgul.kangroo.pouch.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndDeleted(Long cartItemId, boolean deleted);
    List<CartItem> findByProductAndDeleted(Product product, boolean deleted);
}
