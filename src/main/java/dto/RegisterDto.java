package dto;

import enums.UserType;

public class RegisterDto {
    private String email;
    private String password;
    private String name;
    private UserType type;
    private String phone;

    public RegisterDto(String email, String password, String name, UserType type, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.type = type;
        this.phone = phone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
