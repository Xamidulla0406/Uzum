package uz.nt.uzumproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories
public class UzumProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(UzumProjectApplication.class, args);
    }

}