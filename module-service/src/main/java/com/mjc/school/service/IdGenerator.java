package com.mjc.school.service;

import com.mjc.school.repository.DataSource;

public class IdGenerator {
    private Long newsId;

    public IdGenerator(DataSource dataSource) {
        newsId = (long) dataSource.readAllNews().size();
    }

    public Long getNewsId() {
        return ++newsId;
    }
}
