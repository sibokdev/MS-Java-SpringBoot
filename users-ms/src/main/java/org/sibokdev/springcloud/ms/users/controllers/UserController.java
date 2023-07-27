package org.sibokdev.springcloud.ms.users.controllers;

import org.sibokdev.springcloud.ms.users.models.entity.User;
import org.sibokdev.springcloud.ms.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public User save(@RequestBody User user){
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update (@RequestBody User user, @PathVariable Long id){
        Optional<User> userOptional = userService.getById(id);
        if (userOptional.isPresent()) {
            User userFound = userOptional.get();
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
}
