package uz.nt.uzumproject.security;
import com.google.gson.Gson;
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
}
