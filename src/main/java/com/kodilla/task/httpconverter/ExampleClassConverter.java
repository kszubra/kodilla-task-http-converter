package com.kodilla.task.httpconverter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.server.ResponseStatusException;

public class ExampleClassConverter implements HttpMessageConverter<Object> {

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        return clazz.getName().equals("com.kodilla.task.httpconverter.ExampleClass") &&
                mediaType.getSubtype().equals("plain") && mediaType.getType().equals("text");
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return clazz.getName().equals("com.kodilla.task.httpconverter.ExampleClass") &&
                mediaType.getSubtype().equals("plain") && mediaType.getType().equals("text");
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return List.of(MediaType.TEXT_PLAIN);
    }

    @Override
    public Object read(Class<?> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        StringBuilder builder = new StringBuilder();

        try (Reader reader = new BufferedReader(
                new InputStreamReader(
                        inputMessage.getBody(),
                        Charset.forName(StandardCharsets.UTF_8.name())))) {
            int c = 0;

            while ((c = reader.read()) != -1)
                builder.append((char) c);
        }

        try {
            String[] fields = builder.toString().split("\\|");
            String stringField = fields[0];
            Integer numberField = Integer.parseInt(fields[1]);
            Boolean booleanField = Boolean.valueOf(fields[2]);

            return new ExampleClass(stringField, numberField, booleanField);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to convert");
        }
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {

    }
}
