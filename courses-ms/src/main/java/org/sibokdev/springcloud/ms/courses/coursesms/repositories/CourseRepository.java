package org.sibokdev.springcloud.ms.courses.coursesms.repositories;

import org.sibokdev.springcloud.ms.courses.coursesms.models.entity.Course;
import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course,Long> {
}
