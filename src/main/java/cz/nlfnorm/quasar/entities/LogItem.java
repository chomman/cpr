package cz.nlfnorm.quasar.entities;

public interface LogItem {
	
	void clearNandoCodes();
	
	void clearEacCodes();
	
	void addEacCode(EacCode eacCode);
	
	void addNandoCode(NandoCode nandoCode);
}
