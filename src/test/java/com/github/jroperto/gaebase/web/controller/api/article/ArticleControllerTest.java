package com.github.jroperto.gaebase.web.controller.api.article;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jroperto.gaebase.model.Article;
import com.github.jroperto.gaebase.service.ArticleService;
import com.github.jroperto.gaebase.service.ValidationException;
import com.github.jroperto.gaebase.web.dto.request.ArticleRequest;
import com.github.jroperto.gaebase.web.dto.response.ArticleResponse;
import com.google.appengine.api.datastore.Category;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/test-applicationContext.xml")
public class ArticleControllerTest {

    private static final String         API_VERSION = "1";
    private static final UUID           API_KEY = UUID.fromString("f3512d26-72f6-4290-9265-63ad69eccc13");
    private static final String         TITLE = "This is an article title";
    private static final String         BODY = "<h4>Article short title</h4> This is an article body to test if repository works";
    private static final Category       NEWS = new Category("NEWS");
    private static final Category       ALERT = new Category("ALERT");
    private static final List<String>   TAGS = Arrays.asList(NEWS.getCategory(), ALERT.getCategory());

    private MockMvc mockMvc;

    @Mock
    private ArticleService articleServiceMock;
    @InjectMocks
    private ArticleController articleController;

    @Value("testData/article/articleController-create-invalidRequest-noTitle.json")
    private File createArticleInvalidRequestNoTitle;
    @Value("testData/article/articleController-create-invalidRequest-noBody.json")
    private File createArticleInvalidRequestNoBody;
    @Value("testData/article/articleController-create-validRequest.json")
    private File createArticleValidRequest;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = standaloneSetup(articleController).setMessageConverters(new MappingJackson2HttpMessageConverter()).build();
    }

    @Test
    public void thatBuildArticleBuildsCorrectly() {
        ArticleRequest articleRequest = new ArticleRequest()
                .setTitle(TITLE)
                .setBody(BODY)
                .setTags(TAGS);

        articleRequest.setRequestedAt(new Date());

        Article article = articleController.buildArticle(articleRequest);

        assertEquals("Titles should match", articleRequest.getTitle(), article.getTitle());
        assertEquals("Bodies should match", articleRequest.getBody(), article.getBody());
        assertEquals("Tags should match", articleRequest.getTags().get(0), article.getTags().get(0).getCategory());
        assertEquals("Tags should match", articleRequest.getTags().get(1), article.getTags().get(1).getCategory());
    }

    @Test
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException"})
    public void thatCreateArticleHandlesValidationExceptionWhenEmptyTitle() throws Exception {

        String errorMessage = "Title cannot be empty";
        ValidationException validationException = new ValidationException(errorMessage, new IllegalArgumentException());

        doThrow(validationException).when(articleServiceMock).saveArticle(any(Article.class));

        final MvcResult mvcResult = this.mockMvc.perform(
                post("/api/{version}/article/", API_VERSION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, API_KEY.toString())
                        .content(FileUtils.readFileToString(createArticleInvalidRequestNoTitle))
        )
                .andExpect(status().isExpectationFailed()).andReturn();

        assertEquals("Status code is not the expected", HttpStatus.EXPECTATION_FAILED.value(), mvcResult.getResponse().getStatus());
        assertEquals("Error message is not the expected", "\"" + errorMessage + "\"", mvcResult.getResponse().getContentAsString());

        verify(articleServiceMock).saveArticle(any(Article.class));
        verifyNoMoreInteractions(articleServiceMock);
    }

    @Test
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException", "PMD.AvoidThrowingNullPointerException"})
    public void thatCreateArticleHandlesValidationExceptionWhenBodyIsEmpty() throws Exception {

        String errorMessage = "Body cannot be empty";
        ValidationException validationException = new ValidationException(errorMessage, new NullPointerException());

        doThrow(validationException).when(articleServiceMock).saveArticle(any(Article.class));

        final MvcResult mvcResult = this.mockMvc.perform(
                post("/api/{version}/article/", API_VERSION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, API_KEY.toString())
                        .content(FileUtils.readFileToString(createArticleInvalidRequestNoBody))
        )
                .andExpect(status().isExpectationFailed()).andReturn();

        assertEquals("Status code is not the expected", HttpStatus.EXPECTATION_FAILED.value(), mvcResult.getResponse().getStatus());
        assertEquals("Error message is not the expected", "\"" + errorMessage + "\"", mvcResult.getResponse().getContentAsString());

        verify(articleServiceMock).saveArticle(any(Article.class));
        verifyNoMoreInteractions(articleServiceMock);
    }

    @Test
    @SuppressWarnings({"PMD.SignatureDeclareThrowsException"})
    public void thatCreateArticleReturnsValidData() throws Exception {

        doNothing().when(articleServiceMock).saveArticle(any(Article.class));

        final MvcResult mvcResult = this.mockMvc.perform(
                post("/api/{version}/article/", API_VERSION)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, API_KEY.toString())
                        .content(FileUtils.readFileToString(createArticleValidRequest))
        )
                .andExpect(status().isCreated()).andReturn();

        ArticleResponse actualArticleResponse = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), ArticleResponse.class);

        assertEquals("Status code is not the expected", HttpStatus.CREATED.value(), mvcResult.getResponse().getStatus());
        assertTrue("Responded at is not correct", System.currentTimeMillis() > actualArticleResponse.getRespondedAt().getTime());
        assertEquals("Title is not the expected", TITLE, actualArticleResponse.getArticle().getTitle());
        assertEquals("Body is not the expected", BODY, actualArticleResponse.getArticle().getBody());

        verify(articleServiceMock).saveArticle(any(Article.class));
        verifyNoMoreInteractions(articleServiceMock);
    }
}