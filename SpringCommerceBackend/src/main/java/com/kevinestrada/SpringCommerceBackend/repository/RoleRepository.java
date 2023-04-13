package com.kevinestrada.SpringCommerceBackend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kevinestrada.SpringCommerceBackend.models.ERole;
import com.kevinestrada.SpringCommerceBackend.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
