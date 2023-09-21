package com.mjc.school.repository;

import com.mjc.school.repository.models.AuthorModel;
import com.mjc.school.repository.models.NewsModel;
import com.mjc.school.repository.exceptions.NewsNotFoundException;

import java.util.List;

public interface DataRepository {
    List<NewsModel> readAllNews();

    List<AuthorModel> readAllAuthors();

    NewsModel readById(Long id) throws NewsNotFoundException;

    NewsModel createNews(NewsModel news);

    NewsModel updateNews(NewsModel news) throws NewsNotFoundException;

    Boolean deleteNewsById(Long id) throws NewsNotFoundException;
}
