package cz.nlfnorm.csvimport;

import java.io.InputStream;

import cz.nlfnorm.dto.CsvImportLogDto;


public interface CsnCsvImport {

	CsvImportLogDto processImport(InputStream is);
}
