package com.mjc.school.repository.impl;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.models.AuthorModel;
import com.mjc.school.repository.models.NewsModel;
import com.mjc.school.repository.exceptions.NewsNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FileDataSource implements DataSource {
    private NewsDataReader newsDataSource;
    private AuthorDataReader authorDataSource;
    private final List<NewsModel> newsData;
    private final List<AuthorModel> authorsData;

    public static final String NEWS_WITH_ID = "News with id ";
    public static final String DOES_NOT_EXIST = " does not exist";

    public FileDataSource(String newsFilename, String authorFilename) {
        newsDataSource = new NewsDataReader(newsFilename);
        authorDataSource = new AuthorDataReader(authorFilename);
        newsData = newsDataSource.getInfo();
        authorsData = authorDataSource.getInfo();
    }

    public FileDataSource(List<NewsModel> newsData, List<AuthorModel> authorsData) {
        this.newsData = newsData;
        this.authorsData = authorsData;
    }

    @Override
    public List<NewsModel> readAllNews(){
        return newsData;
    }

    @Override
    public List<AuthorModel> readAllAuthors() {
        return authorsData;
    }

    @Override
    public NewsModel readById(Long id) throws NewsNotFoundException {
        Optional<NewsModel> value = newsData.stream().filter(news -> news.getId().equals(id)).findAny();
        if (value.isEmpty()) {
            throw new NewsNotFoundException(NEWS_WITH_ID + id + DOES_NOT_EXIST);
        }
        return value.get();
    }

    @Override
    public NewsModel createNews(NewsModel news) {
        newsData.add(news);
        return news;
    }

    @Override
    public NewsModel updateNews(NewsModel newNews) throws NewsNotFoundException {
        int index = Collections.binarySearch(newsData, new NewsModel(newNews.getId(), "", "", null, null, null));
        if(index >= 0) {
            newsData.set(index, newNews);
            return newNews;
        }
        else {
            throw new NewsNotFoundException(NEWS_WITH_ID + newNews.getId() + DOES_NOT_EXIST);
        }
    }

    @Override
    public Boolean deleteNewsById(Long id) throws NewsNotFoundException {
        int index = Collections.binarySearch(newsData, new NewsModel(id, "", "", null, null, null));
        if(index >= 0) {
            newsData.remove(index);
            return true;
        }
        else {
            throw new NewsNotFoundException(NEWS_WITH_ID + id + DOES_NOT_EXIST);
        }
    }

}
