package uz.nt.uzumproject.security;

public enum UserAuthorities {

    READ("READ"),
    CREATE("CREATE"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    UserAuthorities(String name) {
        this.name = name;
    }

    private String name;

    public String getName() {
        return this.name;
    }

}