package main.controllers.users;

import main.modules.users.User;
import main.repositories.users.UserRepository;
import main.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    /*@Autowired
    UserRepository userRepository;*/
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable Long id) {
        return userService.findUser(id);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id,user);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestBody User user) {
        /*if(userService.findUser(user.getId()).isPresent()) {
        }*/
        System.err.println();
        if(userRepository.findById(user.getId()).isPresent()) {
            return userRepository.save(user);
        }
        return null;
    }

    @PatchMapping("/user-name/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateStatus(@PathVariable Long id, @RequestParam String name){
        if(userRepository.findById(id).isPresent()){
            User user = userRepository.findById(id).get();
            user.setUserName(name);
            return userRepository.save(user);
        }
        return null;
    }

}

/*{
        "id": 1,
            "name": "Eduard",
            "birthDate": "1994-07-01",
            "address": {
        "street": "C/ calle, 123",
                "postalCode": "08000",
                "city": "Barcelona",
                "country": "ES"
    },
        "mailingAddress": null
    }*/
