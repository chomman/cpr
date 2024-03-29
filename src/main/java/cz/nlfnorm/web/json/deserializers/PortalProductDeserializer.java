package cz.nlfnorm.web.json.deserializers;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import cz.nlfnorm.entities.PortalProduct;
import cz.nlfnorm.services.PortalProductService;

public class PortalProductDeserializer extends JsonDeserializer<PortalProduct> {
	
	@Autowired
	private PortalProductService portalProductService;
	
	public PortalProductDeserializer(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	@Override
	public PortalProduct deserialize(JsonParser json, DeserializationContext context)
			throws IOException, JsonProcessingException {
		if(StringUtils.isBlank(json.getText())){
			return null;
		}
		try {
			final Long id = Long.valueOf(json.getText());
			return portalProductService.getById(id);
		} catch (ParseException e) {
			return null;
		}
	}

}
