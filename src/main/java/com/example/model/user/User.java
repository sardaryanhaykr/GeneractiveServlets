package com.example.model.user;

import java.util.Objects;

public class User {
    private long id;
    private String userName;
    private String password;
    private UserRole role;
    private boolean isAuthorized ;

    public User(long id, String userName, String password, UserRole role, boolean isAuthorized) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.isAuthorized = isAuthorized;
    }

    public User(String userName, String password, UserRole role, boolean isAuthorized){
        this(0,userName,password,role,isAuthorized);
    }

    public User(String userName, String password, UserRole role){
        this(0,userName,password,role,false);
    }

    public User(){
        this(null,null,null);
    }

    public User(User user){
        this.id=user.getId();
        this.userName=user.getUserName();
        this.password=user.getPassword();
        this.role=user.getRole();
        this.isAuthorized=user.isAuthorized();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public boolean isAuthorized() {
        return isAuthorized;
    }

    public void setAuthorized(boolean authorized) {
        isAuthorized = authorized;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && isAuthorized == user.isAuthorized && Objects.equals(userName, user.userName) && Objects.equals(password, user.password) && role == user.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userName, password, role, isAuthorized);
    }
}
