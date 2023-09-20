package com.mjc.school.service;

import com.mjc.school.repository.DataRepository;
import com.mjc.school.repository.models.News;
import com.mjc.school.repository.exceptions.NewsNotFoundException;
import com.mjc.school.service.exceptions.AuthorNotFoundException;
import com.mjc.school.service.exceptions.ContentLengthIsWrongException;
import com.mjc.school.service.exceptions.TitleLengthIsWrongException;
import com.mjc.school.service.validators.NewsValidator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class NewsServiceImpl implements NewsService {
    private final NewsDtoMapper newsDtoMapper;
    private final NewsValidator newsValidator;
    private final DataRepository dataRepository;

    public NewsServiceImpl(NewsDtoMapper newsDtoMapper, NewsValidator newsValidator, DataRepository dataRepository) {
        this.newsDtoMapper = newsDtoMapper;
        this.newsValidator = newsValidator;
        this.dataRepository = dataRepository;
    }

    @Override
    public List<NewsDtoResponse> getAllNews() {
        return dataRepository.getAllNews().stream().map(newsDtoMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public NewsDtoResponse getNewsById(Long id) {
        try {
            return newsDtoMapper.convertToDto(dataRepository.getNewsById(id));
        } catch (NewsNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public NewsDtoResponse createNews(NewsDtoResponse newsDtoResponse) {
        IdGenerator idGenerator = new IdGenerator(dataRepository);
        Long id = idGenerator.getNewsId();
        LocalDateTime date = LocalDateTime.parse(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        try {
            newsValidator.validate(newsDtoResponse, dataRepository);
            return newsDtoMapper.convertToDto(dataRepository.createNews(new News(id, newsDtoResponse.getTitle(), newsDtoResponse.getContent(), date, date, newsDtoResponse.getAuthorId())));
        } catch (TitleLengthIsWrongException e) {
            System.out.println(e.getMessage());
        } catch (ContentLengthIsWrongException e) {
            System.out.println(e.getMessage());
        } catch (AuthorNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public NewsDtoResponse updateNews(Long id, NewsDtoResponse newsDtoResponse) {
        try {
            LocalDateTime createdDate = dataRepository.getNewsById(id).getCreatedDate();
            LocalDateTime updatedDate = LocalDateTime.parse(ZonedDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            newsValidator.validate(newsDtoResponse, dataRepository);
            return newsDtoMapper.convertToDto(dataRepository.updateNews(new News(id, newsDtoResponse.getTitle(), newsDtoResponse.getContent(), createdDate, updatedDate, newsDtoResponse.getAuthorId())));
        } catch (TitleLengthIsWrongException e) {
            System.out.println(e.getMessage());
        } catch (ContentLengthIsWrongException e) {
            System.out.println(e.getMessage());
        } catch (AuthorNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (NewsNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public boolean deleteNews(Long id){
        try {
            return dataRepository.deleteNewsById(id);
        } catch (NewsNotFoundException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
