package com.example.prehack.service.impl;

import com.example.prehack.exception.ResourceNotFoundException;
import com.example.prehack.model.Role;
import com.example.prehack.repository.RoleRepository;
import com.example.prehack.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleByName(String roleName) {
        log.info("[getRoleByName] >> roleName: {}", roleName);

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> {
                    log.error("Role not found by this name :{} ", roleName);
                    return new ResourceNotFoundException("Role not found by this name :: " + roleName);
                });

        log.info("[getRoleByName] << result: {}", role);
        return role;
    }

    @Override
    public List<Role> getAllRole() {
        log.info("[getAllRole] >> without params");

        List<Role> role = roleRepository.findAll();

        log.info("[getAllRole] << result: {}", role);

        return role;
    }

    @Override
    public Role createRole(String roleName) {
        log.info("[createRole] >> roleName: {}", roleName);

        Role savedRole = roleRepository.save(
                Role.builder()
                        .name(roleName)
                        .build()
        );

        log.info("[createRole] << result: {}", savedRole);

        return savedRole;
    }
}