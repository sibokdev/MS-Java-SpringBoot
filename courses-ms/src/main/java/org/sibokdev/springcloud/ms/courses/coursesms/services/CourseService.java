package org.sibokdev.springcloud.ms.courses.coursesms.services;

import org.sibokdev.springcloud.ms.courses.coursesms.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> list();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void delete(Long id);
}
