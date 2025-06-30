package by.kotik.userservice.service;

import by.kotik.userservice.entity.Role;
import by.kotik.userservice.repository.RoleRepository;
import exception.GenericNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new GenericNotFoundException("Role not found"));
    }

    public void initRoles() {
        roleRepository.save(Role.builder().name("ADMIN").build());
        roleRepository.save(Role.builder().name("USER").build());
    }
}
