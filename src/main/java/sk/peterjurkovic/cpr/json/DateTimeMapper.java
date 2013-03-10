package sk.peterjurkovic.cpr.json;

import org.apache.log4j.Logger;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTime;

public class DateTimeMapper extends ObjectMapper {
	
	
	Logger logger = Logger.getLogger(getClass());
	
	public DateTimeMapper() {
		super();
		SimpleModule module = new SimpleModule("DateTimeMapper", new Version(2, 0, 0, null));
        module.addDeserializer(DateTime.class, new DateTimeDeserializer());
        registerModule(module);
        
    }
}
