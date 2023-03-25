package com.java.assessment.util;

import com.java.assessment.entity.CsvData;
import com.java.assessment.exception.CsvFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
@Slf4j
public class CsvUtils {
    private static final String TYPE = "text/csv";
    private static final String DATE_FORMAT = "dd-MM-yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);

    public static boolean hasCSVFormat(MultipartFile file) {
        return TYPE.equals(file.getContentType());
    }

    public static List<CsvData> decomposeCsvData(InputStream is) {
        log.info("decomposeCsvData --> Started reading the csv input stream");

        CSVFormat csvFormat = CSVFormat.Builder.create()
                .setSkipHeaderRecord(true)
                .setIgnoreHeaderCase(true)
                .setTrim(true)
                .build();

        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
             CSVParser csvParser = new CSVParser(fileReader,
                     CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim())) {

            List<CsvData> csvDataList = new ArrayList<>();

            Iterable<CSVRecord> csvRecords = csvParser.getRecords();

            for (CSVRecord csvRecord : csvRecords) {
                CsvData csvData =  CsvData.builder()
                                .source(csvRecord.get("source"))
                                .codeListCode(csvRecord.get("codeListCode"))
                                .code(csvRecord.get("code"))
                                .displayValue(csvRecord.get("displayValue"))
                                .longDescription(csvRecord.get("longDescription"))
                                .fromDate(parseDate(csvRecord.get("fromDate")))
                                .toDate(parseDate(csvRecord.get("toDate")))
                                .sortingPriority(parseInt(csvRecord.get("sortingPriority")))
                                .build();

                csvDataList.add(csvData);
            }

            log.info("Found {} csv records in the file", csvDataList.size());

            return csvDataList;
        } catch (IOException | DateTimeException e) {
            log.error("Error occurred while parsing the csv file: {}", e.getMessage());
            throw new CsvFormatException("Failed to parse the CSV file: " + e.getMessage());
        }
    }

    public static LocalDate parseDate(String date) {
        if(!Objects.equals(date, "")) {
            return LocalDate.parse(date, formatter);
        } else {
            return null;
        }
    }

    public static Integer parseInt(String integer) {
        if(!Objects.equals(integer, "")) {
            return Integer.parseInt(integer);
        } else {
            return null;
        }
    }
}
