package uz.nt.uzumproject.security;

import com.google.gson.Gson;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.UserSession;
import uz.nt.uzumproject.repository.UserSessionRepository;

import java.util.Date;
import java.util.UUID;

@Component
public class JwtService {

    @Value("${spring.security.secret.key}")
    private String secretKey;

    @Autowired
    private Gson gson;

    @Autowired
    private UserSessionRepository userSessionRepository;

    public String generateToken(UsersDto user){
        String uuid = UUID.randomUUID().toString();
        userSessionRepository.save(new UserSession(uuid, gson.toJson(user, UsersDto.class)));

        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .setSubject(uuid)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isExpired(String token){
        return getClaims(token).getExpiration().getTime() < System.currentTimeMillis();
    }

    public UsersDto getSubject(String token){
        String uuid = getClaims(token).getSubject();

        return userSessionRepository.findById(uuid)
                .map(s -> gson.fromJson(s.getUser(), UsersDto.class))
                .orElseThrow(() -> new JwtException("Token is expired"));
    }
}