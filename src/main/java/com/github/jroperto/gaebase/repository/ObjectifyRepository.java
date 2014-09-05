package com.github.jroperto.gaebase.repository;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

import javax.annotation.PostConstruct;
import java.util.List;

public class ObjectifyRepository {

    private final List<Class> entities;


    public ObjectifyRepository(List<Class> entities) {
        this.entities = entities;
    }

    @PostConstruct
    public void registerEntities() {
        for (Class clazz : entities) {
            ObjectifyService.register(clazz);
        }
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

}
