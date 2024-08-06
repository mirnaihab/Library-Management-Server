package com.example.library.Service;


import com.example.library.Common.Enums;
import com.example.library.DTOs.ResponseDTO;
import com.example.library.DTOs.RolesDTO.RolesDTO;
import com.example.library.DTOs.RolesDTO.RolesResponseDTO;
import com.example.library.Models.Roles;
import com.example.library.Repository.RolesRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class RolesService {
    private final RolesRepository rolesRepository;

    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }


    public ResponseDTO saveListOfRoles(List<RolesDTO> rolesDTO) {
        List<String> savedRoles = new ArrayList<>();
        if(rolesDTO.isEmpty()){
            return new ResponseDTO(Enums.StatusResponse.Failed, "Roles are empty");
        }else {
            for (RolesDTO r : rolesDTO) {

                if (!rolesRepository.existsByName(r.getName())) {
                    Roles newRole = new Roles();
                    newRole.setName(r.getName());
                    rolesRepository.save(newRole);
                    savedRoles.add(r.getName());
                }
            }
            if (savedRoles.size() == rolesDTO.size()) {
                return new ResponseDTO(Enums.StatusResponse.Success, "Roles Saved Successfully");
            } else {
                int notSavedCount = rolesDTO.size() - savedRoles.size();
                return new ResponseDTO(Enums.StatusResponse.Success, notSavedCount + " role(s) are already saved");
            }
        }
    }

    public List<Roles> getExistingRoles(List<RolesDTO> roleDTOs) {

        List<String> roleNames = roleDTOs.stream()
                .map(RolesDTO::getName)
                .collect(Collectors.toList());

        return rolesRepository.findAllByNameIn(roleNames);
    }

    public RolesResponseDTO getAllRoles(){
        List<Roles> roles = rolesRepository.findAll();
        if(!roles.isEmpty())
        {
            return new RolesResponseDTO(roles, Enums.StatusResponse.Success,"Retrieved List of Roles Successfully");
        }
        else return new RolesResponseDTO(null, Enums.StatusResponse.Failed,"Failed To Retrieve List of Roles");
    }


    /*public List<Roles> convertFromStringListToRoleMap(List<String> roles) {
        List<Roles> rolesList = new ArrayList<>();
        for (String roleName : roles) {
            boolean roleExistsResp = rolesRepository.existsByName(roleName);
            if (!roleExistsResp) {
                Roles role = new Roles();
                rolesList.add(role);
            }
        }
        return rolesList;
    }*/
}
