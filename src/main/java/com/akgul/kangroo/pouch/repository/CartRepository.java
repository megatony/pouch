package com.akgul.kangroo.pouch.repository;

import com.akgul.kangroo.pouch.entity.Cart;
import com.akgul.kangroo.pouch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart getByUserAndDeleted(User user, boolean deleted);
}
