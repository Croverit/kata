package com.example.kata.utils.converter;

import com.example.kata.utils.exception.OperationNotSupportedException;
import com.example.kata.utils.constant.OperationType;
import org.springframework.core.convert.converter.Converter;

public class OperationTypeConverter implements Converter<String, OperationType> {
    @Override
    public OperationType convert(String source) {
        try {
            return OperationType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new OperationNotSupportedException("Bad operation");
        }
    }
}
