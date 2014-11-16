package com.github.jroperto.gaebase.service;

import com.github.jroperto.gaebase.model.Article;
import com.github.jroperto.gaebase.repository.ArticleRepository;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.Key;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ArticleServiceTest {

    private static final long       ID = 1L;
    private static final String     TITLE = "Article Title";
    private static final String     BODY = "<h4>Article short title</h4> This is an article body to test if repository works";

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig());
    @Mock
    private ArticleRepository articleRepositoryMock;
    @InjectMocks
    private ArticleService articleService;


    @Before
    public void setUp() {
        helper.setUp();
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void thatSaveArticleValidatesArticle() {

        try {
            articleService.saveArticle(null);
            fail("Exception expected");
        } catch (ValidationException e) {
            assertEquals("Wrong exception thrown", NullPointerException.class, e.getCause().getClass());
        }

        try {
            articleService.saveArticle(new Article());
            fail("Exception expected");
        } catch (ValidationException e) {
            assertEquals("Wrong exception thrown", IllegalArgumentException.class, e.getCause().getClass());
        }

        try {
            articleService.saveArticle(new Article().setTitle("A title"));
            fail("Exception expected");
        } catch (ValidationException e) {
            assertEquals("Wrong exception thrown", NullPointerException.class, e.getCause().getClass());
        }

        try {
            articleService.saveArticle(new Article().setTitle("A title").setBody(""));
            fail("Exception expected");
        } catch (ValidationException e) {
            assertEquals("Wrong exception thrown", IllegalArgumentException.class, e.getCause().getClass());
        }

        try {
            articleService.saveArticle(new Article().setTitle("A title").setBody(" "));
            fail("Exception expected");
        } catch (ValidationException e) {
            assertEquals("Wrong exception thrown", IllegalArgumentException.class, e.getCause().getClass());
        }

        try {
            articleService.saveArticle(new Article().setBody("A body"));
            fail("Exception expected");
        } catch (ValidationException e) {
            assertEquals("Wrong exception thrown", IllegalArgumentException.class, e.getCause().getClass());
        }


        Article article = new Article().setTitle("A title").setBody("A body");
        when(articleRepositoryMock.save(article)).thenReturn(Key.create(Article.class, ID));

        try {
            articleService.saveArticle(article);
        } catch (ValidationException e) {
            fail("Exception not expected");
        }

    }

    @Test
    public void thatSaveArticleCreatesAnArticle() throws ValidationException {
        Article article = new Article()
                .setTitle(TITLE)
                .setBody(BODY);

        when(articleRepositoryMock.save(article)).thenReturn(Key.create(Article.class, ID));

        articleService.saveArticle(article);

        assertNotNull("Create date should be set", article.getCreated());
        assertNotNull("Update date should be set", article.getUpdated());
        assertEquals("Create and update date should be equals", article.getCreated(), article.getUpdated());

        verify(articleRepositoryMock).save(article);
        verifyNoMoreInteractions(articleRepositoryMock);

    }

    @Test
    public void thatSaveArticleUpdatesAnArticle() throws ValidationException {
        Article article = new Article()
                .setId(ID)
                .setCreated(new Date(System.currentTimeMillis() - 2000L))
                .setTitle(TITLE)
                .setBody(BODY);

        when(articleRepositoryMock.save(article)).thenReturn(Key.create(Article.class, ID));

        articleService.saveArticle(article);

        assertNotNull("Create date should be set", article.getCreated());
        assertNotNull("Update date should be set", article.getUpdated());
        assertTrue("Update date should be bigger than created", article.getUpdated().compareTo(article.getCreated()) > 0);

        verify(articleRepositoryMock).save(article);
        verifyNoMoreInteractions(articleRepositoryMock);

    }

}