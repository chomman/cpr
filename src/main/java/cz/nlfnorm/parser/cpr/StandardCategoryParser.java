package cz.nlfnorm.parser.cpr;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang.StringUtils;
import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import cz.nlfnorm.entities.Standard;
import cz.nlfnorm.entities.StandardChange;
import cz.nlfnorm.services.StandardService;

public class StandardCategoryParser extends AbstractParser {
	
	private final static String NEW_IDENTIFER = "(new)";
	
	private List<Standard> standardList = new ArrayList<>();
	private RowIndexes rowTypes;
	
	@Autowired
	private StandardService standardService;


	@Override
	public void parse(final String location) {
		Document doc = getDocument(location);
		Elements tables = doc.select("table.tableDefault");
		System.out.println("Count of tables found: " + tables.size());
		if(tables.size() > 0){
			final int tableIndex = determineTableIndex(tables);
			System.out.println("Table with standards has index: " + tableIndex);
			if(tableIndex != -1 ){
				rowTypes = new RowIndexes(tables.get(tableIndex));
				System.out.println(rowTypes);
				processTable ( tables.get(tableIndex) );
			}
		}
		
	}
	
	@Override
	public void processRow(final Elements tds) {
		final ListIterator<Element> it =  tds.listIterator();
		if(tds.size() == rowTypes.COLLUMN_SIZE){
			createStandard(it);
		}else if(tds.size() == (rowTypes.COLLUMN_SIZE - 1) ){
			createStandardChange(it);
		}else{
			throw new IllegalArgumentException("Unexpected collumn size: " + tds.size());
		}
	}
	
	public void createStandard(final ListIterator<Element> it){
		int index = 0;
		final Standard standard = new Standard();
		while (it.hasNext()) {
			Element td = it.next();	
			if(rowTypes.NAME_INDEX == index){
				extractStandardId(standard, td);
				extractStandardName(standard, td);
				standardService.saveOrUpdate(standard);
			}else if(rowTypes.PUBLICATION_DATE_INDEX == index){
				standard.setReleased(extractDate(td));
			}else if(rowTypes.REF_OR_REPLACED_STANDARDS_INDEX == index){
				extractReplaceStandard(standard, td);
			}else if(rowTypes.DATE_OF_CESASSION == index){
				standard.setStopValidity(extractDate(td));
			}
			index++;
		}
		
		standardList.add(standard);
	}
	
	
	public void createStandardChange(final ListIterator<Element> it){
		int index = 0;
		if(standardList.size() == 0){
			throw new IllegalArgumentException("Standard list is empty");
		}
		final Standard standard = standardList.get(standardList.size() - 1);
		final StandardChange standardChange = new StandardChange();
		while (it.hasNext()) {
			Element td = it.next();	
			if(rowTypes.NAME_INDEX - 1 == index ){
				if(isNew(td.text())){
					standard.setStatusDate(new LocalDate());
				}
				final String changeCode = getChangeCode(td);
				standardChange.setChangeCode(changeCode);
			}else if(rowTypes.PUBLICATION_DATE_INDEX - 1 == index ){
				standard.setReleased(extractDate(td));
			}else if(rowTypes.REF_OR_REPLACED_STANDARDS_INDEX - 1 == index ){
				
			}else if(rowTypes.DATE_OF_CESASSION - 1 == index ){
				
			}
			index++;
		}
		standard.getStandardChanges().add(standardChange);
		standardChange.setStandard(standard);
		standardService.updateStandard(standard);
	}
	
	 	
	public int determineTableIndex(final Elements tables){
		for(int i = (tables.size() - 1); i > -1; i--){
			if(tables.get(i).select("tr").size() > 1){
				return i;
			}
		}
		return -1;
	}
	
	public boolean isNew(final String tdText){
		return tdText.contains(NEW_IDENTIFER);
	}
	
	public String getStandardId(final Element td){
		return trim(td.select("p").eq(0).text().replace(NEW_IDENTIFER, ""));
	}
	
	public String getChangeCode(final Element td){
		final String text = td.select("p").get(0).text();
		if(StringUtils.isBlank(text)){
			throw new IllegalArgumentException("Standard change code can not be blank");
		}
		final int start = text.lastIndexOf("/");
		if(start == -1){
			throw new IllegalArgumentException("Can not determine change code first char index");
		}
		String changeCode = text.substring(text.lastIndexOf("/") + 1, text.length());
		return trim(changeCode.replace(NEW_IDENTIFER, ""));
	}
	
	public LocalDate extractDate(final Element td){
		String text = trim(td.text());
		if(StringUtils.isBlank(text)){
			return null;
		}
		text = text.replace("Date expired (","");
		text = text.replace(")", "");
		if(text.contains("This is the first publication")){
			return new LocalDate();
		}else if(text.length() == 10){
			final String[] splitedDate = text.split("/");
			if(splitedDate.length != 3){
				throw new IllegalArgumentException("Unsupported date format : " + text + " expacted dd/MM/yyyy");
			}
			return new LocalDate()
				.withDayOfMonth(Integer.valueOf(splitedDate[0]))
				.withMonthOfYear(Integer.valueOf(splitedDate[1]))
				.withYear(Integer.valueOf(splitedDate[2]));
				
		}
		throw new IllegalArgumentException("Invalid date: " + text);
	}

	
	public void extractStandardId(final Standard standard, final Element td){
		final String standardId = getStandardId(td);
		if(standardService.isStandardIdUnique(standardId, 0l)){
			standard.setStandardId(standardId);
		}else{
			throw new IllegalArgumentException("Standard, with id: " + standardId + " alerady exists.");
		}
	}
	public void extractStandardName(final Standard standard, final Element td){
		final Elements pList = td.select("p");
		if(pList.size() != 2){
			throw new IllegalArgumentException("Element which contains name of the standard was not found.");
		}
		final String englishName = trim(pList.get(1).text());
		standard.setEnglishName(englishName);
	}
	
	public void extractReplaceStandard(final Standard standard, final Element td){
		final String code = extractReplacedStandardCode(td);
		if(code != null){
			System.out.println("Replaced standard: " + code);
		}
	}
	
	public String extractReplacedStandardCode(final Element td){
		if(StringUtils.isNotBlank(trim(td.text())) && td.select("p").size() == 1){
			final Element p = td.select("p").get(0);
			p.select("a").remove();
			if(trim(p.text()).startsWith("HD")){
				return null;
			}
			if(StringUtils.isNotBlank(trim(p.text()))){
				final String html = p.html().replace("&nbsp;", "").replaceAll("<br /><br />", "<br />");
				String[] codes = html.split("<br />");
				if(codes.length == 0){
					return trim(html.replaceAll("<br />", ""));
				}else if(codes.length > 0){
					return trim(codes[0].replaceAll("<br />", ""));
				}
			}
		}
		return null;
	}
	
	final class RowIndexes {
		
		final int NAME_INDEX;
		final int PUBLICATION_DATE_INDEX;
		final int REF_OR_REPLACED_STANDARDS_INDEX;
		final int DATE_OF_CESASSION;
		final int COLLUMN_SIZE;
		
		
		private final static int NAME = 1;
		private final static int DATE = 2;
		private final static int REPLACED = 3;
		private final static int CESSASION = 4;
		
		
		RowIndexes(final Element table){
			COLLUMN_SIZE = table.select("tr").get(0).select("td") .size();
			NAME_INDEX = getForType(table, NAME);
			PUBLICATION_DATE_INDEX = getForType(table, DATE);
			REF_OR_REPLACED_STANDARDS_INDEX = getForType(table, REPLACED);
			DATE_OF_CESASSION = getForType(table, CESSASION);
		}
		
		
		int getForType(final Element table, final int type){
			ListIterator<Element> it = table.select("tr").get(0).select("td").listIterator();
			int i = 0;
			while(it.hasNext()){
				final String text = it.next().text();
				if(type == NAME && text.contains("Reference and title")){
					return i;
				}else if(type == DATE && text.contains("First publication")){
					return i;
				}else if(type == REPLACED && text.contains("Reference of superseded standard")){
					return i;
				}else if(type == CESSASION && text.contains("Date of cessation")){
					return i;
				}
				i++;
			}
			return -1;
		}


		@Override
		public String toString() {
			return "RowIndexes [NAME_INDEX=" + NAME_INDEX
					+ ", PUBLICATION_DATE_INDEX=" + PUBLICATION_DATE_INDEX
					+ ", REF_OR_REPLACED_STANDARDS="
					+ REF_OR_REPLACED_STANDARDS_INDEX + ", DATE_OF_CESASSION="
					+ DATE_OF_CESASSION + ", COLLUMN_SIZE=" + COLLUMN_SIZE
					+ "]";
		}
		
		
	}

}
