package com.example.library.Repository;

import com.example.library.Models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles,Integer> {
    boolean existsByName(String name);

    Roles findByName(String name);

    List<Roles> findAllByNameIn(List<String> names);
}
