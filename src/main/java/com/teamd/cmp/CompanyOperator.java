package com.teamd.cmp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CompanyOperator {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;

    private String name;
    private String address;
    private String contact;
    private String email;
    private String password;
    private String token;
}
