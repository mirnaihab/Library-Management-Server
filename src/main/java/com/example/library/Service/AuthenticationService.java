package com.example.library.Service;


import com.example.library.Common.Enums;
import com.example.library.DTOs.LoginDTO.LoginDTO;
import com.example.library.DTOs.LoginDTO.LoginResponseDTO;
import com.example.library.DTOs.RegisterDTO;
import com.example.library.DTOs.ResponseDTO;
import com.example.library.DTOs.RolesDTO.RolesDTO;
import com.example.library.Mapper.UserMapper;
import com.example.library.Models.Patron;
import com.example.library.Repository.PatronRepository;
import com.example.library.Security.JWTGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    private final PatronRepository patronRepository;
    private final JWTGenerator jwtGenerator;
    private final AuthenticationManager authenticationManager;
    private final RolesService rolesService;


    public AuthenticationService(PatronRepository patronRepository, JWTGenerator jwtGenerator, AuthenticationManager authenticationManager, RolesService rolesService) {
        this.patronRepository = patronRepository;
        this.jwtGenerator = jwtGenerator;
        this.authenticationManager = authenticationManager;
        this.rolesService = rolesService;
    }

    public LoginResponseDTO registerUser(RegisterDTO registerDTO, List<RolesDTO> roles) {
        Patron user = UserMapper.mapToUsers(registerDTO);
        user.setRoles(rolesService.getExistingRoles(roles));
        try {
            // Save user
            patronRepository.save(user);

            return loginUser(new LoginDTO(user.getUsername(), registerDTO.getPassword()));
        } catch (Exception e) {
            // Handle unique constraint violation
            return new LoginResponseDTO(null, null , Enums.StatusResponse.Failed, e.getMessage());
         }
    }


    public LoginResponseDTO loginUser(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getIdentifier(),
                            loginDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtGenerator.generateToken(authentication);
            Patron user = patronRepository.findByUsernameOrEmail(loginDTO.getIdentifier(),loginDTO.getIdentifier());
            if(user != null && token != null)
            {
                return new LoginResponseDTO(token, user.getId(),Enums.StatusResponse.Success, "Login Successful");
            }
            return new LoginResponseDTO(null, null ,Enums.StatusResponse.Failed, "Incorrect Username or Password");
        }
        catch (Exception e) {
            return new LoginResponseDTO(null, null ,Enums.StatusResponse.Failed, "Incorrect Username or Password");
        }
    }

    public ResponseDTO CheckOnUsernameAndEmail(String email, String username) {
        Patron userByUsername = patronRepository.findByUsername( username);
        if(userByUsername!=null){
            return new ResponseDTO(Enums.StatusResponse.Failed,"Username already exists");
        }

        Patron userByEmail = patronRepository.findByEmail(email);
        if(userByEmail!=null){
            return new ResponseDTO(Enums.StatusResponse.Failed,"Email already exists");
        }
        return new ResponseDTO(Enums.StatusResponse.Success,"Email and Username are valid");


    }

    public ResponseDTO CheckOnUsername(String username) {
        Patron userByUsername = patronRepository.findByUsername( username);
        if(userByUsername!=null){
            return new ResponseDTO(Enums.StatusResponse.Failed,"Username already exists");
        }

        return new ResponseDTO(Enums.StatusResponse.Success,"Email and Username are valid");
    }
}
