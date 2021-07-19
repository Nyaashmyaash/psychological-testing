package com.nyash.psychologicaltesting.api.utils;

import com.nyash.psychologicaltesting.api.exceptions.BadRequestException;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StringChecker {

    public void checkOnEmpty(@NonNull String value,
                             @NonNull String fieldName) {

        if(value.trim().isEmpty()) {
            throw new BadRequestException(String.format("Поле с названием \"%s\" не может быть пустым.", fieldName));
        }
    }
}
