package com.github.jroperto.gaebase.model;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class EntityMock {

    @Id
    private Long id;
    private String property;


    public Long getId() {
        return id;
    }

    public EntityMock setId(Long id) {
        this.id = id;
        return this;
    }

    public String getProperty() {
        return property;
    }

    public EntityMock setProperty(String property) {
        this.property = property;
        return this;
    }
}
