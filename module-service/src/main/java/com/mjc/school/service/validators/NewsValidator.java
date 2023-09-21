package com.mjc.school.service.validators;

import com.mjc.school.repository.DataSource;
import com.mjc.school.repository.models.AuthorModel;
import com.mjc.school.service.exceptions.AuthorNotFoundException;
import com.mjc.school.service.exceptions.ContentLengthIsWrongException;
import com.mjc.school.service.exceptions.TitleLengthIsWrongException;
import com.mjc.school.service.NewsDtoResponse;

public class NewsValidator implements Validator {

    @Override
    public boolean validate(NewsDtoResponse news, DataSource dataSource) throws TitleLengthIsWrongException, ContentLengthIsWrongException, AuthorNotFoundException {
        if(news.getTitle().length() <= 5 || news.getTitle().length() > 30) {
            throw new TitleLengthIsWrongException("Title length is wrong. Title field should have length of value from 5 to 30. News title is " + news.getTitle());
        }
        else if(news.getContent().length() <= 5 || news.getContent().length() > 255) {
            throw new ContentLengthIsWrongException("Content length is wrong. Content field should have length of value from 5 to 255. News content is " + news.getContent());
        }
        else if(!dataSource.readAllAuthors().contains(new AuthorModel(news.getAuthorId(), ""))) {
            throw new AuthorNotFoundException("Author Id does not exist. Author Id is: " + news.getAuthorId());
        }
        else {
            return true;
        }
    }
}
