package uz.nt.uzumproject.security;

import org.springframework.security.core.parameters.P;
import uz.nt.uzumproject.model.Authorities;

public enum UserAuthorities {
    /**
     * Start of Objects list
     */
    READ("READ"),
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");
    /**
     * End of objects list
     */

    UserAuthorities(String name){
        this.name = name;
    }

    private String name;

    public String getName(){
        return this.name;
    }
}
