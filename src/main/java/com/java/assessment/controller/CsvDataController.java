package com.java.assessment.controller;

import com.java.assessment.entity.CsvData;
import com.java.assessment.service.CsvDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/csv")
@Slf4j
public class CsvDataController {
    @Autowired
    CsvDataService csvDataService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        log.info("CsvDataController --> started uploading the csv file");
        csvDataService.saveCsvFile(file);

        return ResponseEntity.status(HttpStatus.OK).body("Uploaded the file successfully: " + file.getOriginalFilename());
    }

    @GetMapping()
    public ResponseEntity<List<CsvData>> getAllData() {
        log.info("CsvDataController --> started getting all the csv file data");
        List<CsvData> allCsvRecords = csvDataService.getAllCsvRecords();

        if (allCsvRecords.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allCsvRecords, HttpStatus.OK);
    }

    @GetMapping("{code}")
    public ResponseEntity<CsvData> getRecordByCode(@PathVariable("code") String code) {
        log.info("CsvDataController --> started getting the file data by code name");
        Optional<CsvData> optionalCsvData = csvDataService.getRecordByCodeName(code);

        if (optionalCsvData.isPresent()) {
            return new ResponseEntity<>(optionalCsvData.get(), HttpStatus.OK);
        }
        log.error("No record found by the given code");
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteAllRecords() {
        log.info("CsvDataController --> started deleting all the the records");
       csvDataService.deleteRecords();

       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
