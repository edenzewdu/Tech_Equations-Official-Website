package com.example.tech_equations.model;

import java.io.Serializable;

public class Feature implements Serializable {
    private String title;
    private String description;

    public Feature(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
