package org.mindswap.controller.UserControllers;


import jakarta.validation.Valid;
import org.mindswap.dto.UserDto;
import org.mindswap.dto.UserUpdateDto;
import org.mindswap.model.Role;
import org.mindswap.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.mindswap.security.config.JwtAuthenticationFilter.getAuthenticatedUserId;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    private ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @GetMapping(path = "")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public ResponseEntity<String> welcomeManager() {
        return new ResponseEntity<>("Welcome to Blockbuster, dear manager.", HttpStatus.OK);
    }

    @GetMapping(path = "/info")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<UserDto> myInfo() {
        Long authenticatedManagerId = Long.valueOf(getAuthenticatedUserId());
        UserDto myInfoDto = managerService.getInfoById(authenticatedManagerId);

        //TODO: VERIFY THIS METHOD
        return new ResponseEntity<>(myInfoDto, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getManagerInfo(@PathVariable("id") Long managerId) {
        UserDto manager = managerService.getInfoById(managerId);

        //TODO: VERIFY THIS METHOD
        return new ResponseEntity<>(manager, HttpStatus.OK);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<String> updateManagerInfo(@PathVariable("id") Long managerId, @Valid @RequestBody UserUpdateDto userUpdateDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Long authenticatedManagerId = Long.valueOf(getAuthenticatedUserId());

        //If A client tries to access other clients:
        if (role.equals(Role.MANAGER) && !authenticatedManagerId.equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        managerService.updateManager(managerId, userUpdateDto);

        //TODO: VERIFY THIS METHOD
        return new ResponseEntity<>("Updated successfully.", HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteManager(@PathVariable("id") Long managerId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String role = auth.getAuthorities().iterator().next().getAuthority();
        Long authenticatedManagerId = Long.valueOf(getAuthenticatedUserId());

        //If A manager tries to access other clients:
        if (role.equals(Role.MANAGER) && !authenticatedManagerId.equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        managerService.deleteManager(managerId);

        //TODO: VERIFY THIS METHOD
        return new ResponseEntity<>("Deleted successfully.", HttpStatus.OK);
    }

    @GetMapping(path = "/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllManagers() {
        List<UserDto> managersList = managerService.getAllManagers();

        //TODO: VERIFY THIS METHOD
        return new ResponseEntity<>(managersList, HttpStatus.OK);
    }
}