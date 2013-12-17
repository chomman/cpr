package sk.peterjurkovic.cpr.utils;

import sk.peterjurkovic.cpr.constants.DopTextVariable;
import sk.peterjurkovic.cpr.entities.AssessmentSystem;
import sk.peterjurkovic.cpr.entities.DeclarationOfPerformance;
import sk.peterjurkovic.cpr.entities.NotifiedBody;

/**
 * Utilita pre pracu s DoP
 * 
 * @author peto
 *
 */
public class DoPUtils {
	
	/**
	 * Extrahuje z daneho dop definovane premenne, a nahradi ich za relevantne informacie
	 * 	
	 * @param DeclarationOfPerformance
	 * @return vysledny text, 
	 */
	public static String makeText(DeclarationOfPerformance dop){
		String result = replaceVariables(dop.getAssessmentSystem(), dop.getNotifiedBody(), dop);
		if(dop.getCumulative() != null && dop.getCumulative() && dop.getAssessmentSystem2() != null){
			result += replaceVariables(dop.getAssessmentSystem2(), dop.getNotifiedBody2(), dop);
		}
		return result;
	}
	
	
	private static String replaceVariables(AssessmentSystem as, NotifiedBody nb, DeclarationOfPerformance dop){
		String result = as.getDeclarationOfPerformanceText();
		if(as.getAssessmentSystemCode() != "4" && nb != null){
			result = result.replaceAll(DopTextVariable.VAR_NOAO_NAME, 
					"<strong>"+nb.getName()+ " - " +
							 nb.getAddress().getStreet()+", "+
							 nb.getAddress().getZip()+", "+
							 nb.getAddress().getCity()+", " +
							 nb.getCountry().getCountryName()+"</strong>");
			result = result.replaceAll(DopTextVariable.VAR_NOAO_ID, "<strong>"+nb.getNoCode()+"</strong>");
			result = result.replaceAll(DopTextVariable.VAR_REPORT, "<strong>"+dop.getReport()+"</strong>");
		}
		result = "<div class=\"system-r\"><h3>"+dop.getStandard().getStandardId()+", "+ as.getName() +":</h3>"+result+"</div>";
		return result;
	}
	
}
