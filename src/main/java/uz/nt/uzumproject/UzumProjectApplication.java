package uz.nt.uzumproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import uz.nt.uzumproject.repository.UserSessionRepository;

@SpringBootApplication
public class UzumProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(UzumProjectApplication.class, args);
    }

}