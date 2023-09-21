package com.mjc.school.service;

import com.mjc.school.repository.models.News;
import org.modelmapper.ModelMapper;

public class NewsDtoMapper {
    private final ModelMapper modelMapper = new ModelMapper();

    public NewsDtoResponse convertToDto(News news) {
        return modelMapper.map(news, NewsDtoResponse.class);
    }
}
