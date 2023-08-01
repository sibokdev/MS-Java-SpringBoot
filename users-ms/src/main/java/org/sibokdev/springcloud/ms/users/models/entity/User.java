package org.sibokdev.springcloud.ms.users.models.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Entity
@Table(name="users")
public @Data class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @NotEmpty
    @Column(unique = true)
    private  String email;
    @NotBlank
    private String password;
}
