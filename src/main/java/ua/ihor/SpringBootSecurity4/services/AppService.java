package ua.ihor.SpringBootSecurity4.services;

import com.github.javafaker.Faker;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.ihor.SpringBootSecurity4.models.Application;
import ua.ihor.SpringBootSecurity4.models.MyUser;
import ua.ihor.SpringBootSecurity4.repositories.UsersRepository;

import java.util.List;
import java.util.stream.IntStream;


@Service
@AllArgsConstructor
public class AppService {
    private List<Application> applications;
    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;


    @PostConstruct
    public void loadApplications() {
        Faker faker = new Faker();
        applications = IntStream.range(0, 10)
                .mapToObj(i->Application.builder()
                        .id(i)
                        .name(faker.app().name())
                        .author(faker.app().author())
                        .version(faker.app().version())
                        .build())
                .toList();

    }

    public List<Application> getApplications() {
        return applications;
    }

    public Application getApplicationById(int id) {
        return applications.stream().filter(app -> app.getId() == id).findFirst().orElse(null);
    }

    public void addUser(MyUser myUser){
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        usersRepository.save(myUser);
    }
}
