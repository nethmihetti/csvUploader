package com.java.assessment.repository;

import com.java.assessment.entity.CsvData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CsvDataRepository extends JpaRepository<CsvData, Long> {
    Optional<CsvData> findByCode(String code);
}
