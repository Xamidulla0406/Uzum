package uz.nt.uzumproject.security;

import java.util.ArrayList;
import java.util.List;

import static uz.nt.uzumproject.security.UserAuthorities.*;

public enum UserRoles {
    ADMIN(List.of(READ,UPDATE,CREATE)),
    USER(List.of(READ)),
    MODERATOR(List.of(READ,CREATE)),
    SUPER_ADMIN(List.of(CREATE,READ,UPDATE,DELETE));
    List<UserAuthorities> authorities;
    UserRoles (List<UserAuthorities> authorities){
        this.authorities=authorities;
    }
    public List<String> getAuthorities(){
        List<String> list = new ArrayList<>(this.authorities.stream()
                .map(UserAuthorities::getName)
                .toList());
        list.add("ROLE_"+this.name());
        return list;
    }
}
