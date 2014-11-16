package com.github.jroperto.gaebase.web.controller.api.article;

import com.github.jroperto.gaebase.model.Article;
import com.github.jroperto.gaebase.service.ArticleService;
import com.github.jroperto.gaebase.service.ValidationException;
import com.github.jroperto.gaebase.web.controller.api.ApiControllerBase;
import com.github.jroperto.gaebase.web.dto.request.ArticleRequest;
import com.github.jroperto.gaebase.web.dto.response.ArticleResponse;
import com.google.appengine.api.datastore.Category;
import com.google.common.annotations.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
@RequestMapping(ApiControllerBase.BASE_API_PATH + "/article")
public class ArticleController extends ApiControllerBase {

    @Autowired
    private ArticleService articleService;


    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ArticleResponse createArticle(@RequestBody ArticleRequest articleRequest) throws ValidationException {

        Article article = buildArticle(articleRequest);

        articleService.saveArticle(article);

        return new ArticleResponse().setArticle(article);
    }

    @VisibleForTesting
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    protected Article buildArticle(ArticleRequest articleRequest) {
        Article article = new Article()
                .setTitle(articleRequest.getTitle())
                .setBody(articleRequest.getBody());

        for (String tag: articleRequest.getTags() ) {
            article.addTag(new Category(tag));
        }

        return article;
    }

    /**
     * Handle validation exception exceptions
     *
     * @param throwable Exception thrown
     * @return An error status with the error message
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> handleValidationExceptions(Throwable throwable) {
        return new ResponseEntity<>(throwable.getMessage(), HttpStatus.EXPECTATION_FAILED);
    }

}
