package com.java.assessment.service;

import com.java.assessment.entity.CsvData;
import com.java.assessment.exception.FileTypeException;
import com.java.assessment.repository.CsvDataRepository;
import com.java.assessment.util.CsvUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CsvDataService {
    @Autowired
    CsvDataRepository csvDataRepository;

    public void saveCsvFile(MultipartFile file) {
        try {
            if (CsvUtils.hasCSVFormat(file)) {
                List<CsvData> tutorials = CsvUtils.decomposeCsvData(file.getInputStream());
                csvDataRepository.saveAll(tutorials);
            } else {
                log.error("Uploaded file does not have text/csv format");
                throw new FileTypeException("Uploaded file does not have text/csv format");
            }
        } catch (IOException ex) {
            log.error("Error occurred while reading from the file");
            throw new FileTypeException(ex.getMessage());
        }

    }
    public List<CsvData> getAllCsvRecords() {
        return csvDataRepository.findAll();
    }

    public Optional<CsvData> getRecordByCodeName(String codeName) {
        return csvDataRepository.findByCode(codeName);
    }

    public void deleteRecords() {
        csvDataRepository.deleteAll();
        log.info("Successfully deleted all the records from the database");
    }
}
