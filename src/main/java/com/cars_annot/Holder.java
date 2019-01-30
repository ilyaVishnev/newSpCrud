package com.cars_annot;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Entity
@Table(name = "holders")
public class Holder {
    private int id;
    private String login;
    private String password;
    private List<Role> roles = new ArrayList<>();
    private List<Car> cars = new ArrayList<>();

    public Holder() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "login")
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.EAGER)
    @JoinTable(
            name = "holders_roles",
            joinColumns = { @JoinColumn(name = "id_holder") },
            inverseJoinColumns = { @JoinColumn(name = "id_roles") }
    )
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "holder")
    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public String toString() {
        return this.getLogin() + " " + this.getPassword();
    }
}
