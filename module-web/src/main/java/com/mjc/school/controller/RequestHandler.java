package com.mjc.school.controller;

import com.mjc.school.service.NewsDtoResponse;
import com.mjc.school.controller.exceptions.IdIsWrongException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class RequestHandler {
    private final RequestController requestController;
    private final MenuPrinter menuPrinter;

    public RequestHandler(RequestController requestController, MenuPrinter menuPrinter) {
        this.requestController = requestController;
        this.menuPrinter = menuPrinter;
    }

    public void handle() {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;
        while (!exit) {
            long id;
            String title;
            String content;
            long authorId;
            menuPrinter.print();
            String clause = scanner.next();
            switch (clause) {
                case "1" -> {
                    System.out.println("Operation: Get all news.");
                    List<NewsDtoResponse> response = requestController.getAllNews();
                    for (NewsDtoResponse newsDtoResponse : response) {
                        System.out.println(newsDtoResponse);
                    }
                }
                case "2" -> {
                    System.out.println("Operation: Get news by id.");
                    System.out.println("Enter news id:");
                    try {
                        id = new Scanner(System.in).nextLong();
                        NewsDtoResponse newsDtoResponse = requestController.getNewsById(id);
                        if(newsDtoResponse != null) {
                            System.out.println(newsDtoResponse);
                        }
                    } catch (InputMismatchException e) {
                        idIsWrongExceptionThrowAndHandle("News Id should be number");
                    }
                }
                case "3" -> {
                    boolean authorIdIsValid = false;
                    while (!authorIdIsValid) {
                        System.out.println("Operation: Create news.");
                        System.out.println("Enter news title: ");
                        title = new Scanner(System.in).nextLine();
                        System.out.println("Enter news content: ");
                        content = new Scanner(System.in).nextLine();
                        System.out.println("Enter author id: ");
                        try{
                            authorId = new Scanner(System.in).nextLong();
                            authorIdIsValid = true;
                            NewsDtoResponse newsDtoResponse = requestController.createNews(new NewsDtoResponse(title, content, authorId));
                            if(newsDtoResponse != null) {
                                System.out.println(newsDtoResponse);
                            }
                        } catch (InputMismatchException e) {
                                idIsWrongExceptionThrowAndHandle("Author Id should be number");
                        }
                    }
                }
                case "4" -> {
                    boolean idIsNotValid = true;
                    boolean authorIdIsNotValid = true;
                    while (idIsNotValid || authorIdIsNotValid) {
                        System.out.println("Operation: Update news.");
                        System.out.println("Enter news id:");
                        try {
                            id = new Scanner(System.in).nextLong();
                            idIsNotValid = false;
                        } catch (InputMismatchException e) {
                            idIsWrongExceptionThrowAndHandle("News Id should be number");
                            continue;
                        }
                        System.out.println("Enter news title: ");
                        title = new Scanner(System.in).nextLine();
                        System.out.println("Enter news content: ");
                        content = new Scanner(System.in).nextLine();
                        System.out.println("Enter author id: ");
                        try {
                            authorId = new Scanner(System.in).nextLong();
                            authorIdIsNotValid = false;
                            NewsDtoResponse newsDtoResponse = requestController.updateNews(id, new NewsDtoResponse(title, content, authorId));
                            if(newsDtoResponse != null) {
                                System.out.println(newsDtoResponse);
                            }
                        } catch (InputMismatchException e) {
                            idIsWrongExceptionThrowAndHandle("Author Id should be number");
                        }
                    }

                }
                case "5" -> {
                    boolean idIsValid = false;
                    while (!idIsValid) {
                        System.out.println("Operation: Remove news by id.");
                        try {
                            id = new Scanner(System.in).nextLong();
                            idIsValid = true;
                            System.out.println(requestController.deleteNews(id));
                        } catch (InputMismatchException e) {
                            idIsWrongExceptionThrowAndHandle("News Id should be number");
                        }
                    }
                }
                case "0" -> exit = true;
                default -> System.out.println("command not found");
            }
        }
    }

    private void idIsWrongExceptionThrowAndHandle(String message) {
        try {
            throw new IdIsWrongException(message);
        } catch (IdIsWrongException e) {
            System.out.println(e.getMessage());
        }
    }
}
