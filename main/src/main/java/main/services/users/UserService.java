package main.services.users;

import main.modules.users.User;
import main.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long id) {
        return (User) userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exsist"));
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        if (userRepository.findById(id).isPresent()) {
            User updateUser = userRepository.findById(id).get();
            /*if(blog.getTitle() != null) updatedBlog.setTitle(blog.getTitle());
            if(blog.getAuthor() != null) updatedBlog.setAuthor(blog.getAuthor());
            if(blog.getPost() != null) updatedBlog.setPost(blog.getPost());*/

            return userRepository.save(updateUser);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "El id especificado no se encuentra en la base de datos");
    }


}
