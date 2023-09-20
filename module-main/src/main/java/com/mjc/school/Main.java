package com.mjc.school;

import com.mjc.school.controller.MenuPrinter;
import com.mjc.school.controller.RequestController;
import com.mjc.school.controller.RequestHandler;
import com.mjc.school.repository.DataRepository;
import com.mjc.school.repository.FileDataRepository;
import com.mjc.school.service.NewsDtoMapper;
import com.mjc.school.service.NewsService;
import com.mjc.school.service.NewsServiceImpl;
import com.mjc.school.service.validators.NewsValidator;

public class Main {

    public static void main(String[] args) {
        String newsFilename = "module-repository/src/main/resources/content.txt";
        String authorFilename = "module-repository/src/main/resources/author.txt";
        DataRepository dataSource = new FileDataRepository(newsFilename, authorFilename);
        NewsService newsService = new NewsServiceImpl(new NewsDtoMapper(), new NewsValidator(), dataSource);
        RequestController requestController = new RequestController(newsService);
        RequestHandler handler = new RequestHandler(requestController, new MenuPrinter());
        handler.handle();
    }
}
