package com.speedment.plugins.json;

public class UserImpl implements User {

    private int id;
    private String name;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public User setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public User setName(String name) {
        this.name = name;
        return this;
    }
}
