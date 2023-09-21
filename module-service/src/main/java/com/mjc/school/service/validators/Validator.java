package com.mjc.school.service.validators;

import com.mjc.school.repository.DataSource;
import com.mjc.school.service.NewsDtoResponse;
import com.mjc.school.service.exceptions.AuthorNotFoundException;
import com.mjc.school.service.exceptions.ContentLengthIsWrongException;
import com.mjc.school.service.exceptions.TitleLengthIsWrongException;

public interface Validator {
    boolean validate(NewsDtoResponse newsDtoResponse, DataSource dataSource) throws TitleLengthIsWrongException, ContentLengthIsWrongException, AuthorNotFoundException;
}
