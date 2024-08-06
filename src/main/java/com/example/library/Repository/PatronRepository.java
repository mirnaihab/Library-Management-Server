package com.example.library.Repository;

import com.example.library.Models.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatronRepository extends JpaRepository<Patron,Long>{
    Patron findByEmail(String email);
    Patron findByUsername(String username);
    Patron findByUsernameOrEmail(String username, String email);
}

