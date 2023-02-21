package uz.nt.uzumproject.secutiry;

import com.google.gson.Gson;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.UsersDto;

import java.util.Date;

@Component
public class JwtService {

    @Value("{spring.security.secret.key}")
    String key;

    Gson gson;
    public String jwts(UsersDto usersDto){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 2 * 60 + 10))
                .setSubject(new Gson().toJson(usersDto))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();
    }

}
