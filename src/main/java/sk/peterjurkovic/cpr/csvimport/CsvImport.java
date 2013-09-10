package sk.peterjurkovic.cpr.csvimport;

import java.io.InputStream;

public interface CsvImport {
	
	
	int processImport(InputStream is);
	
	
}
