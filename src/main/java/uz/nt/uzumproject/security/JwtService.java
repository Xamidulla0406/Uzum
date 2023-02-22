package uz.nt.uzumproject.security;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.nt.uzumproject.dto.UsersDto;
import java.util.Date;

@Component
public class JwtService {

    @Value("$spring.security.secret.key")
    private String secretKey;
    @Autowired
    private Gson gson;

    public String generateToken(UsersDto usersDto){
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*2))
                .setSubject(gson.toJson(usersDto))
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
        String subject = claims(token).getSubject();
        UsersDto usersDto = gson.fromJson(subject, UsersDto.class);
        return usersDto;
    }
}
