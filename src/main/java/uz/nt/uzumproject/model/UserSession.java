package uz.nt.uzumproject.model;


import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

@AllArgsConstructor
@Getter
@Setter
@RedisHash(timeToLive = 1000*2*2)
public class UserSession {
    @Id
    String key;

    String value;
}
