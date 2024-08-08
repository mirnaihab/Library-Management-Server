package com.example.library.AuthenticationTests;

import com.example.library.Common.ApplicationRoles;
import com.example.library.Common.Enums;
import com.example.library.Controller.AuthenticationController;
import com.example.library.DTOs.LoginDTO.LoginDTO;
import com.example.library.DTOs.LoginDTO.LoginResponseDTO;
import com.example.library.DTOs.RegisterDTO;
import com.example.library.Security.CustomUserDetailsService;
import com.example.library.Security.JWTAuthenticationFilter;
import com.example.library.Security.JWTGenerator;
import com.example.library.Service.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.springframework.security.test.context.support.WithMockUser;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(AuthenticationController.class)
public class AuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private static JWTGenerator jwtGenerator;

    @MockBean
    private static CustomUserDetailsService customUserDetailsService;


    @Test
    @WithMockUser
    public void registerUser_ShouldReturnSuccessResponse() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("testUser", "test@example.com", "test","password123", "+123456789", "Country", "Address");
        LoginResponseDTO responseDTO = new LoginResponseDTO("token", 1L, Enums.StatusResponse.Success, "User registered successfully");

        when(authenticationService.registerUser(any(RegisterDTO.class), anyList())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/Authentication/RegisterUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusResponse").value(Enums.StatusResponse.Success.toString()))
                .andExpect(jsonPath("$.accessToken").value("token"))
                .andExpect(jsonPath("$.message").value("User registered successfully"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    public void registerUser_ShouldReturnBadRequest() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO("testUser", "test@example.com", "test", "", "+123456789", "Country", "Address");
        LoginResponseDTO responseDTO = new LoginResponseDTO(null, null, Enums.StatusResponse.Failed, "User registration failed");

        when(authenticationService.registerUser(any(RegisterDTO.class), anyList())).thenReturn(responseDTO);

        mockMvc.perform(post("/api/Authentication/RegisterUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(registerDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusResponse").value(Enums.StatusResponse.Failed.toString()))
                .andExpect(jsonPath("$.message").value("User registration failed"));
    }

    @Test
    @WithMockUser
    public void login_ShouldReturnSuccessResponse() throws Exception {
        LoginDTO loginDTO = new LoginDTO("username", "password123");
        LoginResponseDTO responseDTO = new LoginResponseDTO("token", 1L, Enums.StatusResponse.Success, "Login Successful");

        when(authenticationService.loginUser(any(LoginDTO.class))).thenReturn(responseDTO);

        mockMvc.perform(post("/api/Authentication/Login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusResponse").value("Success"))
                .andExpect(jsonPath("$.accessToken").value("token"))
                .andExpect(jsonPath("$.message").value("Login Successful"))
                .andExpect(jsonPath("$.id").value(1L));
    }

    @TestConfiguration
    @EnableWebSecurity
    static class TestSecurityConfig {
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http.csrf(AbstractHttpConfigurer::disable)
                    .exceptionHandling(Customizer.withDefaults())
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authRequests -> {
                        authRequests.requestMatchers("/api/Authentication/**").permitAll();
                        authRequests.requestMatchers(HttpMethod.GET, "/api/books/**").hasAuthority(ApplicationRoles.View_Books)
                                .requestMatchers(HttpMethod.POST, "/api/books/**").hasAuthority(ApplicationRoles.Edit_Books)
                                .requestMatchers(HttpMethod.PUT, "/api/books/**").hasAuthority(ApplicationRoles.Edit_Books)
                                .requestMatchers(HttpMethod.DELETE, "/api/books/**").hasAuthority(ApplicationRoles.Edit_Books);
                        authRequests.requestMatchers("/api/patrons/**").hasAnyAuthority(ApplicationRoles.Edit_Patrons,ApplicationRoles.View_Patrons);
                        authRequests.requestMatchers("/api/borrow/**").hasAuthority(ApplicationRoles.Borrow_Book);
                        authRequests.requestMatchers("/api/return/**").hasAuthority(ApplicationRoles.Return_Book);
                        authRequests.requestMatchers("/**").permitAll();
                    })
                    .httpBasic(AbstractHttpConfigurer::disable);
            http.addFilterBefore(new JWTAuthenticationFilter(jwtGenerator, customUserDetailsService), UsernamePasswordAuthenticationFilter.class);
            return http.build();
        }

    }

}
