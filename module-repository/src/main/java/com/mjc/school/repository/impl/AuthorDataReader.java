package com.mjc.school.repository.impl;

import com.mjc.school.repository.DataReader;
import com.mjc.school.repository.models.AuthorModel;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AuthorDataReader implements DataReader<AuthorModel> {
    private final String filename;

    public AuthorDataReader(String filename) {
        this.filename = filename;
    }

    @Override
    public List<AuthorModel> getInfo() {
        List<AuthorModel> data = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileReader(filename))) {
            while (scanner.hasNextLine()) {
                String[] values = scanner.nextLine().split(";");

                Long id = Long.parseLong(values[0]);
                String name = values[1];
                AuthorModel author = new AuthorModel(id, name);
                data.add(author);
            }

        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
        return data;
    }
}
