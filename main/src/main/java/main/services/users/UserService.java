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

    /*
    // UNUSED METHODS HAVE BEEN COMMENTED
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUserById(Long id) {
        return (User) userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            User updatedUser = userRepository.findById(id).get();
            if(user.getUserName() != null) updatedUser.setUserName(user.getUserName());
            if(user.getPassword() != null) updatedUser.setPassword(user.getPassword());
            return userRepository.save(updatedUser);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "El id especificado no se encuentra en la base de datos");
    }*/

}
