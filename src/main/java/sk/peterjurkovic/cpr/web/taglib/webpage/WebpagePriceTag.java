package sk.peterjurkovic.cpr.web.taglib.webpage;

import java.math.BigDecimal;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import sk.peterjurkovic.cpr.context.ContextHolder;
import sk.peterjurkovic.cpr.formatters.PriceFormatter;

@SuppressWarnings("serial")
public class WebpagePriceTag extends RequestContextAwareTag{
	
	private BigDecimal price;
	private boolean hideCurrency = false;
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(price == null){
			return SKIP_PAGE;
		}
		PriceFormatter formetter = new PriceFormatter(hideCurrency);
		pageContext.getOut().print(formetter.print(price, ContextHolder.getLocale()));		
		return SKIP_PAGE;
	}

	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public boolean isHideCurrency() {
		return hideCurrency;
	}

	public void setHideCurrency(boolean hideCurrency) {
		this.hideCurrency = hideCurrency;
	}
	
	

}
