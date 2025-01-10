package com.thuve.slkustuportal;

public class DBHelper {
    String name,email,password,sid,faculty;

    public DBHelper() {
    }

    public DBHelper(String password, String name, String email, String sid,String faculty) {
        this.password = password;
        this.name = name;
        this.email = email;
        this.sid = sid;
        this.faculty=faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSid() {
        return sid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
