package by.kotik.userservice.controller;

import by.kotik.userservice.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("/init")
    public ResponseEntity<Void> initRoles(){
        roleService.initRoles();
        return ResponseEntity.noContent().build();
    }
}
