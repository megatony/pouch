package com.akgul.kangroo.pouch.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "USERS")
public class User extends PouchObject {
    @Column(name = "USER_NAME", unique = true)
    private String userName;
}
