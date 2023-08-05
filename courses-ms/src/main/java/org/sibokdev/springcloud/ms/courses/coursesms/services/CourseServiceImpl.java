package org.sibokdev.springcloud.ms.courses.coursesms.services;

import org.sibokdev.springcloud.ms.courses.coursesms.integration.clients.UserClientRest;
import org.sibokdev.springcloud.ms.courses.coursesms.models.User;
import org.sibokdev.springcloud.ms.courses.coursesms.models.entity.Course;
import org.sibokdev.springcloud.ms.courses.coursesms.models.entity.CourseUser;
import org.sibokdev.springcloud.ms.courses.coursesms.repositories.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService{
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    UserClientRest userClientRest;
    @Override
    @Transactional(readOnly = true)
    public List<Course> list() {
        return (List<Course>) courseRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> findById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long id) {
        Optional<Course> o = courseRepository.findById(id);
        if(o.isPresent()) {
            User userMs = userClientRest.detail(user.getId());
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMs.getId());
            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMs);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> o = courseRepository.findById(courseId);
        if(o.isPresent()) {
            User userMs = userClientRest.create(user);
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMs.getId());
            course.addCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMs);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> deleteUser(User user, Long CourseId) {
        Optional<Course> o = courseRepository.findById(CourseId);
        if(o.isPresent()) {
            User userMS = userClientRest.detail(user.getId());
            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMS.getId());
            course.removeCourseUser(courseUser);
            courseRepository.save(course);
            return Optional.of(userMS);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> getByIdWithUsers(Long id) {
        Optional o = courseRepository.findById(id);
        if(o.isPresent()) {
            Course course = (Course) o.get();
            if(!course.getCourseUsers().isEmpty()){
                List<Long> ids = course.getCourseUsers().stream().map(cu->cu.getUserId()).
                        collect(Collectors.toList());
                List<User> Users = userClientRest.getUsersByCourse(ids);
                course.setUsers(Users);
            }
            return Optional.of(course);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteCourseUserById(Long id) {
        courseRepository.deleteCourseUserById(id);
    }

}
