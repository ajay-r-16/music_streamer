package com.example.music.user;

import com.example.music.user.verfication.Verification;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class Users implements Serializable {

    private static final long serialVersionUID = -2460659701384032012L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERNAME", length = 100)
    private String username;

    @Column(name = "EMAIL", length = 100, unique = true)
    private String email;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "is_active")
    private boolean active;

    @Column(name = "role")
    private Long role;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Verification> verification;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<Verification> getVerifyAccounts() {
        return verification;
    }

    public void setVerifyAccounts(Set<Verification> verifyAccounts) {
        this.verification = verifyAccounts;
    }

    public boolean isActive() { return active; }

    public void setActive(boolean active) {
        this.active = active;
    }

}