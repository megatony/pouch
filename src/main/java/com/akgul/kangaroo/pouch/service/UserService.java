package com.akgul.kangaroo.pouch.service;

import com.akgul.kangaroo.pouch.entity.User;
import com.akgul.kangaroo.pouch.controller.UserController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserController userController;

    public User getUserByUserName(String userName) {
        return userController.getUserByUserName(userName);
    }
}
