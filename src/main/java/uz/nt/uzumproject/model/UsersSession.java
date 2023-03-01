package uz.nt.uzumproject.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(timeToLive = 60*60*2)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersSession {
    @Id
    private String uuid;
    private String user;
}

