package com.speedment.plugins.json;

public final class LanguageImpl implements Language {

    private int id;
    private String name;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Language setId(int id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Language setName(String name) {
        this.name = name;
        return this;
    }

}