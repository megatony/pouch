package com.akgul.kangaroo.pouch.controller;

import com.akgul.kangaroo.pouch.entity.User;
import com.akgul.kangaroo.pouch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    public User getUserByUserName(String userName) {
        return repository.getByUserNameAndDeleted(userName, false);
    }
}
