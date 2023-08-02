package org.sibokdev.springcloud.ms.users.integration.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="courses-ms", url="localhost:8002")
public interface CourseClientRest {
    @DeleteMapping("/delete-course-user/{id}")
    void deleteCourseUser(@PathVariable Long id);
}
