package com.github.jroperto.gaebase.repository;

import com.googlecode.objectify.Key;

import static com.googlecode.objectify.ObjectifyService.ofy;

public class BaseRepository<T> {

    private static final int INVALID_ID = 0;
    private final Class clazz;

    public BaseRepository(Class clazz) {
        this.clazz = clazz;
    }


    @SuppressWarnings({"unchecked", })
    public T get(long id) {
        if (id <= INVALID_ID) {
            return null;
        }
        return (T) ofy().load().key(Key.create(clazz, id)).now();
    }

    public T get(Key<T> key) {
        if (key == null) {
            return null;
        }
        return ofy().load().key(key).now();
    }

    public Key<T> save(T object) {
        return ofy().save().entity(object).now();
    }

    @SuppressWarnings({"unchecked"})
    public void delete(long id) {
        ofy().delete().key(Key.create(clazz, id));
    }
}
