package com.sri.event.Entity;

import java.util.Collection;

import jakarta.persistence.*;

@Entity
@Table(name = "Owners")
public class OwnerEntity {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, length = 100)
    private String ownerId;

    @Column(nullable = false, length = 100)
    private String fname;

    @Column(nullable = false, length = 100)
    private String lname;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String encrypassword;

    @ManyToMany(cascade = { CascadeType.PERSIST }, fetch = FetchType.EAGER)
    @JoinTable(name = "owners_roles",
            joinColumns = @JoinColumn(name = "owners_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id", referencedColumnName = "id"))
    private Collection<RoleEntity> roles;

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEncrypassword() {
        return encrypassword;
    }

    public void setEncrypassword(String encrypassword) {
        this.encrypassword = encrypassword;
    }

    public Collection<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Collection<RoleEntity> roles) {
        this.roles = roles;
    }
}
