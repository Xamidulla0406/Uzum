package uz.nt.uzumproject.model;
import lombok.AllArgsConstructor;
import lombok.Getter;


import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@Setter
@RedisHash(timeToLive = 60*60*2)
@AllArgsConstructor
public class UsersSession {
    @Id
    String uuid;
    String userInfo;

}
