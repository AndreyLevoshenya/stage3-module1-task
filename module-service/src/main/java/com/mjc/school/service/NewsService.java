package com.mjc.school.service;

import java.util.List;

public interface NewsService {

    List<NewsDtoResponse> getAllNews();

    NewsDtoResponse getNewsById(Long id);

    NewsDtoResponse createNews(NewsDtoResponse newsDtoResponse);

    NewsDtoResponse updateNews(Long id, NewsDtoResponse newsDtoResponse);

    boolean deleteNews(Long id);
}
