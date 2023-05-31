package database;

public class User {
    private String name;
    private String surname;
    private int id;

    public User(String name, String surname, int id){
        setName(name);
        setSurname(surname);
        setId(id);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getId() {
        return id;
    }
}
