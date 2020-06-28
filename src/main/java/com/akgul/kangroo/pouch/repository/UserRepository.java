package com.akgul.kangroo.pouch.repository;

import com.akgul.kangroo.pouch.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User getByUserNameAndDeleted(String userName, boolean deleted);
}
