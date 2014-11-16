package com.github.jroperto.gaebase.model;

import com.google.appengine.api.datastore.Category;
import com.google.appengine.api.datastore.Text;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Article {

    @Id
    private Long id;
    private String title;
    private Text body;
    private List<Category> tags;
    private Date created;
    private Date updated;


    public Long getId() {
        return id;
    }

    public Article setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body == null ? null : body.getValue();
    }

    public Article setBody(String body) {
        this.body = new Text(body);
        return this;
    }

    public List<Category> getTags() {
        return tags;
    }

    public Article setTags(List<Category> tags) {
        this.tags = tags;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Article setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getUpdated() {
        return updated;
    }

    public Article setUpdated(Date updated) {
        this.updated = updated;
        return this;
    }

    public Article addTag(Category tag) {
        if (tags == null) {
            tags = new ArrayList<>();
        }
        tags.add(tag);

        return this;
    }
}
