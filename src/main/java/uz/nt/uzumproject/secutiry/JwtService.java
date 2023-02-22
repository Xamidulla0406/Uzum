package uz.nt.uzumproject.secutiry;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.UserSession;
import uz.nt.uzumproject.repository.UserSessionRepository;
import uz.nt.uzumproject.rest.ExceptionHandlerResource;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtService {

    @Value("${spring.security.secret.key}")
    private String key;

    public final Gson gson;

    @Autowired
    UserSessionRepository userSessionRepository;

    public String generationKey(UsersDto usersDto) {
        String id = UUID.randomUUID().toString();
        userSessionRepository.save(new UserSession(id,gson.toJson(usersDto)));
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis()  + 100000))
                .setSubject(id)
                .signWith(SignatureAlgorithm.HS256,key)
                .compact();
    }



    private Claims claims(String token){
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean isExpired(String token){

        return claims(token).getExpiration().getTime() > System.currentTimeMillis();
    }

    public UsersDto subject(String token){
        String uuid = claims(token).getSubject();
        return userSessionRepository.findById(uuid).map(userSession -> gson.fromJson(userSession.getKey(), UsersDto.class))
                .orElseThrow(() -> new JwtException("Token is expired"));
    }

}
