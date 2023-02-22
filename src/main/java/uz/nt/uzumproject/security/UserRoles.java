package uz.nt.uzumproject.security;

import java.util.ArrayList;
import java.util.List;

import static uz.nt.uzumproject.security.UserAuthorities.*;

public enum UserRoles {
    ADMIN(List.of(READ, UPDATE, CREATE)),
    USER(List.of(READ)),
    MODERATOR(List.of(CREATE, READ)),
    SUPER_ADMIN(List.of(READ, UPDATE, CREATE, DELETE));

//    private String name;
    public final List<UserAuthorities> authorities;
    UserRoles(List<UserAuthorities> userAuthorities){
        this.authorities = userAuthorities;
    }

    public UserRoles getRole(String roleName){
        return valueOf(roleName);
    }

    public List<String> getAuthorities(){
        List<String> auth = new ArrayList<>(this.authorities.stream()
                .map(UserAuthorities::getName)
                .toList());
        auth.add("ROLE_" + this.name());
        return auth;
    }
}
