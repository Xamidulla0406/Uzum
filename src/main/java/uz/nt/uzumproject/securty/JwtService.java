package uz.nt.uzumproject.securty;

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
    @Value("${secret.key}")
    private String secretKey;

    @Autowired
    private Gson gson;

    public String generateToken(UsersDto user) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis()))
                .setSubject(gson.toJson(user))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
