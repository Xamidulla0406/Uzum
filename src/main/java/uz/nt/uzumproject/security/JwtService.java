package uz.nt.uzumproject.security;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.UsersDto;
import uz.nt.uzumproject.model.UsersSession;
import uz.nt.uzumproject.repository.UserSessionRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Component
public class JwtService {

    @Value("$spring.security.secret.key")
    private String secretKey;
    @Autowired
    private Gson gson;

    @Autowired
    UserSessionRepository userSessionRepository;
    public String generateToken(UsersDto usersDto){
        String uuid = UUID.randomUUID().toString();
        userSessionRepository.save(new UsersSession(uuid, gson.toJson(usersDto)));
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*2))
                .setSubject(uuid)
                .signWith(SignatureAlgorithm.HS256,secretKey)
                .compact();
    }
    public Claims claims(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean isExpire(String token){
        return claims(token).getExpiration().getTime()<System.currentTimeMillis();
    }
    public UsersDto subject(String token){
        String uuid = claims(token).getSubject();
        return userSessionRepository.findById(uuid).map(u->gson.fromJson(u.getUserInfo(),UsersDto.class))
                .orElseThrow(()-> new JwtException(""));
    }
}
