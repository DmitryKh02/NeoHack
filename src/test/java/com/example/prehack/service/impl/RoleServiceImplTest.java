package com.example.prehack.service.impl;

import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.model.Role;
import com.example.prehack.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @InjectMocks
    RoleServiceImpl roleService;

    @Mock
    RoleRepository roleRepository;

    List<Role> roles;

    @BeforeEach
    void setUp() {
        Role role1 = new Role(1L, "ROLE_USER");
        Role role2 = new Role(2L, "ROLE_ADMIN");
        roles = List.of(role1, role2);
    }

    @Test
    void getRoleByName_withExistingName() {
        String name = "ROLE_USER";
        Role role = new Role(1L, name);

        when(roleRepository.findByName(name)).thenReturn(Optional.of(role));

        assertEquals(role, roleService.getRoleByName(name));
    }

    @Test
    void getRoleByName_withNotExistingName() {
        String name = "DESTROYER";

        when(roleRepository.findByName(name)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> roleService.getRoleByName(name));
    }


    @Test
    void getAllRole() {
        Role role1 = new Role(1L, "ROLE_USER");
        Role role2 = new Role(2L, "ROLE_ADMIN");
        Role role666 = new Role(3L, "DESTROYER");

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> allRoles = roleRepository.findAll();
        assertTrue(allRoles.contains(role1));
        assertTrue(allRoles.contains(role2));
        assertFalse(allRoles.contains(role666));
    }

    @Test
    void createRole() {
        Role role1 = new Role(1L, "ROLE_USER");

        when(roleRepository.save(role1)).thenReturn(role1);

        assertEquals("ROLE_USER", roleRepository.save(role1).getName());
    }
}