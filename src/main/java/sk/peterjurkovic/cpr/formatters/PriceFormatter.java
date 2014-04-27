package sk.peterjurkovic.cpr.formatters;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;


public class PriceFormatter implements Formatter<BigDecimal>{

	private NumberFormat priceFormat = NumberFormat.getCurrencyInstance(new Locale("cs", "CZ"));
	
	 public PriceFormatter(boolean hideCurrency) {
        if (hideCurrency) {
            priceFormat = NumberFormat.getInstance();
        }
	 }
	
	@Override
	public String print(BigDecimal price, Locale locale) {
		if (price == null) {
            return "";
        }

        try {
            if (price.stripTrailingZeros().scale() > 0) {
                priceFormat.setMinimumFractionDigits(2);
                priceFormat.setMaximumFractionDigits(2);
            }
            return priceFormat.format(price);
        } catch (IllegalArgumentException iae) {
            return "";
        }
	}

	@Override
	public BigDecimal parse(String text, Locale locale) throws ParseException {
		if (text.contains(",")) {
            text = text.replaceAll(",", ".");
        }

        BigDecimal price;
        try {
            price = new BigDecimal(priceFormat.parse(text).doubleValue());
        } catch (ParseException pe) {
            price = null;
        }
        if (price == null) {
            try {
                price = new BigDecimal(text.replaceAll("Kč", "").trim());
            } catch (NumberFormatException nfe) {
                throw new ParseException("Invalid price format: " + text, 0);
            }
        }
        if (price.doubleValue() < 0) {
            throw new ParseException("Price can not be negative: " + text, 0);
        }
        return price;
	}

}