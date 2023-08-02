package org.sibokdev.springcloud.ms.users.controllers;

import org.sibokdev.springcloud.ms.users.models.entity.User;
import org.sibokdev.springcloud.ms.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping
    public List<User> list(){
        return userService.list();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<User> userOptional = userService.getById(id);
        if(userOptional.isPresent()){
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> save(@Valid @RequestBody User user, BindingResult result){
        if (user.getEmail().isEmpty() && userService.byEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections.singletonMap("Message", "An user with the email already exists"));
        }
        if (result.hasErrors()) {
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@Valid @RequestBody User user, @PathVariable Long id, BindingResult result){
        if (result.hasErrors()) {
            return validate(result);
        }
        Optional<User> userOptional = userService.getById(id);
        if (userOptional.isPresent()) {
            User userFound = userOptional.get();

            if (!user.getEmail().equalsIgnoreCase(userFound.getEmail()) && userService.byEmail(user.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections.singletonMap("Message", "An user with the email already exists"));
            }

            user.setEmail(user.getEmail());
            user.setName(user.getName());
            user.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(userFound));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Optional<User> userOptional = userService.getById(id);
        if (userOptional.isPresent()) {
            User userFound = userOptional.get();
            userService.delete(userFound.getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }

    }
    @GetMapping("/users-by-course")
    public ResponseEntity<?> getUsersByCourse(@RequestParam List<Long> ids){
        return ResponseEntity.ok(userService.findAllById(ids));
    }
    private static ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors =new HashMap<String,String>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField().toString(), "Field: " +err.getField() + " "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }
}
