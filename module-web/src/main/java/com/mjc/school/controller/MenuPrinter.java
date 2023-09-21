package com.mjc.school.controller;

public class MenuPrinter {
    public void print() {
        System.out.println("""
                Enter the number of operation:
                1 - Get all news.
                2 - Get news by id.
                3 - Create news.
                4 - Update news.
                5 - Remove news by id.
                0 - Exit.""");
    }
}
