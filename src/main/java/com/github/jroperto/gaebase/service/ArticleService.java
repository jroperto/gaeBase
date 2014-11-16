package com.github.jroperto.gaebase.service;


import com.github.jroperto.gaebase.model.Article;
import com.github.jroperto.gaebase.repository.ArticleRepository;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;


    @SuppressWarnings({"PMD.AvoidCatchingGenericException", "PMD.AvoidCatchingNPE"})
    public void saveArticle(Article article) throws ValidationException {
        try {
            Preconditions.checkNotNull(article, "Article cannot be null");
            Preconditions.checkArgument(StringUtils.isNotBlank(article.getTitle()), "Title cannot be empty");
            Preconditions.checkNotNull(article.getBody(), "Body cannot be null");
            Preconditions.checkArgument(StringUtils.isNotBlank(article.getBody()), "Body cannot be empty");
        } catch (NullPointerException | IllegalArgumentException e) {
            throw new ValidationException("Validation error: " + e.getMessage(), e);
        }

        Date updated = new Date();

        if (article.getId() == null) {
            article.setCreated(updated);
        }
        article.setUpdated(updated);

        articleRepository.save(article);
    }
}
