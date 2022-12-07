package main.controllers.users;

import main.modules.users.Admin;
import main.repositories.users.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findAllAdmins() {
        return adminRepository.findAll();
    }

}
