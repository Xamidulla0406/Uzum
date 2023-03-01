package uz.nt.uzumproject.secutiry;

public enum UserAuthorities {


    CREATE("CREATE"),
    READ("READ"),
    UPDATE("UPDATE"),
    DELETE("DELETE");

    String name;
     UserAuthorities(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
