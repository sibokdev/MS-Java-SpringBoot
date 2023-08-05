package org.sibokdev.springcloud.ms.courses.coursesms.models;

import lombok.Data;
public @Data class User {
    private Long id;
    private String name;
    private  String email;
    private String password;
}
