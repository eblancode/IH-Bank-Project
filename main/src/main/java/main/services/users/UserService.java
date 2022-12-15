package main.services.users;

import main.modules.users.User;
import main.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User findUserByUserName(String userName) {
        return (User) userRepository.findByUserName(userName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
    }

}
