package com.example.library.Controller;

import com.example.library.Common.Enums;
import com.example.library.DTOs.PatronDTO.AllPatronsResponseDTO;
import com.example.library.DTOs.PatronDTO.PatronDTO;
import com.example.library.DTOs.PatronDTO.PatronResponseDTO;
import com.example.library.DTOs.ResponseDTO;
import com.example.library.Service.PatronService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patrons")
@CrossOrigin(origins = "*")
@Validated
public class PatronController {
    private final PatronService patronService;

    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }


    @GetMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.library.Common.ApplicationRoles).View_Patrons)")
    public ResponseEntity<PatronResponseDTO> getPatronById(@PathVariable Long id)   {
        PatronResponseDTO response = patronService.getPatronById(id);
        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.library.Common.ApplicationRoles).Edit_Patrons)")
    public ResponseEntity<ResponseDTO> editPatronById(@PathVariable Long id, @RequestBody @Validated PatronDTO patronDTO)   {
        ResponseDTO response = patronService.editPatronById(id, patronDTO );
        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("")
    @PreAuthorize("hasRole(T(com.example.library.Common.ApplicationRoles).View_Patrons)")
    public ResponseEntity<AllPatronsResponseDTO> getPatrons()   {
        AllPatronsResponseDTO response = patronService.getAllPatrons();
        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.library.Common.ApplicationRoles).Edit_Patrons)")
    public ResponseEntity<ResponseDTO> deletePatron(@PathVariable Long id)   {
        ResponseDTO response = patronService.deletePatron(id);
        if (response.getStatusResponse() == Enums.StatusResponse.Success) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
