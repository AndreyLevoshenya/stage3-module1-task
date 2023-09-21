package com.mjc.school;

import com.mjc.school.controller.MenuPrinter;
import com.mjc.school.controller.RequestController;
import com.mjc.school.controller.RequestHandler;
import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.impl.FileDataSource;
import com.mjc.school.service.NewsDtoMapper;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.impl.NewsServiceImpl;
import com.mjc.school.service.validators.NewsValidator;

public class Main {

    public static void main(String[] args) {
        String newsFilename = "module-repository/src/main/resources/content.txt";
        String authorFilename = "module-repository/src/main/resources/author.txt";
        DataSource dataSource = new FileDataSource(newsFilename, authorFilename);
        NewsService newsService = new NewsServiceImpl(new NewsDtoMapper(), new NewsValidator(), dataSource);
        RequestController requestController = new RequestController(newsService);
        RequestHandler handler = new RequestHandler(requestController, new MenuPrinter());
        handler.handle();
    }
}
