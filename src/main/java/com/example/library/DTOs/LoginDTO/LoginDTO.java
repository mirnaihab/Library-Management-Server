package com.example.library.DTOs.LoginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDTO {
    private String identifier;
    private String password;
}
