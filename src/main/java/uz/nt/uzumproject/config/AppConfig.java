package uz.nt.uzumproject.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.time.LocalDateTime;

@Configuration
@EnableRedisRepositories
public class AppConfig {

    @Autowired
    private LocalDateTimeSerializer localDateTimeSerializer;
    @Autowired
    private LocalDateTimeDeserializer localDateTimeDeserializer;

    @Bean
    public Gson gson(){
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(LocalDateTime.class, localDateTimeSerializer);
        builder.registerTypeAdapter(LocalDateTime.class, localDateTimeDeserializer);

        return builder.create();
    }
}
