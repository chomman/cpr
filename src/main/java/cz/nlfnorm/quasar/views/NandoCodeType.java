package cz.nlfnorm.quasar.views;

public interface NandoCodeType {
	
	/**
	 * Returns TRUE, if is this code for active medical device  
	 * Active MD has code MD 1000 and higher
	 * 
	 * @return TRUE, if is active MD
	 */
	boolean isActiveMd();
	
	
	/**
	 * Returns TRUE, if is this code for NON-Active medical device  
	 * NON Active MD has code MD up to MD 0999
	 * 
	 * @return TRUE, if is NON-Active MD
	 */
	boolean isNonActiveMd();
	
	
	/**
	 * Returns TRUE, if is this code for In-vitro Diagnostic medical device
	 * IVD start with IVD prefix
	 * 
	 * @return TRUE, if is IVD
	 */
	boolean isIvd();
	
	
	/**
	 * Returns TRUE, If the code is for Horizontal clasification
	 * MDS 70XX
	 * 
	 * @return
	 */
	boolean isHorizontal();
	
}
