package com.akgul.kangroo.pouch.service;

import com.akgul.kangroo.pouch.controller.UserController;
import com.akgul.kangroo.pouch.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserController userController;

    public User getUserByUserName(String userName) {
        return null;
    }
}
