package com.mjc.school.controller;

import com.mjc.school.service.NewsDtoResponse;

import java.util.List;

public interface NewsController {
    List<NewsDtoResponse> getAllNews();

    NewsDtoResponse getNewsById(Long id);

    NewsDtoResponse createNews(NewsDtoResponse newsDtoResponse);

    NewsDtoResponse updateNews(Long id, NewsDtoResponse newsDtoResponse);

    boolean deleteNews(Long id);
}
