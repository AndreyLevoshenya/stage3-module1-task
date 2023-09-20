package com.mjc.school.repository;

import com.mjc.school.repository.models.Author;
import com.mjc.school.repository.models.News;
import com.mjc.school.repository.exceptions.NewsNotFoundException;

import java.util.List;

public interface DataRepository {
    List<News> getAllNews();

    List<Author> getAllAuthors();

    News getNewsById(Long id) throws NewsNotFoundException;

    News createNews(News news);

    News updateNews(News news) throws NewsNotFoundException;

    boolean deleteNewsById(Long id) throws NewsNotFoundException;
}
