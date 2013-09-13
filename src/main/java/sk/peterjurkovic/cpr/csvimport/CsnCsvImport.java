package sk.peterjurkovic.cpr.csvimport;

import java.io.InputStream;

import sk.peterjurkovic.cpr.dto.CsvImportLogDto;


public interface CsnCsvImport {

	CsvImportLogDto processImport(InputStream is);
}
