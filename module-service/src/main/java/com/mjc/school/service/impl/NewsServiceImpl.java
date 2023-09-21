package com.mjc.school.service.impl;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.models.NewsModel;
import com.mjc.school.repository.exceptions.NewsNotFoundException;
import com.mjc.school.service.IdGenerator;
import com.mjc.school.service.NewsDtoMapper;
import com.mjc.school.service.NewsDtoResponse;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.exceptions.AuthorNotFoundException;
import com.mjc.school.service.exceptions.ContentLengthIsWrongException;
import com.mjc.school.service.exceptions.TitleLengthIsWrongException;
import com.mjc.school.service.validators.NewsValidator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class NewsServiceImpl implements NewsService {
    private final NewsDtoMapper newsDtoMapper;
    private final NewsValidator newsValidator;
    private final DataSource dataSource;

    public NewsServiceImpl(NewsDtoMapper newsDtoMapper, NewsValidator newsValidator, DataSource dataSource) {
        this.newsDtoMapper = newsDtoMapper;
        this.newsValidator = newsValidator;
        this.dataSource = dataSource;
    }

    @Override
    public List<NewsDtoResponse> getAllNews() {
        return dataSource.readAllNews().stream().map(newsDtoMapper::convertToDto).toList();
    }

    @Override
    public NewsDtoResponse getNewsById(Long id) {
        try {
            return newsDtoMapper.convertToDto(dataSource.readById(id));
        } catch (NewsNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public NewsDtoResponse createNews(NewsDtoResponse newsDtoResponse) {
        IdGenerator idGenerator = new IdGenerator(dataSource);
        Long id = idGenerator.getNewsId();
        LocalDateTime date = LocalDateTime.parse(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        try {
            newsValidator.validate(newsDtoResponse, dataSource);
            return newsDtoMapper.convertToDto(dataSource.createNews(new NewsModel(id, newsDtoResponse.getTitle(), newsDtoResponse.getContent(), date, date, newsDtoResponse.getAuthorId())));
        } catch (TitleLengthIsWrongException | ContentLengthIsWrongException | AuthorNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public NewsDtoResponse updateNews(Long id, NewsDtoResponse newsDtoResponse) {
        try {
            LocalDateTime createdDate = dataSource.readById(id).getCreatedDate();
            LocalDateTime updatedDate = LocalDateTime.parse(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            newsValidator.validate(newsDtoResponse, dataSource);
            return newsDtoMapper.convertToDto(dataSource.updateNews(new NewsModel(id, newsDtoResponse.getTitle(), newsDtoResponse.getContent(), createdDate, updatedDate, newsDtoResponse.getAuthorId())));
        } catch (TitleLengthIsWrongException | ContentLengthIsWrongException | AuthorNotFoundException | NewsNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteNews(Long id){
        try {
            return dataSource.deleteNewsById(id);
        } catch (NewsNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
