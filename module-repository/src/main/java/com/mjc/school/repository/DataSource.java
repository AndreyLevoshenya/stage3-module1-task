package com.mjc.school.repository;

import java.util.List;

public interface DataSource<T> {
    List<T> getInfo();
}
