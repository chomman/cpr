/**
 * 
 */
package cz.nlfnorm.export.sitemap;

import static java.util.Calendar.HOUR_OF_DAY;
import static java.util.Calendar.MILLISECOND;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.SECOND;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

/**
 * <p>Formats and parses dates in the six defined W3C date time formats.  These formats are described in 
 * "Date and Time Formats",
 * <a href="http://www.w3.org/TR/NOTE-datetime">http://www.w3.org/TR/NOTE-datetime</a>.</p>
 * 
 * <p>The formats are:
 * 
 * <ol>
 * <li>YEAR: YYYY (eg 1997)
 * <li>MONTH: YYYY-MM (eg 1997-07)
 * <li>DAY: YYYY-MM-DD (eg 1997-07-16)
 * <li>MINUTE: YYYY-MM-DDThh:mmTZD (eg 1997-07-16T19:20+01:00)
 * <li>SECOND: YYYY-MM-DDThh:mm:ssTZD (eg 1997-07-16T19:20:30+01:00)
 * <li>MILLISECOND: YYYY-MM-DDThh:mm:ss.sTZD (eg 1997-07-16T19:20:30.45+01:00)
 * </ol>
 * 
 * Note that W3C timezone designators (TZD) are either the letter "Z" (for GMT) or a pattern like "+00:30" or "-08:00".  This is unlike
 * RFC 822 timezones generated by SimpleDateFormat, which omit the ":" like this: "+0030" or "-0800".</p>
 * 
 * <p>This class allows you to either specify which format pattern to use, or (by default) to
 * automatically guess which pattern to use (AUTO mode).  When parsing in AUTO mode, we'll try parsing using each pattern
 * until we find one that works.  When formatting in AUTO mode, we'll use this algorithm:
 * 
 * <ol><li>If the date has fractional milliseconds (e.g. 2009-06-06T19:49:04.45Z) we'll use the MILLISECOND pattern
 * <li>Otherwise, if the date has non-zero seconds (e.g. 2009-06-06T19:49:04Z) we'll use the SECOND pattern
 * <li>Otherwise, if the date is not at exactly midnight (e.g. 2009-06-06T19:49Z) we'll use the MINUTE pattern
 * <li>Otherwise, we'll use the DAY pattern.  If you want to format using the MONTH or YEAR pattern, you must declare it explicitly.
 * </ol>
 * 
 * Finally note that, like all classes that inherit from DateFormat, <b>this class is not thread-safe</b>.  Also note that you
 * can explicitly specify the timezone to use for formatting using the {@link #setTimeZone(TimeZone)} method.
 * 
 * @author Dan Fabulich
 * @see <a href="http://www.w3.org/TR/NOTE-datetime">Date and Time Formats</a>
 */
public class W3CDateFormat extends SimpleDateFormat {
	private static final long serialVersionUID = -5733368073260485802L;

	/** The six patterns defined by W3C, plus {@link #AUTO} configuration */
	public enum Pattern {
		/** "yyyy-MM-dd'T'HH:mm:ss.SSSZ" */
		MILLISECOND("yyyy-MM-dd'T'HH:mm:ss.SSSZ", true),
		/** "yyyy-MM-dd'T'HH:mm:ssZ" */
		SECOND("yyyy-MM-dd'T'HH:mm:ssZ", true),
		/** "yyyy-MM-dd'T'HH:mmZ" */
		MINUTE("yyyy-MM-dd'T'HH:mmZ", true),
		/** "yyyy-MM-dd" */
		DAY("yyyy-MM-dd", false),
		/** "yyyy-MM" */
		MONTH("yyyy-MM", false),
		/** "yyyy" */
		YEAR("yyyy", false),
		/** Automatically compute the right pattern to use */
		AUTO("", true);
	
		private final String pattern;
		private final boolean includeTimeZone;
		
		Pattern(String pattern, boolean includeTimeZone) {
			this.pattern = pattern;
			this.includeTimeZone = includeTimeZone;
		}
	}
	
	private final Pattern pattern;
	/** The GMT ("zulu") time zone, for your convenience */
	public static final TimeZone ZULU = TimeZone.getTimeZone("GMT");
	
	/** Build a formatter in AUTO mode */
	public W3CDateFormat() {
		this(Pattern.AUTO);
	}
	
	/** Build a formatter using the specified Pattern, or AUTO mode */
	public W3CDateFormat(Pattern pattern) {
		super(pattern.pattern);
		this.pattern = pattern;
	}
	
	/** This is what you override when you extend DateFormat; use {@link DateFormat#format(Date)} instead */
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition pos) {
		boolean includeTimeZone = pattern.includeTimeZone;
		if (pattern == Pattern.AUTO) {
			includeTimeZone = autoFormat(date);
		}
		super.format(date, toAppendTo, pos);
		if (includeTimeZone) convertRfc822TimeZoneToW3c(toAppendTo);
		return toAppendTo;
	}
	
	private boolean applyPattern(Pattern pattern) {
		applyPattern(pattern.pattern);
		return pattern.includeTimeZone;
	}
	
	private boolean autoFormat(Date date) {
		if (calendar == null) calendar = new GregorianCalendar();
		calendar.setTime(date);
		boolean hasMillis = calendar.get(MILLISECOND) > 0;
		if (hasMillis) {
			return applyPattern(Pattern.MILLISECOND);
		}
		boolean hasSeconds = calendar.get(SECOND) > 0;
		if (hasSeconds) {
			return applyPattern(Pattern.SECOND);
		}
		boolean hasTime = (calendar.get(HOUR_OF_DAY) + calendar.get(MINUTE)) > 0;
		if (hasTime) {
			return applyPattern(Pattern.MINUTE);
		}
		return applyPattern(Pattern.DAY);
	}

	/** This is what you override when you extend DateFormat; use {@link DateFormat#parse(String)} instead */
	@Override
	public Date parse(String text, ParsePosition pos) {
		text = convertW3cTimeZoneToRfc822(text); 
		if (pattern == Pattern.AUTO) {
			return autoParse(text, pos);
		}
		return super.parse(text, pos);
	}
	
	private Date autoParse(String text, ParsePosition pos) {
		for (Pattern pattern : Pattern.values()) {
			if (pattern == Pattern.AUTO) continue;
			applyPattern(pattern);
			Date out = super.parse(text, pos);
			if (out != null) return out;
		}
		return null; // this will force a ParseException
	}
	
	private void convertRfc822TimeZoneToW3c(StringBuffer toAppendTo) {		
		int length = toAppendTo.length();
		if (ZULU.equals(calendar.getTimeZone())) {
			toAppendTo.replace(length - 5, length, "Z");
		} else {
			toAppendTo.insert(length - 2, ':');
		}
	}
	
	private String convertW3cTimeZoneToRfc822(String source) {
		int length = source.length();
		if (source.endsWith("Z")) {
			return source.substring(0, length-1) + "+0000";
		}
		if (source.charAt(length-3) == ':') {
			return source.substring(0, length-3) + source.substring(length - 2);
		}
		return source;
	}
	
}