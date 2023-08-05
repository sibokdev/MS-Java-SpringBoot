package org.sibokdev.springcloud.ms.courses.coursesms.controllers;

import feign.FeignException;
import org.sibokdev.springcloud.ms.courses.coursesms.models.User;
import org.sibokdev.springcloud.ms.courses.coursesms.models.entity.Course;
import org.sibokdev.springcloud.ms.courses.coursesms.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController {

    @Autowired
    CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> list(){
        return ResponseEntity.ok(courseService.list());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id){
        Optional courseOptional = courseService.findById(id);
        if (courseOptional.isPresent()) {
            return ResponseEntity.ok(courseOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Course courseCreated = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Course course, @PathVariable Long id, BindingResult result){
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<Course> courseOptional = courseService.findById(id);
        if (courseOptional.isPresent()) {
            Course courseToSave = courseOptional.get();
            courseToSave.setId(course.getId());
            courseToSave.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseToSave));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<Course> courseOptional = courseService.findById(id);
        if (courseOptional.isPresent()) {
            Course courseFound = courseOptional.get();
            courseService.delete(courseFound.getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors =new HashMap<String,String>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField().toString(), "Field: " +err.getField() + " "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(@RequestBody User user, @PathVariable Long courseId){
        Optional<User> o;
        try {
            o = courseService.assignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No existe el usuario por el id o error de comunicacion"));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(@RequestBody User user,@PathVariable Long courseId){
        Optional<User> o;
        try {
            o = courseService.createUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No existe el usuario por el id o error de comunicacion"));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-user/{courseId}")
    public ResponseEntity<?> deleteUser(@RequestBody User user,@PathVariable Long courseId){

        Optional<User> o;
        try {
            o = courseService.deleteUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", "No se pudo crear el usuario por el id o error de comunicacion"));
        }
        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-course-user/{id}")
    public ResponseEntity<?> deleteCourseUser(@PathVariable Long id){
        courseService.deleteCourseUserById(id);
        return ResponseEntity.noContent().build();
    }
}

