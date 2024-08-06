package com.example.library.Controller;



import com.example.library.Common.ApplicationRoles;
import com.example.library.Common.Enums;
import com.example.library.DTOs.LoginDTO.LoginDTO;
import com.example.library.DTOs.LoginDTO.LoginResponseDTO;
import com.example.library.DTOs.RegisterDTO;
import com.example.library.DTOs.ResponseDTO;
import com.example.library.DTOs.RolesDTO.RolesDTO;
import com.example.library.Mapper.RolesMapper;
import com.example.library.Service.AuthenticationService;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
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

    @PostMapping("/Login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(loginDTO);
        if(loginResponseDTO.getStatusResponse()== Enums.StatusResponse.Success){
            return ResponseEntity.ok(loginResponseDTO);
        }else{
            return ResponseEntity.badRequest().body(loginResponseDTO);
        }
    }



//    @GetMapping("/GetUsername")
//    public ResponseEntity<UsernameDTO> GetUsername(@Valid @RequestParam("email") String email) {
//        UsernameDTO response = authenticationService.GetUsername(email);
//        if(response.getStatusResponse() == Enums.StatusResponse.Success){
//            return ResponseEntity.ok(response);
//        }else{
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//
//    @GetMapping("/CheckOnUsernameAndEmail")
//    public ResponseEntity<ResponseDTO> CheckOnUsernameAndEmail(@Valid @RequestParam("email") String email, @RequestParam("username") String username) {
//        ResponseDTO response = authenticationService.CheckOnUsernameAndEmail(email, username);
//        if(response.getStatusResponse() == Enums.StatusResponse.Success){
//            return ResponseEntity.ok(response);
//        }else{
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    @GetMapping("/CheckOnUsername")
//    public ResponseEntity<ResponseDTO> CheckOnUsername(@RequestParam("username") String username) {
//        ResponseDTO response = authenticationService.CheckOnUsername(username);
//        if(response.getStatusResponse() == Enums.StatusResponse.Success){
//            return ResponseEntity.ok(response);
//        }else{
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

    private static ResponseEntity<ResponseDTO> getResponseEntity(ResponseDTO response) {
        if(response.getStatusResponse()== Enums.StatusResponse.Success){
            return ResponseEntity.ok(response);
        }else{
            return ResponseEntity.badRequest().body(response);
        }
    }

    private static List<RolesDTO> initializeUserRoles(){
        List<String> roleNames = Arrays.asList(ApplicationRoles.Borrow_Book, ApplicationRoles.Return_Book, ApplicationRoles.View_Books);
        return RolesMapper.mapRoleNamesToDTO(roleNames);
    }

}
