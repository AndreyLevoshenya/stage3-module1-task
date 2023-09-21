package com.mjc.school.service;

import com.mjc.school.repository.models.NewsModel;
import org.modelmapper.ModelMapper;

public class NewsDtoMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public NewsDtoResponse convertToDto(NewsModel news) {
        return modelMapper.map(news, NewsDtoResponse.class);
    }
}
