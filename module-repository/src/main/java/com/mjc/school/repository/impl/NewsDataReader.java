package com.mjc.school.repository.impl;

import com.mjc.school.repository.DataReader;
import com.mjc.school.repository.models.NewsModel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NewsDataReader implements DataReader<NewsModel> {
    private final String filename;

    public NewsDataReader(String filename) {
        this.filename = filename;
    }

    @Override
    public List<NewsModel> getInfo() {
        List<NewsModel> data = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(";");

                Long id = Long.parseLong(values[0]);
                String title = values[1];
                String content = values[2];
                LocalDateTime createDate = LocalDateTime.parse(values[3]);
                LocalDateTime lastUpdateDate = LocalDateTime.parse(values[4]);
                Long authorId = Long.parseLong(values[5]);

                NewsModel news = new NewsModel(id, title, content, createDate, lastUpdateDate, authorId);
                data.add(news);
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return data;
    }
}
