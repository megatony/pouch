package com.akgul.kangaroo.pouch.repository;

import com.akgul.kangaroo.pouch.entity.User;
import com.akgul.kangaroo.pouch.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order getByUserEqualsAndIdAndDeleted(User user, Long id, boolean deleted);
    List<Order> findAllByUserEqualsAndDeleted(User user, boolean deleted);
}
