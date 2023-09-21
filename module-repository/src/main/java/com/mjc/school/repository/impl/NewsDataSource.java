package com.mjc.school.repository.impl;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.models.News;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsDataSource implements DataSource<News> {
    private final String filename;

    public NewsDataSource(String filename) {
        this.filename = filename;
    }

    @Override
    public List<News> getInfo() {
        List<News> data = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(";");

                Long id = Long.parseLong(values[0]);
                String title = values[1];
                String content = values[2];
                LocalDateTime createDate = LocalDateTime.parse(values[3]);
                LocalDateTime lastUpdateDate = LocalDateTime.parse(values[4]);
                Long authorId = Long.parseLong(values[5]);

                News news = new News(id, title, content, createDate, lastUpdateDate, authorId);
                data.add(news);
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return data;
    }
}
