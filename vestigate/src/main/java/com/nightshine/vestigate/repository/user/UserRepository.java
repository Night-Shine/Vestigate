package com.nightshine.vestigate.repository.user;

import com.nightshine.vestigate.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends
        JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username, String email);

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT P FROM User P WHERE P.isDeleted=false ")
    List<User> findAll();

    @Query("SELECT P FROM User P WHERE P.isDeleted=false and P.id=:id")
    Optional<User> findById(UUID id);

    Optional<User> findById(String id);

    @Modifying
    @Query("UPDATE User c SET c.isDeleted=true WHERE c.id=:id")
    void deleteById(UUID id);

    @Modifying
    @Query("UPDATE User c SET c.isDeleted=true WHERE c.id IN :ids")
    void deleteAll(List<UUID> ids);
}