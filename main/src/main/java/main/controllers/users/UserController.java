package main.controllers.users;

import main.modules.users.ThirdParty;
import main.repositories.users.UserRepository;
import main.services.users.ThirdPartyService;
import main.services.users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    ThirdPartyService thirdPartyService;

    @PostMapping("/third-party/add")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdPartyUser(@RequestBody ThirdParty thirdParty) {
        return thirdPartyService.addThirdParty(thirdParty);
    }

}
