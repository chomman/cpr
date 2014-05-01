package sk.peterjurkovic.cpr.web.taglib.webpage;

import java.math.BigDecimal;

import org.springframework.web.servlet.tags.RequestContextAwareTag;

import sk.peterjurkovic.cpr.constants.Constants;
import sk.peterjurkovic.cpr.entities.PortalCurrency;
import sk.peterjurkovic.cpr.formatters.PriceFormatter;
import sk.peterjurkovic.cpr.utils.PriceUtils;

@SuppressWarnings("serial")
public class WebpagePriceTag extends RequestContextAwareTag{
	
	private BigDecimal price;
	private boolean isEuro = false;
	private boolean hideCurrency = false;
	private BigDecimal vat;
	private boolean useSystemVat = false;
	
	@Override
	protected int doStartTagInternal() throws Exception {
		if(price == null){
			return SKIP_PAGE;
		}
		PriceFormatter formetter = new PriceFormatter(hideCurrency);
		pageContext.getOut().print(formetter.print(preparePrice(), getCurrency()));		
		return SKIP_PAGE;
	}
	
	private String getCurrency(){
		if(isEuro){
			return PortalCurrency.EUR.getSymbol();
		}
		return PortalCurrency.CZK.getSymbol();
	}

	private BigDecimal preparePrice(){
		if(vat != null){
			return PriceUtils.getPriceWithVat(price, vat);
		}else if(useSystemVat){
			return PriceUtils.getPriceWithVat(price, Constants.VAT);
		}
		return price;
	}
	
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	
	public BigDecimal getVat() {
		return vat;
	}


	public void setVat(BigDecimal vat) {
		this.vat = vat;
	}


	public boolean isHideCurrency() {
		return hideCurrency;
	}

	public void setHideCurrency(boolean hideCurrency) {
		this.hideCurrency = hideCurrency;
	}

	
	public boolean isUseSystemVat() {
		return useSystemVat;
	}

	
	public void setUseSystemVat(boolean useSystemVat) {
		this.useSystemVat = useSystemVat;
	}

	public void setIsEuro(Boolean isEuro) {
		this.isEuro = isEuro;
	}
	
	
	
	

}
