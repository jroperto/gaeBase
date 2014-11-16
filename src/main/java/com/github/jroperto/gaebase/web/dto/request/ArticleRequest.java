package com.github.jroperto.gaebase.web.dto.request;

import java.util.List;

public class ArticleRequest extends BaseApiRequest {

    private String title;
    private String body;
    private List<String> tags;


    public String getTitle() {
        return title;
    }

    public ArticleRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public ArticleRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public List<String> getTags() {
        return tags;
    }

    public ArticleRequest setTags(List<String> tags) {
        this.tags = tags;
        return this;
    }
}
