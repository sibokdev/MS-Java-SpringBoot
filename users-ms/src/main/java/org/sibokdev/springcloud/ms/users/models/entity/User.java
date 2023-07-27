package org.sibokdev.springcloud.ms.users.models.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="users")
public @Data class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private  String email;
    private String password;
}
