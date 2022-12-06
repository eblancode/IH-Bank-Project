package main.controllers;

import main.modules.users.Admin;
import main.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/third-party")
public class ThirdPartyController {
    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findAllAccountHolders() {
        return adminRepository.findAll();
    }

}
