package com.mjc.school.repository.impl;

import com.mjc.school.repository.DataRepository;
import com.mjc.school.repository.models.Author;
import com.mjc.school.repository.models.News;
import com.mjc.school.repository.exceptions.NewsNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class FileDataRepository implements DataRepository {
    private final List<News> newsData;
    private final List<Author> authorsData;

    public FileDataRepository(String newsFilename, String authorFilename) {
        newsData = new NewsDataSource(newsFilename).getInfo();
        authorsData = new AuthorDataSource(authorFilename).getInfo();
    }

    public FileDataRepository(List<News> newsData, List<Author> authorsData) {
        this.newsData = newsData;
        this.authorsData = authorsData;
    }

    @Override
    public List<News> readAllNews(){
        return newsData;
    }

    @Override
    public List<Author> readAllAuthors() {
        return authorsData;
    }

    @Override
    public News readById(Long id) throws NewsNotFoundException {
        Optional<News> value = newsData.stream().filter(news -> news.getId().equals(id)).findAny();
        if (value.isEmpty()) {
            throw new NewsNotFoundException("News with id " + id + " does not exist");
        }
        return value.get();
    }

    @Override
    public News createNews(News news) {
        newsData.add(news);
        return news;
    }

    @Override
    public News updateNews(News newNews) throws NewsNotFoundException {
        int index = Collections.binarySearch(newsData, new News(newNews.getId(), "", "", null, null, null));
        if(index >= 0) {
            newsData.set(index, newNews);
            return newNews;
        }
        else {
            throw new NewsNotFoundException("News with id " + newNews.getId() + " does not exist");
        }
    }

    @Override
    public boolean deleteNewsById(Long id) throws NewsNotFoundException {
        int index = Collections.binarySearch(newsData, new News(id, "", "", null, null, null));
        if(index >= 0) {
            newsData.remove(index);
            return true;
        }
        else {
            throw new NewsNotFoundException("News with id " + id + " does not exist");
        }
    }

}
