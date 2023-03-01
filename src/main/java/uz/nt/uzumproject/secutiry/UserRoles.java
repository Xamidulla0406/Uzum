package uz.nt.uzumproject.secutiry;

import java.util.ArrayList;
import java.util.List;


public enum UserRoles {

    ADMIN(List.of(UserAuthorities.CREATE,UserAuthorities.UPDATE,UserAuthorities.READ)),
    USER(List.of(UserAuthorities.READ)),
    MODERATOR(List.of(UserAuthorities.CREATE,UserAuthorities.READ)),
    SUPER_ADMIN(List.of(UserAuthorities.CREATE,UserAuthorities.READ,UserAuthorities.UPDATE,UserAuthorities.DELETE));


     UserRoles(List<UserAuthorities> userAuthoritiesList){
        this.userAuthoritiesList = userAuthoritiesList;
    }

    private List<UserAuthorities> userAuthoritiesList;


     public List<String> getUserAuthoritiesList(){
         List<String> list = new ArrayList<>(
                 this.userAuthoritiesList.stream()
                         .map(UserAuthorities::getName).toList());
         list.add("ROLE_" + this.name());
         return list;
     }

}
