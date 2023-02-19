package uz.nt.uzumproject.security;

public enum UserAuthorities {

    CREATE("CREATE"),
    READ("READ"),
    DELETE("DELETE"),
    UPDATE("UPDATE");

    private String name;

    UserAuthorities(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
