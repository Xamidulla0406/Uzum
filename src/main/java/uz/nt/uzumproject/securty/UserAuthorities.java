package uz.nt.uzumproject.securty;

public enum UserAuthorities {

    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE"),
    CREATE("CREATE");

    UserAuthorities(String name){
        this.name = name;
    }

    private String name;

    public String getName(){
        return this.name;
    }
}
