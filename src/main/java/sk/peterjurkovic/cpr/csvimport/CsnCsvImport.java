package sk.peterjurkovic.cpr.csvimport;

import java.io.InputStream;

import sk.peterjurkovic.cpr.dto.CsvImportDto;


public interface CsnCsvImport {

	CsvImportDto processImport(InputStream is);
}
