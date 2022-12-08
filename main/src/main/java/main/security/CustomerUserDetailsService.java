package main.security;

import main.repositories.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    /*@Override
    public UserDetails loadUserById(Long id) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findById(id);
        return null;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Optional<User> user = userRepository.findByUserName(username);
        return null;
    }

}
