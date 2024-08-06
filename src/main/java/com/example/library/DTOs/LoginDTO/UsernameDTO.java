package com.example.library.DTOs.LoginDTO;

import com.example.library.Common.Enums;
import com.example.library.DTOs.ResponseDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UsernameDTO extends ResponseDTO {

    private String username;

    public UsernameDTO(String username,Enums.StatusResponse status, String Message) {
        super(status, Message);
        this.username=username;

    }
}
