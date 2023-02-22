package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    public Gson gson(){
        return new Gson();
    }
}
