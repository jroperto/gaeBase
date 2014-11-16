package com.github.jroperto.gaebase.repository;

import com.github.jroperto.gaebase.model.Article;

public class ArticleRepository extends BaseRepository<Article> {

    public ArticleRepository() {
        super(Article.class);
    }

}
