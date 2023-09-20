package com.mjc.school.service;

import com.mjc.school.repository.DataRepository;

public class IdGenerator {
    private Long newsId;

    public IdGenerator(DataRepository dataSource) {
        newsId = (long) dataSource.getAllNews().size();
    }

    public Long getNewsId() {
        return ++newsId;
    }
}
