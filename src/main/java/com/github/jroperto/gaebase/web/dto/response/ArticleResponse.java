package com.github.jroperto.gaebase.web.dto.response;


import com.github.jroperto.gaebase.model.Article;

public class ArticleResponse extends BaseApiResponse {

    private Article article;

    public Article getArticle() {
        return article;
    }

    public ArticleResponse setArticle(Article article) {
        this.article = article;
        return this;
    }
}
