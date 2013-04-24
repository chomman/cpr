package sk.peterjurkovic.cpr.web.json;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.joda.time.DateTime;

public class DateTimeMapper extends ObjectMapper {
	
	public DateTimeMapper() {
		super();
		SimpleModule module = new SimpleModule("DateTimeMapper", new Version(2, 0, 0, null));
        module.addDeserializer(DateTime.class, new DateTimeDeserializer());
        registerModule(module);
        
    }
}
