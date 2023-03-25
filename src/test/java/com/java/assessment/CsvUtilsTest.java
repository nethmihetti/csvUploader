package com.java.assessment;

import com.java.assessment.entity.CsvData;
import com.java.assessment.exception.CsvFormatException;
import com.java.assessment.util.CsvUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

public class CsvUtilsTest {

    // Test hasCSVFormat method
    @Test()
    public void testHasCSVFormat() {
        MockMultipartFile csvFile = new MockMultipartFile("data", "data.csv", "text/csv", "content".getBytes());
        Assertions.assertTrue(CsvUtils.hasCSVFormat(csvFile));
    }

    // Test decomposeCsvData method with valid CSV file
    @Test
    public void testDecomposeCsvDataValidCsv() throws IOException {
        String csvData = "source,codeListCode,code,displayValue,longDescription,fromDate,toDate,sortingPriority\n" +
                "ZIB,ZIB001,271636001,Polsslag regelmatig,The long description is necessary,01-01-2022,31-12-2022,1\n" +
                "ZIB,ZIB001,61086009,Polsslag onregelmatig,,01-01-2023,,2";
        InputStream stream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));
        List<CsvData> csvDataList = CsvUtils.decomposeCsvData(stream);
        Assertions.assertEquals(2, csvDataList.size());
        Assertions.assertEquals("ZIB", csvDataList.get(0).getSource());
        Assertions.assertEquals("271636001", csvDataList.get(0).getCode());
        Assertions.assertEquals(LocalDate.of(2022, 1, 1), csvDataList.get(0).getFromDate());
        Assertions.assertEquals(LocalDate.of(2022, 12, 31), csvDataList.get(0).getToDate());
        Assertions.assertEquals(1, csvDataList.get(0).getSortingPriority());
    }

    // Test decomposeCsvData method with invalid CSV file
    @Test
    public void testDecomposeCsvDataInvalidCsv() {
        String csvData = "source,codeListCode,code,displayValue,longDescription,fromDate,toDate,sortingPriority\n" +
                "ZIB,ZIB001,271636001,Polsslag regelmatig,The long description is necessary,01-01-2022,31-12-2022,1\n" +
                "ZIB,ZIB001,61086009,Polsslag onregelmatig,,invalid-date,,2";
        InputStream stream = new ByteArrayInputStream(csvData.getBytes(StandardCharsets.UTF_8));
        Assertions.assertThrows(CsvFormatException.class, () -> CsvUtils.decomposeCsvData(stream));
    }

    // Test parseDate method with valid date
    @Test
    public void testParseDateValid() {
        LocalDate date = CsvUtils.parseDate("01-01-2022");
        Assertions.assertEquals(LocalDate.of(2022, 1, 1), date);
    }

    // Test parseDate method with empty string
    @Test
    public void testParseDateEmpty() {
        LocalDate date = CsvUtils.parseDate("");
        Assertions.assertNull(date);
    }

    // Test parseInt method with valid integer
    @Test
    public void testParseIntValid() {
        Integer integer = CsvUtils.parseInt("123");
        Assertions.assertEquals(123, integer);
    }

    // Test parseInt method with empty string
    @Test
    public void testParseIntEmpty() {
        Integer integer = CsvUtils.parseInt("");
        Assertions.assertNull(integer);
    }
}
