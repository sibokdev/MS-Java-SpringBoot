package org.sibokdev.springcloud.ms.users.services;

import org.sibokdev.springcloud.ms.users.models.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> list();
    Optional<User> getById(Long id);
    User save(User user);
    void delete(Long id);

    Optional<User> byEmail(String email);
    List<User> findAllById(Iterable<Long> ids);
}
