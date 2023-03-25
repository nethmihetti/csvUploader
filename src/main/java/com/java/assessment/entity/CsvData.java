package com.java.assessment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "t_csvdata")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CsvData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String source;
    @Column(name = "codeListCode")
    private String codeListCode;
    @Column(name = "code")
    private String code;
    @Column(name = "displayValue")
    private String displayValue;
    @Column(name = "longDescription")
    private String longDescription;
    @Column(name = "fromDate")
    private LocalDate fromDate;
    @Column(name = "toDate")
    private LocalDate toDate;
    @Column(name = "sortingPriority")
    private Integer sortingPriority;
}
