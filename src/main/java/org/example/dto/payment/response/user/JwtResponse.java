package org.example.dto.payment.response.user;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.model.Role;

public class JwtResponse {
    private String token;
    private String type = "Bearer";

    private Long id;

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String role;

    public JwtResponse(String token, Long id, String username, String firstname, String lastname, String email, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role.toString();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }




    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {return role;}

    public void setRole(Role role) {this.role = role.toString();}

}
