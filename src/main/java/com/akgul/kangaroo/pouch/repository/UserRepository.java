package com.akgul.kangaroo.pouch.repository;

import com.akgul.kangaroo.pouch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByUserNameAndDeleted(String userName, boolean deleted);
}
