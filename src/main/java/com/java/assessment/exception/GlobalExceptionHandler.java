package com.java.assessment.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<String> generalException(IOException ex) {
        log.error("IO error occurred while parsing: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bad File format");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationExceptionException(ConstraintViolationException ex) {
        log.error("Constraint violation exception: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Code field already exists");
    }

    @ExceptionHandler(FileTypeException.class)
    public ResponseEntity<String> handleFileTypeException(FileTypeException ex) {
        log.error("Error occurred with the file type: {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad File format");
    }

    @ExceptionHandler(CsvFormatException.class)
    public ResponseEntity<String> handleCsvFormatException(CsvFormatException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Csv format");
    }
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("File too large!");
    }
}
