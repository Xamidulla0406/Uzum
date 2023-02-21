package uz.nt.uzumproject.security;

public enum UserAuthorities {
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    CREATE("CREATE");
    private String name;
    UserAuthorities(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
}
