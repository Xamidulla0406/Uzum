package uz.nt.uzumproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class UzumProjectApplication {

    static public void main(String[] args) {
        SpringApplication.run(UzumProjectApplication.class, args);

    }
}