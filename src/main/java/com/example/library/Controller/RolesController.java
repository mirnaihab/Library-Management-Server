//package com.example.library.Controller;
//
//import com.example.medicinebackend.Common.Enums;
//import com.example.medicinebackend.DTOs.ResponseDTO;
//import com.example.medicinebackend.DTOs.RolesDTO.RolesDTO;
//import com.example.medicinebackend.DTOs.RolesDTO.RolesResponseDTO;
//import com.example.medicinebackend.Service.RolesService;
//import jakarta.mail.MessagingException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//
//import javax.validation.Valid;
//import java.io.UnsupportedEncodingException;
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/Roles")
//@CrossOrigin(origins = "*")
//@Validated
//public class RolesController {
//
//    private final RolesService rolesService;
//
//    public RolesController(RolesService rolesService){
//        this.rolesService = rolesService;
//    }
//
//    @PostMapping("/SaveRoles")
//    public ResponseEntity<ResponseDTO> saveRoles(@Valid @RequestBody List<RolesDTO> roles) throws MessagingException, UnsupportedEncodingException {
//        ResponseDTO response = rolesService.saveListOfRoles(roles);
//        if(response.getStatusResponse()== Enums.StatusResponse.Success){
//            return ResponseEntity.ok(response);
//        }else{
//            return ResponseEntity.badRequest().body(response);
//        }
//    }
//
//    @GetMapping()
//    public ResponseEntity<RolesResponseDTO> getRolesList(){
//        RolesResponseDTO rolesResponseDTO = rolesService.getAllRoles();
//        if(rolesResponseDTO.getStatusResponse()== Enums.StatusResponse.Success){
//            return ResponseEntity.ok(rolesResponseDTO);
//        }else{
//            return ResponseEntity.badRequest().body(rolesResponseDTO);
//        }
//    }
//}
