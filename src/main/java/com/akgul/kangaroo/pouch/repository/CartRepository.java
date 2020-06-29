package com.akgul.kangaroo.pouch.repository;

import com.akgul.kangaroo.pouch.entity.Cart;
import com.akgul.kangaroo.pouch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getByUserAndDeleted(User user, boolean deleted);
}
