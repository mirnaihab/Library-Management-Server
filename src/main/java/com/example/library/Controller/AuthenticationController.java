package com.example.library.Controller;



import com.example.library.Common.ApplicationRoles;
import com.example.library.Common.Enums;
import com.example.library.DTOs.LoginDTO.LoginDTO;
import com.example.library.DTOs.LoginDTO.LoginResponseDTO;
import com.example.library.DTOs.RegisterDTO;
import com.example.library.DTOs.RolesDTO.RolesDTO;
import com.example.library.Mapper.RolesMapper;
import com.example.library.Service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/Authentication")
@CrossOrigin(origins = "*")
@Validated
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/RegisterUser")
    public ResponseEntity<LoginResponseDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO)   {
        LoginResponseDTO response = authenticationService.registerUser(registerDTO, initializeUserRoles());
        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/RegisterAdmin")
    public ResponseEntity<LoginResponseDTO> registerAdmin(@Valid @RequestBody RegisterDTO registerDTO) {
        LoginResponseDTO response = authenticationService.registerAdmin(registerDTO, initializeAdminRoles());
        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/Login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(loginDTO);
        if(loginResponseDTO.getStatusResponse()== Enums.StatusResponse.Success){
            return ResponseEntity.ok(loginResponseDTO);
        }else{
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
    }


    private static List<RolesDTO> initializeUserRoles(){
        List<String> roleNames = Arrays.asList(ApplicationRoles.Borrow_Book, ApplicationRoles.Return_Book, ApplicationRoles.View_Books);
        return RolesMapper.mapRoleNamesToDTO(roleNames);
    }

    private static List<RolesDTO> initializeAdminRoles(){
        List<String> roleNames = Arrays.asList(ApplicationRoles.View_Patrons, ApplicationRoles.View_Books, ApplicationRoles.Return_Book, ApplicationRoles.Borrow_Book, ApplicationRoles.Edit_Books, ApplicationRoles.Edit_Patrons);
        return RolesMapper.mapRoleNamesToDTO(roleNames);
    }

}
