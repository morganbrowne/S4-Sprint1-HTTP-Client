package com.keyin.domain;

public class Airport {
    private String id;
    private String name;
    private String location;


    public Airport() {}
    public Airport(String id, String name, String location) {
        this.id = id;
        this.name = name;
        this.location = location;
    }


    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
}