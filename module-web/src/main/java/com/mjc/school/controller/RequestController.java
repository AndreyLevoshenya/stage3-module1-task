package com.mjc.school.controller;

import com.mjc.school.service.NewsDtoResponse;
import com.mjc.school.service.NewsService;

import java.util.List;

public class RequestController implements NewsController {
    private final NewsService service;

    public RequestController(NewsService service) {
        this.service = service;
    }

    @Override
    public List<NewsDtoResponse> getAllNews() {
        return service.getAllNews();
    }

    @Override
    public NewsDtoResponse getNewsById(Long id) {
        return service.getNewsById(id);
    }

    @Override
    public NewsDtoResponse createNews(NewsDtoResponse newsDtoResponse) {
        return service.createNews(newsDtoResponse);
    }

    @Override
    public NewsDtoResponse updateNews(Long id, NewsDtoResponse newsDtoResponse) {
        return service.updateNews(id, newsDtoResponse);
    }

    @Override
    public boolean deleteNews(Long id) {
        return service.deleteNews(id);
    }
}
