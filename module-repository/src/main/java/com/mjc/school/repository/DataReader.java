package com.mjc.school.repository;

import java.util.List;

public interface DataReader<T> {
    List<T> getInfo();
}
