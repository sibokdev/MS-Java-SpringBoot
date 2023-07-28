package org.sibokdev.springcloud.ms.courses.coursesms.models.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="courses")
public @Data class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

}
