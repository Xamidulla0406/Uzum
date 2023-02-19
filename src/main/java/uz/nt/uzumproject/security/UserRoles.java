package uz.nt.uzumproject.security;


import java.util.ArrayList;
import java.util.List;

import static uz.nt.uzumproject.security.UserAuthorities.*;

public enum UserRoles {
    ADMIN(List.of(CREATE,UPDATE,READ)),
    USER(List.of(READ)),
    SUPER_ADMIN(List.of(CREATE,READ,UPDATE,DELETE)),
    MODERATOR(List.of(CREATE,READ));

    List<UserAuthorities> authorities;

    UserRoles(List<UserAuthorities> authorities) {
        this.authorities = authorities;
    }


    public List<String > getAuthorities(){
        List<String > list = new ArrayList<>(
                this.authorities.stream().map(UserAuthorities::getName).toList()
        );
        list.add("ROLE_"+name());

        return list;
    }

}
