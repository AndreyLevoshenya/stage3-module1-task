package com.mjc.school.service;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.impl.FileDataSource;
import com.mjc.school.repository.models.AuthorModel;
import com.mjc.school.repository.models.NewsModel;
import com.mjc.school.repository.exceptions.NewsNotFoundException;
import com.mjc.school.service.impl.NewsServiceImpl;
import com.mjc.school.service.validators.NewsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class NewsServiceImplTest {
    private DataSource dataSource;
    private NewsService newsService;
    private NewsDtoMapper newsDtoMapper;


    @BeforeEach
    void setUp() {
        List<NewsModel> newsInfo = new ArrayList<>();
        newsInfo.add(new NewsModel(1L, "title1", "content1", LocalDateTime.parse("2023-10-12T21:41:39"), LocalDateTime.parse("2023-10-12T21:41:39"), 3L));
        newsInfo.add(new NewsModel(2L, "title2", "content2", LocalDateTime.parse("2023-11-12T21:41:39"), LocalDateTime.parse("2023-11-12T21:41:39"), 9L));
        newsInfo.add(new NewsModel(3L, "title3", "content3", LocalDateTime.parse("2023-12-12T21:41:39"), LocalDateTime.parse("2023-12-12T21:41:39"), 7L));
        newsInfo.add(new NewsModel(4L, "title4", "content4", LocalDateTime.parse("2022-10-12T21:41:39"), LocalDateTime.parse("2022-10-12T21:41:39"), 9L));
        newsInfo.add(new NewsModel(5L, "title5", "content5", LocalDateTime.parse("2021-10-12T21:41:39"), LocalDateTime.parse("2021-10-12T21:41:39"), 3L));

        List<AuthorModel> authorInfo = new ArrayList<>();
        authorInfo.add(new AuthorModel(2L, "author2"));
        authorInfo.add(new AuthorModel(3L, "author3"));
        authorInfo.add(new AuthorModel(7L, "author7"));
        authorInfo.add(new AuthorModel(9L, "author9"));

        newsDtoMapper = new NewsDtoMapper();
        dataSource = new FileDataSource(newsInfo, authorInfo);
        newsService = new NewsServiceImpl(newsDtoMapper, new NewsValidator(), dataSource);
    }

    @Test
    void canGetAllNews() {
        List<NewsDtoResponse> expected = dataSource.readAllNews().stream().map(newsDtoMapper::convertToDto).collect(Collectors.toList());

        List<NewsDtoResponse> actual = newsService.getAllNews();

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void getNewsThatExistsById() {
        Long id = 1L;
        NewsDtoResponse expected = newsDtoMapper.convertToDto(
                new NewsModel(1L,
                        "title1",
                        "content1",
                        LocalDateTime.parse("2023-10-12T21:41:39"),
                        LocalDateTime.parse("2023-10-12T21:41:39"),
                        3L));

        NewsDtoResponse actual = newsService.getNewsById(id);

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void getNewsThatDoesNotExistsById() {
        Long id = 10L;
        //?????????????????????
        NewsDtoResponse actual = newsService.getNewsById(id);

        assertNull(actual);
    }

    @Test
    void createValidNews() {
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("title6", "content6", 2L);
        NewsDtoResponse actual = newsService.createNews(newsDtoResponse);

        assertNotNull(actual);
        assertEquals(6, dataSource.readAllNews().size());
    }

    @ParameterizedTest
    @CsvSource(value = {"titl, content6, 2", "title6, cont, 2", "title6, content6, 4"})
    void createInvalidNews(String title, String content, Long authorId) {
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse(title, content, authorId);
        NewsDtoResponse actual = newsService.createNews(newsDtoResponse);

        assertNull(actual);
        assertEquals(5, dataSource.readAllNews().size());
    }

    @Test
    void updateValidNews() throws NewsNotFoundException {
        Long id = 4L;
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("newTitle4", "newContent4", 3L);
        NewsDtoResponse actual = newsService.updateNews(id, newsDtoResponse);

        assertNotNull(actual);
        assertEquals(newsDtoMapper.convertToDto(dataSource.readById(id)).toString(), actual.toString());
    }

    @Test
    void updateNewsWithInvalidId() {
        Long id = 41L;
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("newTitle4", "newContent4", 3L);

        NewsDtoResponse actual = newsService.updateNews(id, newsDtoResponse);

        assertNull(actual);
    }

    @ParameterizedTest
    @CsvSource( value = {"newT, newContent4, 3", "newTitle4, newC, 3", "newTitle4, newContent4, 12"})
    void updateInvalidNews(String newTitle, String newContent, Long newAuthorId) throws NewsNotFoundException {
        Long id = 4L;
        NewsDtoResponse newsBeforeUpdate = newsDtoMapper.convertToDto(dataSource.readById(id));
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse(newTitle, newContent, newAuthorId);

        NewsDtoResponse actual = newsService.updateNews(id, newsDtoResponse);
        NewsDtoResponse newsAfterUpdate = newsDtoMapper.convertToDto(dataSource.readById(id));

        assertNull(actual);
        assertEquals(newsBeforeUpdate.toString(), newsAfterUpdate.toString());
    }

    @Test
    void deleteNewsWithValidId() {
        Long id = 4L;
        boolean actual = newsService.deleteNews(id);

        assertTrue(actual);
        assertEquals(4, dataSource.readAllNews().size());
    }

    @Test
    void deleteNewsWithInvalidId() {
        Long id = 7L;
        boolean actual = newsService.deleteNews(id);

        assertFalse(actual);
        assertEquals(5, dataSource.readAllNews().size());
    }
}