package com.example.library.Mapper;

import com.example.library.DTOs.RegisterDTO;
import com.example.library.Models.Patron;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserMapper {
/*    public static RegisterDTO mapToUserDTO(Users users){
        return new RegisterDTO(
            users.getId(),
            users.getFullName(),
            users.getEmail(),
            users.getUsername(),
            users.getPassword(),
            users.getPhoneNumber(),
            users.getCountry(),
            users.getLevel(),
            users.isAdmin(),
            null
        );
    }*/

    public static Patron mapToUsers(RegisterDTO registerDTO){
        return new Patron(
                registerDTO.getFullName(),
                registerDTO.getEmail(),
                registerDTO.getUsername(),
                new BCryptPasswordEncoder().encode(registerDTO.getPassword()),
                registerDTO.getPhoneNumber(),
                registerDTO.getCountry(),
                registerDTO.getAddress()
        );
    }
}
