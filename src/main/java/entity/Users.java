package entity;

import enums.UserType;

public class Users {
    private int id;
    private UserType type;
    private String name;
    private String email;
    private String password;
    private String phone;

    public Users() {
    }

    public Users(int id, UserType type, String name, String email, String password, String phone) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
    }

    public Users(String email, String password, String type) {
    }

    public Users(int id, String password, String email) {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
