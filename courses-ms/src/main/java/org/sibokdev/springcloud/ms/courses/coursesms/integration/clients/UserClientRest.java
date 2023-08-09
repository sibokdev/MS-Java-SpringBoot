package org.sibokdev.springcloud.ms.courses.coursesms.integration.clients;

import org.sibokdev.springcloud.ms.courses.coursesms.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//se debe poner el nombre del microservicio que se va a consumir
// el nombre debe ser identico al que se encuentra en el properties del servicio.
@FeignClient(name="users-ms", url="users-ms:8001")
public interface UserClientRest {
    @GetMapping("/{id}")
    User detail(@PathVariable Long id);
    @PostMapping("/")
    User create(@RequestBody User user);

    @GetMapping("/users-by-course")
    List<User> getUsersByCourse(@RequestParam Iterable<Long> ids);

}