package com.github.jroperto.gaebase.repository;

import com.github.jroperto.gaebase.model.User;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public final class ObjectifyRepository {

    static {
        ObjectifyService.register(User.class);
    }

    private ObjectifyRepository() { }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}
