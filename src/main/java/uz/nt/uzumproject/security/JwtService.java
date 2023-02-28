package uz.nt.uzumproject.security;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.UsersDto;

import java.time.LocalDateTime;
import java.util.Date;

@Component
public class JwtService {

    @Value("${spring.security.secret.key}")
    private String secretKey;

    @Autowired
    private Gson gson;

    public String generateToken(UsersDto user){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .setSubject(gson.toJson(user))
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
        String subject = getClaims(token).getSubject();
        return gson.fromJson(subject, UsersDto.class);
    }
}