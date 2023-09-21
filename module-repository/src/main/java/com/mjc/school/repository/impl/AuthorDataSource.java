package com.mjc.school.repository.impl;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.models.Author;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorDataSource implements DataSource<Author> {
    private final String filename;

    public AuthorDataSource(String filename) {
        this.filename = filename;
    }

    @Override
    public List<Author> getInfo() {
        List<Author> data = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(";");

                Long id = Long.parseLong(values[0]);
                String name = values[1];
                Author author = new Author(id, name);
                data.add(author);
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return data;
    }
}
