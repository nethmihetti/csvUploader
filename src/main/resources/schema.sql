--CREATE SCHEMA IF NOT EXISTS assessment;
--SET SCHEMA assessment;
DROP TABLE IF EXISTS t_csvdata;
CREATE TABLE t_csvdata (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    source VARCHAR(3) NOT NULL,
    codeListCode VARCHAR(6) NOT NULL,
    code VARCHAR(50) NOT NULL,
    displayValue VARCHAR(500) NOT NULL,
    longDescription VARCHAR NOT NULL,
    fromDate DATE NOT NULL,
    toDate DATE NULL,
    sortingPriority INT NULL,
    CONSTRAINT uniqueCode unique (code)
);