package ua.ihor.SpringBootSecurity4.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.ihor.SpringBootSecurity4.models.MyUser;
import ua.ihor.SpringBootSecurity4.repositories.UsersRepository;
import ua.ihor.SpringBootSecurity4.usersDetails.MyUserDetails;

import java.util.Optional;


@Service
public class MyUsersDetailsService implements UserDetailsService {
    @Autowired
    private UsersRepository usersRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MyUser> user = usersRepository.findByName(username);
        return user.map(MyUserDetails::new).orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
