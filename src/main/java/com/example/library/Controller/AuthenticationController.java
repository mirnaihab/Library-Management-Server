//package com.example.library.Controller;
//
//
//import com.example.medicinebackend.Common.ApplicationRoles;
//import com.example.medicinebackend.Common.Enums;
//import com.example.medicinebackend.DTOs.*;
//import com.example.medicinebackend.DTOs.LoginDTO.LoginDTO;
//import com.example.medicinebackend.DTOs.LoginDTO.LoginResponseDTO;
//import com.example.medicinebackend.DTOs.LoginDTO.UsernameDTO;
//import com.example.medicinebackend.DTOs.OAuthDTO.GoogleOAuthLoginRequestDTO;
//import com.example.medicinebackend.DTOs.OAuthDTO.GoogleOAuthRegisterRequestDTO;
//import com.example.medicinebackend.DTOs.ResetPasswordDTO.ResetPasswordRequestDTO;
//import com.example.medicinebackend.DTOs.ResetPasswordDTO.ResetPasswordResponseDTO;
//import com.example.medicinebackend.DTOs.RolesDTO.RolesDTO;
//import com.example.medicinebackend.Mapper.RolesMapper;
//import com.example.medicinebackend.Service.AuthenticationService;
//import jakarta.mail.MessagingException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.io.UnsupportedEncodingException;
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/Authentication")
//@CrossOrigin(origins = "*")
//@Validated
//public class AuthenticationController {
//
//    private final AuthenticationService authenticationService;
//
//    public AuthenticationController(AuthenticationService authenticationService) {
//        this.authenticationService = authenticationService;
//    }
//
//    @PostMapping("/RegisterUser")
//    public ResponseEntity<LoginResponseDTO> registerUser(@Valid @RequestBody RegisterDTO registerDTO) throws MessagingException, UnsupportedEncodingException {
//        LoginResponseDTO response = authenticationService.registerUser(registerDTO, initializeUserRoles());
//        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    @PostMapping("/GoogleOAuthRegisterUser")
//    public ResponseEntity<ResponseDTO> GoogleOAuthRegisterUser(@Valid @RequestBody GoogleOAuthRegisterRequestDTO googleOAuthRegisterRequestDTO)  {
//        LoginResponseDTO response = authenticationService.GoogleOAuthRegisterUser(googleOAuthRegisterRequestDTO, initializeUserRoles());
//        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//
//    @PostMapping("/RegisterAdmin")
//    public ResponseEntity<LoginResponseDTO> registerAdmin(@Valid @RequestBody RegisterDTO registerDTO) throws MessagingException, UnsupportedEncodingException {
//        LoginResponseDTO response = authenticationService.registerAdmin(registerDTO, initializeAdminRoles());
//        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    @PostMapping("/Login")
//    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginDTO loginDTO) {
//        LoginResponseDTO loginResponseDTO = authenticationService.IdentifyUserOrAdminByUsername(loginDTO);
//        if(loginResponseDTO.getStatusResponse()== Enums.StatusResponse.Success){
//            return ResponseEntity.ok(loginResponseDTO);
//        }else{
//            return ResponseEntity.badRequest().body(loginResponseDTO);
//        }
//    }
//
//    @PostMapping("/GenerateVerificationCode")
//    public ResponseEntity<EmailVerificationCodeResponseDTO> generateCode(@Valid @RequestBody RegisterDTO registerDTO) {
//        EmailVerificationCodeResponseDTO code = authenticationService.sendEmailAndGenerateCode(registerDTO);
//        return ResponseEntity.ok(code);
//    }
//
//    @PostMapping("/ResendVerificationCode")
//    public ResponseEntity<ResendVerificationCodeResponseDTO> resendVerificationCode(@Valid @RequestParam("email") String email) throws MessagingException, UnsupportedEncodingException {
//        ResendVerificationCodeResponseDTO response = authenticationService.resendVerificationEmailAsync(email);
//        return ResponseEntity.ok(response);
//    }
//
//    @GetMapping("/GenerateResetPasswordCode")
//    public ResponseEntity<ResetPasswordResponseDTO> generateForgotPasswordCode(@Valid @RequestParam("email") String email) {
//        ResetPasswordResponseDTO response = authenticationService.sendPasswordResetToken(email);
//        if(response.getStatusResponse() == Enums.StatusResponse.Success){
//            return ResponseEntity.ok(response);
//        }else{
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
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
//    @PostMapping("GoogleOAuthLoginRequest")
//    public ResponseEntity<LoginResponseDTO> GoogleOAuthLoginRequest(@Valid @RequestBody GoogleOAuthLoginRequestDTO googleOAuthLoginRequestDTO) {
//        LoginResponseDTO response = authenticationService.GoogleOAuthLoginRequest(googleOAuthLoginRequestDTO);
//        if(response.getStatusResponse() == Enums.StatusResponse.Success){
//            return ResponseEntity.ok(response);
//        }else{
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    @PutMapping("/ResetPassword")
//    public ResponseEntity<ResponseDTO> resetPassword(@Valid @RequestBody ResetPasswordRequestDTO resetPasswordRequestDTO) {
//        ResponseDTO response = authenticationService.updatePassword(resetPasswordRequestDTO);
//        return getResponseEntity(response);
//    }
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
//
//    private static ResponseEntity<ResponseDTO> getResponseEntity(ResponseDTO response) {
//        if(response.getStatusResponse()== Enums.StatusResponse.Success){
//            return ResponseEntity.ok(response);
//        }else{
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    private static List<RolesDTO> initializeUserRoles(){
//        List<String> roleNames = Arrays.asList(ApplicationRoles.View_Teaser, ApplicationRoles.Purchase_Course, ApplicationRoles.View_Course_Content);
//        return RolesMapper.mapRoleNamesToDTO(roleNames);
//    }
//
//    private static List<RolesDTO> initializeAdminRoles(){
//        List<String> roleNames = Arrays.asList(ApplicationRoles.Add_Course, ApplicationRoles.Edit_Course);
//        return RolesMapper.mapRoleNamesToDTO(roleNames);
//    }
//
//}
