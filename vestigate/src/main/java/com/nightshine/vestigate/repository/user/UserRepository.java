package com.nightshine.vestigate.repository.user;

import com.nightshine.vestigate.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends
        MongoRepository<User, String>, CustomUserRepository<User, String> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("{isDeleted:false}")
    List<User> findAll();

    @Query("{isDeleted:false, id:?0}")
    Optional<User> findById(String id);
}