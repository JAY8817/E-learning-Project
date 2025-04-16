package com.codewithjay.Reposetories;

import com.codewithjay.Entities.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Roles, String> {

    Optional<Roles> findByRoleName(String roleName);
}
