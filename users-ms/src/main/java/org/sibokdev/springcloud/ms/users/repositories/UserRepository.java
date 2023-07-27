package org.sibokdev.springcloud.ms.users.repositories;

import org.sibokdev.springcloud.ms.users.models.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Long> {
}
