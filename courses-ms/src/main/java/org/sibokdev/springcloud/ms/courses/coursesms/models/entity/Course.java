package org.sibokdev.springcloud.ms.courses.coursesms.models.entity;

import lombok.Data;
import org.sibokdev.springcloud.ms.courses.coursesms.models.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="courses")
public @Data class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;

    @OneToMany(cascade =  CascadeType.ALL, orphanRemoval = true)
    private List<CourseUser> courseUsers;

    @Transient
    private List<User> users;
    public Course(){
        courseUsers = new ArrayList<>();
    }

    public void addCourseUser(CourseUser courseUser){
        courseUsers.add(courseUser);
    }

    public void removeCourseUser(CourseUser courseUser){
        courseUsers.remove(courseUser);
    }
}
