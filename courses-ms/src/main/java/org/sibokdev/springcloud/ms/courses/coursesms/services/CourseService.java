package org.sibokdev.springcloud.ms.courses.coursesms.services;

import org.sibokdev.springcloud.ms.courses.coursesms.models.User;
import org.sibokdev.springcloud.ms.courses.coursesms.models.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> list();
    Optional<Course> findById(Long id);
    Course save(Course course);
    void delete(Long id);
    Optional<User> assignUser(User user, Long id);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> deleteUser(User user, Long courseId);
    Optional<Course> getByIdWithUsers(Long id);
    void deleteCourseUserById(Long id);
}
