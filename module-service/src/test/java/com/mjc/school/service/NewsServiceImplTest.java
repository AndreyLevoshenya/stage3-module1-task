package com.mjc.school.service;

import com.mjc.school.repository.DataRepository;
import com.mjc.school.repository.FileDataRepository;
import com.mjc.school.repository.models.Author;
import com.mjc.school.repository.models.News;
import com.mjc.school.repository.exceptions.NewsNotFoundException;
import com.mjc.school.service.validators.NewsValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;


class NewsServiceImplTest {
    private DataRepository dataRepository;
    private NewsService newsService;
    private NewsDtoMapper newsDtoMapper;


    @BeforeEach
    void setUp() {
        List<News> newsInfo = new ArrayList<>();
        newsInfo.add(new News(1L, "title1", "content1", LocalDateTime.parse("2023-10-12T21:41:39"), LocalDateTime.parse("2023-10-12T21:41:39"), 3L));
        newsInfo.add(new News(2L, "title2", "content2", LocalDateTime.parse("2023-11-12T21:41:39"), LocalDateTime.parse("2023-11-12T21:41:39"), 9L));
        newsInfo.add(new News(3L, "title3", "content3", LocalDateTime.parse("2023-12-12T21:41:39"), LocalDateTime.parse("2023-12-12T21:41:39"), 7L));
        newsInfo.add(new News(4L, "title4", "content4", LocalDateTime.parse("2022-10-12T21:41:39"), LocalDateTime.parse("2022-10-12T21:41:39"), 9L));
        newsInfo.add(new News(5L, "title5", "content5", LocalDateTime.parse("2021-10-12T21:41:39"), LocalDateTime.parse("2021-10-12T21:41:39"), 3L));

        List<Author> authorInfo = new ArrayList<>();
        authorInfo.add(new Author(2L, "author2"));
        authorInfo.add(new Author(3L, "author3"));
        authorInfo.add(new Author(7L, "author7"));
        authorInfo.add(new Author(9L, "author9"));

        newsDtoMapper = new NewsDtoMapper();
        dataRepository = new FileDataRepository(newsInfo, authorInfo);
        newsService = new NewsServiceImpl(newsDtoMapper, new NewsValidator(), dataRepository);
    }

    @Test
    void canGetAllNews() {
        List<NewsDtoResponse> expected = dataRepository.getAllNews().stream().map(newsDtoMapper::convertToDto).collect(Collectors.toList());

        List<NewsDtoResponse> actual = newsService.getAllNews();

        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void getNewsThatExistsById() {
        Long id = 1L;
        NewsDtoResponse expected = newsDtoMapper.convertToDto(
                new News(1L,
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
        assertEquals(6, dataRepository.getAllNews().size());
    }

    @Test
    void createNewsWithInvalidTitle() {
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("tit", "content6", 2L);
        NewsDtoResponse actual = newsService.createNews(newsDtoResponse);

        assertNull(actual);
        assertEquals(5, dataRepository.getAllNews().size());
    }

    @Test
    void createNewsWithInvalidContent() {
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("title6", "cont", 2L);
        NewsDtoResponse actual = newsService.createNews(newsDtoResponse);

        assertNull(actual);
        assertEquals(5, dataRepository.getAllNews().size());
    }

    @Test
    void createNewsWithInvalidAuthorId() {
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("title6", "content6", 4L);
        NewsDtoResponse actual = newsService.createNews(newsDtoResponse);

        assertNull(actual);
        assertEquals(5, dataRepository.getAllNews().size());
    }

    @Test
    void updateValidNews() throws NewsNotFoundException {
        Long id = 4L;
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("newTitle4", "newContent4", 3L);
        NewsDtoResponse actual = newsService.updateNews(id, newsDtoResponse);

        assertNotNull(actual);
        assertEquals(newsDtoMapper.convertToDto(dataRepository.getNewsById(id)).toString(), actual.toString());
    }

    @Test
    void updateNewsWithInvalidId() {
        Long id = 41L;
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("newTitle4", "newContent4", 3L);

        NewsDtoResponse actual = newsService.updateNews(id, newsDtoResponse);

        assertNull(actual);
    }

    @Test
    void updateNewsWithInvalidTitle() throws NewsNotFoundException {
        Long id = 4L;
        NewsDtoResponse newsBeforeUpdate = newsDtoMapper.convertToDto(dataRepository.getNewsById(id));
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("newT", "newContent4", 3L);

        NewsDtoResponse actual = newsService.updateNews(id, newsDtoResponse);
        NewsDtoResponse newsAfterUpdate = newsDtoMapper.convertToDto(dataRepository.getNewsById(id));

        assertNull(actual);
        assertEquals(newsBeforeUpdate.toString(), newsAfterUpdate.toString());
    }

    @Test
    void updateNewsWithInvalidContent() throws NewsNotFoundException {
        Long id = 4L;
        NewsDtoResponse newsBeforeUpdate = newsDtoMapper.convertToDto(dataRepository.getNewsById(id));
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("newTitle4", "newC", 3L);

        NewsDtoResponse actual = newsService.updateNews(id, newsDtoResponse);
        NewsDtoResponse newsAfterUpdate = newsDtoMapper.convertToDto(dataRepository.getNewsById(id));

        assertNull(actual);
        assertEquals(newsBeforeUpdate.toString(), newsAfterUpdate.toString());
    }

    @Test
    void updateNewsWithInvalidAuthorId() throws NewsNotFoundException {
        Long id = 4L;
        NewsDtoResponse newsBeforeUpdate = newsDtoMapper.convertToDto(dataRepository.getNewsById(id));
        NewsDtoResponse newsDtoResponse = new NewsDtoResponse("newTitle4", "newContent4", 12L);

        NewsDtoResponse actual = newsService.updateNews(id, newsDtoResponse);
        NewsDtoResponse newsAfterUpdate = newsDtoMapper.convertToDto(dataRepository.getNewsById(id));

        assertNull(actual);
        assertEquals(newsBeforeUpdate.toString(), newsAfterUpdate.toString());
    }

    @Test
    void deleteNewsWithValidId() {
        Long id = 4L;
        boolean actual = newsService.deleteNews(id);

        assertTrue(actual);
        assertEquals(4, dataRepository.getAllNews().size());
    }

    @Test
    void deleteNewsWithInvalidId() {
        Long id = 7L;
        boolean actual = newsService.deleteNews(id);

        assertFalse(actual);
        assertEquals(5, dataRepository.getAllNews().size());
    }
}