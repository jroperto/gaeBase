package com.github.jroperto.gaebase.repository;

import com.github.jroperto.gaebase.model.Article;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public final class ObjectifyRepository {


    static {
        ObjectifyService.register(Article.class);
    }

    private ObjectifyRepository() { }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}
