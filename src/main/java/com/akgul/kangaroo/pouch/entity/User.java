package com.akgul.kangaroo.pouch.entity;

import com.akgul.kangaroo.pouch.enums.UserType;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User extends PouchObject {
    @Column(name = "USER_NAME", unique = true)
    private String userName;

    @Enumerated(EnumType.STRING)
    @Column(name = "USER_TYPE")
    private UserType userType = UserType.CUSTOMER;
}
